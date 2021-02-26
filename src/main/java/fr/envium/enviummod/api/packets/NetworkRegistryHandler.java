package fr.envium.enviummod.api.packets;

import fr.envium.enviummod.References;
import fr.envium.enviummod.core.packets.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;

public class NetworkRegistryHandler
{
    private static final String PROTOCOL_VERSION = String.valueOf(1);

    public static final SimpleChannel network = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(References.MODID, "main_channel"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    /*public static void registerClientMessages()
    {
        int id = 0;

        //network.registerMessage(id++, PacketUpdateTileEntityData.class, PacketUpdateTileEntityData::encode, PacketUpdateTileEntityData::decode, PacketUpdateTileEntityData.ClientHandler::handle);

        network.messageBuilder(ReponseClientPacket.class, id++).encoder(ReponseClientPacket::encode).decoder(ReponseClientPacket::decode).consumer(ReponseClientPacket.ClientHandler::handle).add();
        network.registerMessage(id++, ReponseClientPacket.class, ReponseClientPacket::encode, ReponseClientPacket::decode, ReponseClientPacket.ClientHandler::handle, distClient());
        network.registerMessage(id++, PacketUpdateTileEntityData.class, PacketUpdateTileEntityData::encode, PacketUpdateTileEntityData::decode, PacketUpdateTileEntityData.ClientHandler::handle, distClient());
        network.registerMessage(id++, InventoryPacket.class, InventoryPacket::encode, InventoryPacket::decode, InventoryPacket::handle, distServer());
        network.registerMessage(id++, GetInventoryTypePacket.class, GetInventoryTypePacket::encode, GetInventoryTypePacket::decode, GetInventoryTypePacket::handle, distServer());
    }*/

    private static Optional<NetworkDirection> distClient() { return Optional.of(NetworkDirection.PLAY_TO_CLIENT);}
    private static Optional<NetworkDirection> distServer() { return Optional.of(NetworkDirection.PLAY_TO_SERVER); }

    /*public static void registerServerMessages()
    {
        int id = 100;

        network.registerMessage(id++, ReponseClientPacket.class, ReponseClientPacket::encode, ReponseClientPacket::decode, ReponseClientPacket.ServerHandler::handle, distClient());
        network.registerMessage(id++, PacketUpdateTileEntityData.class, PacketUpdateTileEntityData::encode, PacketUpdateTileEntityData::decode, PacketUpdateTileEntityData.ServerHandler::handle, distClient());
        network.registerMessage(id++, InventoryPacket.class, InventoryPacket::encode, InventoryPacket::decode, InventoryPacket::handle, distServer());
        network.registerMessage(id++, GetInventoryTypePacket.class, GetInventoryTypePacket::encode, GetInventoryTypePacket::decode, GetInventoryTypePacket::handleServer, distServer());

        //network.messageBuilder(MySimplePacket.class, id++).encoder(MySimplePacket::encode).decoder(MySimplePacket::decode).consumer(MySimplePacket::handle).add();

        //network.registerMessage(id++, PacketUpdateTileEntityData.class, PacketUpdateTileEntityData::encode, PacketUpdateTileEntityData::decode, PacketUpdateTileEntityData.ClientHandler::handle, distServer());

    }*/

    public static void registerMessage() {
        int id = 0;

        network.registerMessage(id++, ReponseClientPacket.class, ReponseClientPacket::encode, ReponseClientPacket::decode, ReponseClientPacket.ClientHandler::handle, distClient());
        network.registerMessage(id++, PacketUpdateTileEntityData.class, PacketUpdateTileEntityData::encode, PacketUpdateTileEntityData::decode, PacketUpdateTileEntityData.ClientHandler::handle, distClient());
        network.registerMessage(id++, InventoryPacket.class, InventoryPacket::encode, InventoryPacket::decode, InventoryPacket::handle, distServer());
        network.registerMessage(id++, GetInventoryTypePacket.class, GetInventoryTypePacket::encode, GetInventoryTypePacket::decode, GetInventoryTypePacket::handle, distServer());
        network.registerMessage(id++, MetierPackets.class, MetierPackets::encode, MetierPackets::decode, MetierPackets::handle, distServer());
    }
}
