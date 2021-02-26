package fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.providers;

import fr.envium.enviummod.addons.probe.theoneprobe.RegisterItem;
import fr.envium.enviummod.addons.probe.theoneprobe.TheOneProbe;
import fr.envium.enviummod.addons.probe.theoneprobe.api.*;
import fr.envium.enviummod.addons.probe.theoneprobe.config.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import java.util.HashMap;
import java.util.Map;

import static fr.envium.enviummod.addons.probe.theoneprobe.api.TextStyleClass.*;

public class HarvestInfoTools {

    private static final ResourceLocation ICONS = new ResourceLocation(TheOneProbe.MODID, "textures/gui/icons.png");
    private static String[] harvestLevels = new String[]{
            "stone",
            "iron",
            "diamond",
            "obsidian",
            "cobalt"
    };

    private static final Map<ToolType, ItemStack> TEST_TOOLS = new HashMap<>();
    static {
        TEST_TOOLS.put(ToolType.SHOVEL, new ItemStack(Items.WOODEN_SHOVEL));
        TEST_TOOLS.put(ToolType.AXE, new ItemStack(Items.WOODEN_AXE));
        TEST_TOOLS.put(ToolType.PICKAXE, new ItemStack(Items.WOODEN_PICKAXE));
    }

    static void showHarvestLevel(IProbeInfo probeInfo, BlockState blockState, Block block) {
        ToolType harvestTool = block.getHarvestTool(blockState);
        if (harvestTool != null) {
            int harvestLevel = block.getHarvestLevel(blockState);
            String harvestName;
            if (harvestLevel >= harvestLevels.length) {
                harvestName = Integer.toString(harvestLevel);
            } else if (harvestLevel < 0) {
                harvestName = Integer.toString(harvestLevel);
            } else {
                harvestName = harvestLevels[harvestLevel];
            }
            probeInfo.text(CompoundText.createLabelInfo("Tool: ",harvestTool + " (level " + harvestName + ")"));
        }
    }

    static void showCanBeHarvested(IProbeInfo probeInfo, World world, BlockPos pos, Block block, PlayerEntity player) {
        if (RegisterItem.isProbeInHand(player.getHeldItemMainhand())) {
            // If the player holds the probe there is no need to show harvestability information as the
            // probe cannot harvest anything. This is only supposed to work in off hand.
            return;
        }

        boolean harvestable = block.canHarvestBlock(world.getBlockState(pos), world, pos, player) && world.getBlockState(pos).getBlockHardness(world, pos) >= 0;
        if (harvestable) {
            probeInfo.text(CompoundText.create().style(OK).text("Harvestable"));
        } else {
            probeInfo.text(CompoundText.create().style(WARNING).text("Not harvestable"));
        }
    }

    static void showHarvestInfo(IProbeInfo probeInfo, World world, BlockPos pos, Block block, BlockState blockState, PlayerEntity player) {
        boolean harvestable = block.canHarvestBlock(world.getBlockState(pos), world, pos, player) && world.getBlockState(pos).getBlockHardness(world, pos) >= 0;

        ToolType harvestTool = block.getHarvestTool(blockState);
        String harvestName = null;

        if (harvestTool == null) {
            // The block doesn't have an explicitly-set harvest tool, so we're going to test our wooden tools against the block.
            float blockHardness = blockState.getBlockHardness(world, pos);
            if (blockHardness > 0f) {
                for (Map.Entry<ToolType, ItemStack> testToolEntry : TEST_TOOLS.entrySet()) {
                    // loop through our test tools until we find a winner.
                    ItemStack testTool = testToolEntry.getValue();

                    if (testTool != null && testTool.getItem() instanceof ToolItem) {
                        ToolItem toolItem = (ToolItem) testTool.getItem();
                        // @todo 1.13
                        if (testTool.getDestroySpeed(blockState) >= toolItem.getTier().getEfficiency()) {
                            // BINGO!
                            harvestTool = testToolEntry.getKey();
                            break;
                        }
                    }
                }
            }
        }

        if (harvestTool != null) {
            int harvestLevel = block.getHarvestLevel(blockState);
            if (harvestLevel < 0) {
                // NOTE: When a block doesn't have an explicitly-set harvest tool, getHarvestLevel will return -1 for ANY tool. (Expected behavior)
//                TheOneProbe.logger.info("HarvestLevel out of bounds (less than 0). Found " + harvestLevel);
            } else if (harvestLevel >= harvestLevels.length) {
//                TheOneProbe.logger.info("HarvestLevel out of bounds (Max value " + harvestLevels.length + "). Found " + harvestLevel);
            } else {
                harvestName = harvestLevels[harvestLevel];
            }
        }

        boolean v = Config.harvestStyleVanilla.get();
        int offs = v ? 16 : 0;
        int dim = v ? 13 : 16;

        ILayoutStyle alignment = probeInfo.defaultLayoutStyle().alignment(ElementAlignment.ALIGN_CENTER);
        IIconStyle iconStyle = probeInfo.defaultIconStyle().width(v ? 18 : 20).height(v ? 14 : 16).textureWidth(32).textureHeight(32);
        IProbeInfo horizontal = probeInfo.horizontal(alignment);
        if (harvestable) {
            horizontal.icon(ICONS, 0, offs, dim, dim, iconStyle)
                    .text(CompoundText.create().style(OK).text(((harvestTool != null) ? harvestTool.getName() : "No tool")));
        } else {
            if (harvestName == null || harvestName.isEmpty()) {
                horizontal.icon(ICONS, 16, offs, dim, dim, iconStyle)
                        .text(CompoundText.create().style(WARNING).text(((harvestTool != null) ? harvestTool.getName() : "No tool")));
            } else {
                horizontal.icon(ICONS, 16, offs, dim, dim, iconStyle)
                        .text(CompoundText.create().style(WARNING).text(((harvestTool != null) ? harvestTool.getName() : "No tool") + " (level " + harvestName + ")"));
            }
        }
    }
}
