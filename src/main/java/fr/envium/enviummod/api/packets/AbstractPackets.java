package fr.envium.enviummod.api.packets;

import fr.envium.enviummod.core.packets.GetInventoryTypePacket;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class AbstractPackets {

    public AbstractPackets() {

    }

    public static void encode(GetInventoryTypePacket packet, PacketBuffer buffer)
    {

    }

    public static AbstractPackets decode(PacketBuffer buffer) {
        return null;
    }

    public static void handle(GetInventoryTypePacket packet, Supplier<NetworkEvent.Context> ctx)
    {

    }

}
