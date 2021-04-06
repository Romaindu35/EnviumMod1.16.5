package fr.envium.enviummod.core.packets;

import net.minecraftforge.fml.network.NetworkEvent;

public class BukkitPacketListener {

    public static void packetListener(NetworkEvent.ClientCustomPayloadEvent event) {
        System.out.println("packet recu client");
    }

    public static void packetListener2(NetworkEvent.ServerCustomPayloadEvent event) {
        System.out.println("packet recu server");
    }
}
