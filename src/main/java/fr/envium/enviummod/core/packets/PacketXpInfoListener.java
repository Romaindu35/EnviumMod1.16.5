package fr.envium.enviummod.core.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketXpInfoListener {

    public PacketXpInfoListener()
    {

    }

    public static void encode(PacketXpInfoListener packet, PacketBuffer buffer)
    {

    }

    public static PacketXpInfoListener decode(PacketBuffer buffer)
    {
        PacketXpInfoListener instance = new PacketXpInfoListener();
        return instance;
    }

    public static void handle(PacketXpInfoListener packet, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().setPacketHandled(true);
    }

}
