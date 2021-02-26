package fr.envium.enviummod.core.packets;

import fr.envium.enviummod.core.client.gui.MetierScreen;
import fr.envium.enviummod.core.client.gui.toast.JobsToast;
import fr.envium.enviummod.core.client.gui.toast.JobsToastGui;
import fr.envium.enviummod.core.client.render.RenderOverlay;
import fr.envium.enviummod.core.server.enums.ActionResponseClient;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ReponseClientPacket implements IStringSerializable {

    private ActionResponseClient actionResponseClient;
    private String value;

    public ReponseClientPacket() {}

    public ReponseClientPacket(ActionResponseClient actionResponseClient, String value)
    {
        this.actionResponseClient = actionResponseClient;
        this.value = value;
    }

    public static void encode(ReponseClientPacket msg, PacketBuffer buf)
    {
        buf.writeString(msg.actionResponseClient.toString(), 64);
        buf.writeString(msg.value, 32);
    }

    public static ReponseClientPacket decode(PacketBuffer buf)
    {
        ActionResponseClient actionResponseClientString = ActionResponseClient.valueOf(buf.readString());
        String value = buf.readString();
        return new ReponseClientPacket(actionResponseClientString, value);
    }

    @Override
    public String getName() {
        return null;
    }

    //@OnlyIn(Dist.CLIENT)
    public static class ClientHandler
    {
        @SuppressWarnings("resource")

        public static void handle(ReponseClientPacket msg, Supplier<NetworkEvent.Context> ctx)
        {
            if (msg.actionResponseClient != null) {
                if (msg.actionResponseClient.equals(ActionResponseClient.UPDATE_TEXT_INVENTORY)) {
                    RenderOverlay.inventoryType = msg.value;
                }
                if (msg.actionResponseClient.equals(ActionResponseClient.UPDATE_METIER_MINER)) {
                    MetierScreen.PrincipalScreen.xp_miner = msg.value;
                }
                if (msg.actionResponseClient.equals(ActionResponseClient.UPDATE_METIER_LUMBERJACK)) {
                    MetierScreen.PrincipalScreen.xp_lumberjack = msg.value;
                }
                if (msg.actionResponseClient.equals(ActionResponseClient.UPDATE_METIER_CHASSEUR)) {
                    MetierScreen.PrincipalScreen.xp_chasseur = msg.value;
                }
                if (msg.actionResponseClient.equals(ActionResponseClient.SHOW_TOAST)) {
                    if (Minecraft.getInstance().getToastGui() instanceof JobsToastGui) {
                        System.out.println(msg.value);
                        System.out.println(Integer.parseInt(msg.value));
                        JobsToastGui toastGui = (JobsToastGui) Minecraft.getInstance().getToastGui();
                        System.out.println(toastGui);
                        toastGui.updateJobsToast(new JobsToast(Integer.parseInt(msg.value)));
                    }
                }
            }

            ctx.get().setPacketHandled(true);
        }
    }

    public static class ServerHandler
    {
        public static void handle(ReponseClientPacket msg, Supplier<NetworkEvent.Context> ctx)
        {

        }
    }

}
