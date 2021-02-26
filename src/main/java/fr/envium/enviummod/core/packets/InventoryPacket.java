package fr.envium.enviummod.core.packets;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import fr.envium.enviummod.api.packets.NetworkRegistryHandler;
import fr.envium.enviummod.core.server.BDD;
import fr.envium.enviummod.core.server.enums.ActionResponseClient;
import fr.envium.enviummod.core.server.enums.InventoryAction;
import fr.envium.enviummod.core.server.enums.InventoryType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class InventoryPacket {

    private String player;
    private PlayerEntity playerEntity;
    private InventoryAction inventoryAction;

    public InventoryPacket(String player, InventoryAction inventoryAction)
    {
       /* this.player = player;
        PlayerList playerList = Minecraft.getInstance().getIntegratedServer().getPlayerList();
        for (PlayerEntity playerEntity : playerList.getPlayers()) {
            if (playerEntity.getName().getString().equals(this.player)) {*/
                //this.playerEntity = playerEntity;
                this.inventoryAction = inventoryAction;
            /*}
        }*/
    }

    public static void encode(InventoryPacket packet, PacketBuffer buffer)
    {
        buffer.writeEnumValue(packet.inventoryAction);
    }

    public static InventoryPacket decode(PacketBuffer buffer)
    {
        //String playerEntity = buffer.readString();
        /*String action = buffer.readString();
        InventoryAction inventoryAction = InventoryAction.valueOf(action);*/
        InventoryAction inventoryAction = buffer.readEnumValue(InventoryAction.class);
        System.out.println(inventoryAction);
        //InventoryAction action = InventoryAction.SAVE_READ;

        /*switch (action.toString()) {
            case "SAVE":
                inventoryAction = InventoryAction.SAVE;
                break;
            case "READ":
                inventoryAction = InventoryAction.READ;
                break;
            case "SAVE_READ":
                inventoryAction = InventoryAction.SAVE_READ;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }*/

        InventoryPacket instance = new InventoryPacket("", inventoryAction);
        return instance;
    }

    public static void handle(InventoryPacket packet, Supplier<NetworkEvent.Context> ctx)
    {
        BDD bdd = new BDD();
        packet.playerEntity = ctx.get().getSender();
        System.out.println("dezcqbneiouc,izoc,zrc");
        if (packet.playerEntity != null) {
            System.out.println(packet.inventoryAction);
            if (packet.inventoryAction.equals(InventoryAction.SAVE) || packet.inventoryAction.equals(InventoryAction.SAVE_READ)) {
                saveInventory(packet);
            }
            if (packet.inventoryAction.equals(InventoryAction.READ) || packet.inventoryAction.equals(InventoryAction.SAVE_READ)) {
                changeInventory(packet);
            }
            List<String> permissions = new ArrayList();
            permissions.add("truc");
            //permissions.add("machin");
            //bdd.addPermissions(packet.playerEntity, permissions);
            bdd.removePermissions(packet.playerEntity, permissions);



            if (bdd.getInventoryType(packet.playerEntity, false).equals(InventoryType.STAFF)) {
                packet.playerEntity.setGameType(GameType.CREATIVE);
            } else {
                packet.playerEntity.setGameType(GameType.SURVIVAL);
            }
            //RenderOverlay.change = true;
            NetworkRegistryHandler.network.send(PacketDistributor.PLAYER.with(
                    () -> (ServerPlayerEntity) packet.playerEntity),
                    new ReponseClientPacket(ActionResponseClient.UPDATE_TEXT_INVENTORY, bdd.getInventoryType(packet.playerEntity, false).toString()));
        }
        ctx.get().setPacketHandled(true);
    }

    public static void saveInventory(InventoryPacket packet) {
        BDD bdd = new BDD();
        final List<String> list = new ArrayList<>();

        for (int i =0 ; i < packet.playerEntity.inventory.mainInventory.size(); i++) {
            ItemStack itemStack = packet.playerEntity.inventory.mainInventory.get(i);
            String nbt = itemStack.serializeNBT().toString();
            list.add(nbt + "#");
        }
        for (int i =0 ; i < packet.playerEntity.inventory.armorInventory.size(); i++) {
            ItemStack itemStack = packet.playerEntity.inventory.armorInventory.get(i);
            String nbt = itemStack.serializeNBT().toString();
            list.add(nbt + "#");
        }
        bdd.addInventory(bdd.getInventoryType(packet.playerEntity, true), list, packet.playerEntity);

        System.out.println("inventory save");
    }

    public static void changeInventory(InventoryPacket packet) {
        BDD bdd = new BDD();

        List<String> stringList = bdd.getInventory(packet.playerEntity, true);
        System.out.println(stringList);
        for (int i =0 ; i < packet.playerEntity.inventory.mainInventory.size(); i++) {
            String coupountString = stringList.get(i);

            PlayerInventory playerInventory = packet.playerEntity.inventory;
            CompoundNBT compoundNBT = null;
            try {
                compoundNBT = JsonToNBT.getTagFromJson(coupountString);
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
            }
            ItemStack itemStack = ItemStack.read(compoundNBT);
            playerInventory.setInventorySlotContents(i, itemStack);
        }
        for (int i = packet.playerEntity.inventory.mainInventory.size();
             i < packet.playerEntity.inventory.mainInventory.size()+packet.playerEntity.inventory.armorInventory.size(); i++) {
            String coupountString = stringList.get(i);

            PlayerInventory playerInventory = packet.playerEntity.inventory;
            CompoundNBT compoundNBT = null;
            try {
                compoundNBT = JsonToNBT.getTagFromJson(coupountString);
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
            }
            ItemStack itemStack = ItemStack.read(compoundNBT);
            if (i == 38) {
                playerInventory.setInventorySlotContents(39, itemStack);
            } else if (i == 39) {
                playerInventory.setInventorySlotContents(38, itemStack);
            } else {
                playerInventory.setInventorySlotContents(i, itemStack);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientHandle(InventoryPacket packet, Supplier<NetworkEvent.Context> ctx)
    {

    }

}
