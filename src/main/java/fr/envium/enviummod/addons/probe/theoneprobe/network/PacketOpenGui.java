package fr.envium.enviummod.addons.probe.theoneprobe.network;

import fr.envium.enviummod.addons.probe.theoneprobe.gui.GuiConfig;
import fr.envium.enviummod.addons.probe.theoneprobe.gui.GuiNote;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketOpenGui {

    public static int GUI_CONFIG = 0;
    public static int GUI_NOTE = 1;

    private final int gui;

    public PacketOpenGui(PacketBuffer buf) {
        gui = buf.readInt();
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeInt(gui);
    }

    public PacketOpenGui(int gui) {
        this.gui = gui;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        if (gui == GUI_CONFIG) {
            ctx.get().enqueueWork(GuiConfig::open);
        } else {
            ctx.get().enqueueWork(GuiNote::open);
        }
        ctx.get().setPacketHandled(true);
    }
}
