package fr.envium.enviummod.core.packets;

import fr.envium.enviummod.core.client.render.RenderOverlay;
import fr.envium.enviummod.core.server.BDD;
import fr.envium.enviummod.core.server.enums.InventoryType;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.management.PlayerList;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PermissionsPacket {

    private String player;
    private PlayerEntity playerEntity;

    public PermissionsPacket(String player)
    {
        this.player = player;
        PlayerList playerList = Minecraft.getInstance().getIntegratedServer().getPlayerList();
        for (PlayerEntity playerEntity : playerList.getPlayers()) {
            if (playerEntity.getName().getString().equals(this.player)) {
                this.playerEntity = playerEntity;
            }
        }
    }

    public static void encode(PermissionsPacket packet, PacketBuffer buffer)
    {
        buffer.writeString(packet.player);
    }

    public static PermissionsPacket decode(PacketBuffer buffer)
    {
        String playerEntity = buffer.readString();

        PermissionsPacket instance = new PermissionsPacket(playerEntity);
        return instance;
    }

    public static void handle(PermissionsPacket packet, Supplier<NetworkEvent.Context> ctx)
    {
        BDD bdd = new BDD();
        if (packet.playerEntity != null) {
            InventoryType inventoryType = bdd.getInventoryType(packet.playerEntity, false);
            RenderOverlay.inventoryType = inventoryType.name();
        }
        ctx.get().setPacketHandled(true);
    }

}
