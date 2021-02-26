package fr.envium.enviummod.core.packets;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MySimplePacket {

    private int value;

    public MySimplePacket(int value)
    {
        this.value = value;
    }

    public static void encode(MySimplePacket packet, PacketBuffer buffer)
    {
        buffer.writeInt(packet.value);
    }

    public static MySimplePacket decode(PacketBuffer buffer)
    {
        int value = buffer.readInt();
        MySimplePacket instance = new MySimplePacket(value);
        return instance;
    }

    public static void handle(MySimplePacket packet, Supplier<NetworkEvent.Context> ctx)
    {
        System.out.println(packet.value);
        ctx.get().setPacketHandled(true);
    }

}
