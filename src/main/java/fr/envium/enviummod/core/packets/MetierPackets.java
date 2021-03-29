package fr.envium.enviummod.core.packets;

import fr.envium.enviummod.api.packets.AbstractPackets;
import fr.envium.enviummod.api.packets.NetworkRegistryHandler;
import fr.envium.enviummod.core.server.enums.ActionResponseClient;
import fr.envium.enviummod.core.jobs.JobsEnum;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Map;
import java.util.function.Supplier;

public class MetierPackets extends AbstractPackets {

    private String player;

    public MetierPackets()
    {
        this.player = player;
    }

    public static void encode(MetierPackets packet, PacketBuffer buffer)
    {
    }

    public static MetierPackets decode(PacketBuffer buffer)
    {
        MetierPackets instance = new MetierPackets();
        return instance;
    }

    public static void handle(MetierPackets packet, Supplier<NetworkEvent.Context> ctx)
    {
        /*ServerPlayerEntity serverPlayerEntity = ctx.get().getSender();
        BDD bdd = new BDD();
        if (serverPlayerEntity != null) {
            Map<JobsEnum, Integer> inventoryType = bdd.getJobs(serverPlayerEntity);
            System.out.println(inventoryType);
            NetworkRegistryHandler.network.send(PacketDistributor.PLAYER.with(
                    () -> serverPlayerEntity),
                    new ReponseClientPacket(ActionResponseClient.UPDATE_METIER_MINER, String.valueOf(inventoryType.get(JobsEnum.MINER))));
            NetworkRegistryHandler.network.send(PacketDistributor.PLAYER.with(
                    () -> serverPlayerEntity),
                    new ReponseClientPacket(ActionResponseClient.UPDATE_METIER_LUMBERJACK, String.valueOf(inventoryType.get(JobsEnum.LUMBERJACK))));
            NetworkRegistryHandler.network.send(PacketDistributor.PLAYER.with(
                    () -> serverPlayerEntity),
                    new ReponseClientPacket(ActionResponseClient.UPDATE_METIER_CHASSEUR, String.valueOf(inventoryType.get(JobsEnum.CHASSEUR))));
        }*/
        ctx.get().setPacketHandled(true);
    }

}
