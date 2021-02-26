package fr.envium.enviummod.core.client.render;

import fr.envium.enviummod.core.packets.GetInventoryTypePacket;
import fr.envium.enviummod.api.packets.NetworkRegistryHandler;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

@OnlyIn(Dist.CLIENT)
public class RenderOverlay {

    private static final int WIDTH = 128;
    private static final int HEIGHT = 128;

    public static String inventoryType;

    public static boolean change = true;

    public static void drawText(MainWindow window) {
        if (change) {
            String player = Minecraft.getInstance().player.getName().getString();
            //NetworkRegistryHandler.network.send(PacketDistributor.SERVER.noArg(), new InventoryPacket(player, InventoryAction.READ));
            NetworkRegistryHandler.network.send(PacketDistributor.SERVER.noArg(), new GetInventoryTypePacket(player));
            change = false;
        }
        //System.out.println("[RenderOverlay] " + (window.getWidth() - 100));
        Minecraft.getInstance().fontRenderer.drawString("Inventaire : " + inventoryType, 0,0, 255);
    }

}
