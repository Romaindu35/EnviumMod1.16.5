package fr.envium.enviummod.core.packets;

import fr.envium.enviummod.api.packets.AbstractPackets;
import fr.envium.enviummod.api.packets.NetworkRegistryHandler;
import fr.envium.enviummod.core.server.BDD;
import fr.envium.enviummod.core.server.enums.ActionResponseClient;
import fr.envium.enviummod.core.server.enums.InventoryType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

public class GetInventoryTypePacket extends AbstractPackets {

    private String player;
    private PlayerEntity playerEntity;

    public GetInventoryTypePacket(String player)
    {
        this.player = player;
        //PlayerList playerList = Minecraft.getInstance().getIntegratedServer().getPlayerList();
        //PlayerList playerList = MinecraftServer;
        /*for (PlayerEntity playerEntity : playerList.getPlayers()) {
            if (playerEntity.getName().getString().equals(this.player)) {
                this.playerEntity = playerEntity;
            }
        }*/
    }

    public static void encode(GetInventoryTypePacket packet, PacketBuffer buffer)
    {
        //buffer.writeString(packet.player);
    }

    public static GetInventoryTypePacket decode(PacketBuffer buffer)
    {
        //String playerEntity = buffer.readString();

        GetInventoryTypePacket instance = new GetInventoryTypePacket("");
        return instance;
    }

    public static void handle(GetInventoryTypePacket packet, Supplier<NetworkEvent.Context> ctx)
    {
        ServerPlayerEntity serverPlayerEntity = ctx.get().getSender();
        BDD bdd = new BDD();
        if (serverPlayerEntity != null) {
            InventoryType inventoryType = bdd.getInventoryType(serverPlayerEntity, false);
            System.out.println(inventoryType);
            NetworkRegistryHandler.network.send(PacketDistributor.PLAYER.with(
                    () -> serverPlayerEntity),
                    new ReponseClientPacket(ActionResponseClient.UPDATE_TEXT_INVENTORY, inventoryType.name()));
        }
        ctx.get().setPacketHandled(true);
    }

    public static void handleServer(GetInventoryTypePacket packet, Supplier<NetworkEvent.Context> ctx)
    {

    }
}
