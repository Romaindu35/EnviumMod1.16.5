package fr.envium.enviummod.addons.probe.theoneprobe.compat;

//import baubles.api.BaublesApi;
//import baubles.api.cap.IBaublesItemHandler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public class BaubleTools {

    public static boolean hasProbeGoggle(PlayerEntity player) {
//        IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
//        if (handler == null) {
//            return false;
//        }
//        ItemStack stackInSlot = handler.getStackInSlot(4);
//        if (!stackInSlot.isEmpty() && stackInSlot.getItem() == ModItems.probeGoggles) {
//            return true;
//        }
        return false;
    }

    public static Item initProbeGoggle() {
        return null; //return new ProbeGoggles();
    }

    public static void initProbeModel(Item probeGoggle) {
//        ((ProbeGoggles) probeGoggle).initModel();
    }

}
