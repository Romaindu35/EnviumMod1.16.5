package fr.envium.enviummod.addons.probe.theoneprobe.items;

import fr.envium.enviummod.addons.probe.theoneprobe.TheOneProbe;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TheOneProbe.MODID);

    private static final TopArmorMaterial materialDiamondHelmet = new TopArmorMaterial("diamond_helmet_probe", 33, new int[]{3, 6, 8, 3}, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, null);
    private static final TopArmorMaterial materialGoldHelmet = new TopArmorMaterial("gold_helmet_probe", 7, new int[]{1, 3, 5, 2}, 25, SoundEvents.ARMOR_EQUIP_GOLD, 0.0F, null);
    private static final TopArmorMaterial materialIronHelmet = new TopArmorMaterial("iron_helmet_probe", 15, new int[]{2, 5, 6, 2}, 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, null);

    public static String PROBETAG = "theoneprobe";
    public static String PROBETAG_HAND = "theoneprobe_hand";

    public static final RegistryObject<Item> PROBE =  ITEMS.register("probe", () -> new Probe());
    public static final RegistryObject<Item> CREATIVE_PROBE =  ITEMS.register("creativeprobe", () -> new CreativeProbe());
    public static final RegistryObject<Item> PROBE_NOTE =  ITEMS.register("probenote", () -> new ProbeNote());

    public static final RegistryObject<Item> diamond_helmet_probe =  ITEMS.register("diamond_helmet_probe", () -> makeHelmet(materialDiamondHelmet));
    public static final RegistryObject<Item> gold_helmet_probe =  ITEMS.register("gold_helmet_probe", () -> makeHelmet(materialGoldHelmet));
    public static final RegistryObject<Item> iron_helmet_probe =  ITEMS.register("iron_helmet_probe", () -> makeHelmet(materialIronHelmet));

    private static Item makeHelmet(TopArmorMaterial material) {
        Item item = new ArmorItem(material, EquipmentSlotType.HEAD, new Item.Properties()
                .tab(TheOneProbe.tabProbe));
        return item;
    }

    public static boolean isProbeInHand(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        /*if (stack.getItem() == probe || stack.getItem() == creativeProbe) {
            return true;
        }*/
        if (stack.getTag() == null) {
            return false;
        }
        return stack.getTag().contains(PROBETAG_HAND);
    }

    private static boolean isProbeHelmet(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        if (stack.getTag() == null) {
            return false;
        }
        return stack.getTag().contains(PROBETAG);
    }

    public static boolean hasAProbeSomewhere(PlayerEntity player) {
        return hasProbeInHand(player, Hand.MAIN_HAND) || hasProbeInHand(player, Hand.OFF_HAND) || hasProbeInHelmet(player);
    }

    private static boolean hasProbeInHand(PlayerEntity player, Hand hand) {
        ItemStack item = player.getItemInHand(hand);
        return isProbeInHand(item);
    }

    private static boolean hasProbeInHelmet(PlayerEntity player) {
        ItemStack helmet = player.inventory.getItem(36+3);
        return isProbeHelmet(helmet);
    }

}