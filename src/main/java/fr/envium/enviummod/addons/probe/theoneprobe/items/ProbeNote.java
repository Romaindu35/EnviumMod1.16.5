package fr.envium.enviummod.addons.probe.theoneprobe.items;

import fr.envium.enviummod.addons.probe.theoneprobe.TheOneProbe;
import fr.envium.enviummod.addons.probe.theoneprobe.gui.GuiConfig;
import fr.envium.enviummod.addons.probe.theoneprobe.gui.GuiNote;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import net.minecraft.item.Item.Properties;

public class ProbeNote extends Item {

    public ProbeNote() {
        super(new Properties()
                .stacksTo(1)
                .tab(TheOneProbe.tabProbe));
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (world.isClientSide) {
            if (player.isShiftKeyDown()) {
                GuiConfig.open();
            } else {
                GuiNote.open();
            }
            return new ActionResult<>(ActionResultType.SUCCESS, stack);
        }
        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }
}
