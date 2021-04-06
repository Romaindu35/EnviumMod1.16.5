package fr.envium.enviummod.core.packets;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import fr.envium.enviummod.api.container.ContainerData;
import fr.envium.enviummod.api.packets.DistExecutorMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

public class PacketUpdateTileEntityData {

    private int windowId;
    private Map<String, Integer> map;

    public PacketUpdateTileEntityData() {}

    public PacketUpdateTileEntityData(int windowId, Map<String, Integer> map)
    {
        this.windowId = windowId;
        this.map = map;
    }

    public static void encode(PacketUpdateTileEntityData msg, PacketBuffer buf)
    {
        buf.writeInt(msg.windowId);

        buf.writeInt(msg.map.size());

        for (String name : msg.map.keySet())
        {
            buf.writeUtf(name, 32);
            buf.writeInt(msg.map.get(name));
        }
    }

    public static PacketUpdateTileEntityData decode(PacketBuffer buf)
    {
        int windowId = buf.readInt();

        int size = buf.readInt();

        Map<String, Integer> map = new HashMap<String, Integer>(size);

        for (int i = size; i > 0; i--)
        {
            String name = buf.readUtf();

            int data = buf.readInt();
            map.put(name, data);
        }

        return new PacketUpdateTileEntityData(windowId, map);
    }

    public static class ClientHandler
    {
        @SuppressWarnings("resource")
        public static void handle(PacketUpdateTileEntityData msg, Supplier<NetworkEvent.Context> ctx)
        {

            PlayerEntity player = DistExecutor.safeCallWhenOn(Dist.CLIENT, () -> DistExecutorMod::getPlayerEntity);
            if (player != null) {
                if (player.containerMenu.containerId == msg.windowId) {
                    if (player.containerMenu instanceof ContainerData) {
                        ContainerData container = ((ContainerData) player.containerMenu);
                        container.setClientData(msg.map);
                    }
                }
            }

            ctx.get().setPacketHandled(true);
        }
    }

    public static class ServerHandler
    {
        public static void handle(PacketUpdateTileEntityData msg, Supplier<NetworkEvent.Context> ctx)
        {

        }
    }

}
