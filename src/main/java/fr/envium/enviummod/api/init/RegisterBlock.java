package fr.envium.enviummod.api.init;

import fr.envium.enviummod.References;
import fr.envium.enviummod.api.tab.EnviumTab;
import fr.envium.enviummod.core.blocks.*;
import fr.envium.enviummod.core.client.render.ItemRenderer;
import fr.envium.enviummod.core.tileentity.TileEnviumChest;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(References.MODID)
public class RegisterBlock {

    public static List<Block> blocks = new ArrayList<>();

    public static Block envium_block, envium_ore, cave_block, envium_furnace, envium_chest, pillow;
            //warped_stem, warped_plank, warped_door, warped_wart, warped_hyphae, crimson_stem, crimson_plank, crimson_door, crimson_hyphae;

    public static void init() {
        envium_block = new BlockMod().setRegistryName("envium_block");
        envium_ore = new BlockOreMod(1, 3).setRegistryName("envium_ore");
        //cave_block = new GlassBlock(Block.Properties.create(Material.GLASS).hardnessAndResistance(0.3F).sound(SoundType.GLASS)).setRegistryName("cave_block");
        cave_block = new BlockCaveBlock().setRegistryName("cave_block");
        envium_furnace = new BlockFurnaceMod().setRegistryName("envium_furnace");
        envium_chest = new EnviumChest(/*Block.Properties.create(Material.ROCK)*/).setRegistryName("chest_envium");
        pillow = new BlockPillow().setRegistryName("pillow");
        /*warped_stem = new BlockStem(MaterialColor.WOOD).setRegistryName("warped_stem");
        warped_plank = new BlockPlank().setRegistryName("warped_planks");
        warped_door = new BlockDoor().setRegistryName("warped_door");
        warped_wart = new BlockWart().setRegistryName("warped_wart");
        warped_hyphae = new BlockHyphae(MaterialColor.WOOD).setRegistryName("warped_hyphae");
        crimson_stem = new BlockStem(MaterialColor.WOOD).setRegistryName("crimson_stem");
        crimson_plank = new BlockPlank().setRegistryName("crimson_planks");
        crimson_door = new BlockDoor().setRegistryName("crimson_door");
        crimson_hyphae = new BlockHyphae(MaterialColor.WOOD).setRegistryName("crimson_hyphae");*/
    }

    @SubscribeEvent
    public static void registerBlock(RegistryEvent.Register<Block> event) {
        /*event.getRegistry().registerAll(envium_block, envium_ore, cave_block, envium_furnace,
                envium_chest, pillow, warped_stem, warped_plank, warped_door, warped_wart, warped_hyphae,
                crimson_stem, crimson_plank, crimson_door, crimson_hyphae);*/

        event.getRegistry().registerAll(envium_block, envium_ore, cave_block, envium_furnace,
                envium_chest, pillow);
    }

    @SubscribeEvent
    public static void registerBlocksItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                new BlockItem(envium_block, new Item.Properties().group(EnviumTab.TAB)).setRegistryName(envium_block.getRegistryName()),
                new BlockItem(envium_ore, new Item.Properties().group(EnviumTab.TAB)).setRegistryName(envium_ore.getRegistryName()),
                new BlockItem(cave_block, new Item.Properties().group(EnviumTab.TAB)).setRegistryName(cave_block.getRegistryName()),
                new BlockItem(envium_furnace, new Item.Properties().group(EnviumTab.TAB)).setRegistryName(envium_furnace.getRegistryName()),
                new BlockItem(envium_chest, new Item.Properties().group(EnviumTab.TAB)).setRegistryName(envium_chest.getRegistryName()),/*.setISTER(RegisterBlock::enviumChestRenderer)).setRegistryName(envium_chest.getRegistryName()),*/
                new BlockItem(pillow, new Item.Properties().group(EnviumTab.TAB)).setRegistryName(pillow.getRegistryName())
                /*new BlockItem(warped_stem, new Item.Properties().group(EnviumTab.TAB)).setRegistryName(warped_stem.getRegistryName()),
                new BlockItem(warped_plank, new Item.Properties().group(EnviumTab.TAB)).setRegistryName(warped_plank.getRegistryName()),
                new BlockItem(warped_door, new Item.Properties().group(EnviumTab.TAB)).setRegistryName(warped_door.getRegistryName()),
                new BlockItem(warped_wart, new Item.Properties().group(EnviumTab.TAB)).setRegistryName(warped_wart.getRegistryName()),
                new BlockItem(warped_hyphae, new Item.Properties().group(EnviumTab.TAB)).setRegistryName(warped_hyphae.getRegistryName()),
                new BlockItem(crimson_stem, new Item.Properties().group(EnviumTab.TAB)).setRegistryName(crimson_stem.getRegistryName()),
                new BlockItem(crimson_plank, new Item.Properties().group(EnviumTab.TAB)).setRegistryName(crimson_plank.getRegistryName()),
                new BlockItem(crimson_door, new Item.Properties().group(EnviumTab.TAB)).setRegistryName(crimson_door.getRegistryName()),
                new BlockItem(crimson_hyphae, new Item.Properties().group(EnviumTab.TAB)).setRegistryName(crimson_hyphae.getRegistryName())*/

        );
    }

    @OnlyIn(Dist.CLIENT)
    private static Callable<ItemStackTileEntityRenderer> enviumChestRenderer() {
        return () -> new ItemRenderer(TileEnviumChest::new);
    }
}
