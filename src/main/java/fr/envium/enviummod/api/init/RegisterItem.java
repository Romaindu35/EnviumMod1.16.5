package fr.envium.enviummod.api.init;

import fr.envium.enviummod.References;
import fr.envium.enviummod.core.items.*;
import fr.envium.enviummod.core.items.armor.ArmorMod;
import fr.envium.enviummod.core.items.tools.*;
import fr.envium.enviummod.api.tab.EnviumTab;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(References.MODID)
public class RegisterItem {

    public static Item envium_lingot, envium_pickaxe, envium_sword, envium_shovel, envium_hoe, envium_axe,
            envium_hammer, envium_helmet, envium_chestplate, envium_leggings, envium_boots, frites,
            frites_citrouille, envium_shard_random, envium_shard, envium_shard_quantity, chest_explorer,
            toucan_spawn_eggs, lirondel_spawn_eggs, marmot_spawn_eggs, meerkat_spawn_eggs,
            hang_glider;

    public static void init() {
        envium_lingot = new EnviumLingot().setRegistryName("envium_lingot");

        envium_pickaxe = new ToolsModPickaxe().setRegistryName("envium_pickaxe");
        envium_sword = new ToolsModSword().setRegistryName("envium_sword");
        envium_shovel = new ToolsModShovel().setRegistryName("envium_shovel");
        envium_hoe = new ToolsModHoe().setRegistryName("envium_hoe");
        envium_axe = new ToolsModAxe().setRegistryName("envium_axe");

        envium_helmet = new ArmorMod(EquipmentSlotType.HEAD).setRegistryName("envium_helmet");
        envium_chestplate = new ArmorMod(EquipmentSlotType.CHEST).setRegistryName("envium_chestplate");
        envium_leggings = new ArmorMod(EquipmentSlotType.LEGS).setRegistryName("envium_leggings");
        envium_boots = new ArmorMod(EquipmentSlotType.FEET).setRegistryName("envium_boots");

        frites = new FoodMod(4, 1.5F).setRegistryName("frites");
        frites_citrouille = new FoodMod(8, 1.0F).setRegistryName("frites_citrouille");

        envium_shard_random = new EnviumShardRandom().setRegistryName("envium_shard_random");
        envium_shard = new EnviumShard().setRegistryName("envium_shard");
        envium_shard_quantity = new EnviumShard().setRegistryName("envium_shard_quantity");

        toucan_spawn_eggs = new ModSpawnEgg(RegisterEntity.TOUCAN_ENTITY, 0xFF329F, 0x16777119, new Item.Properties()
                .group(EnviumTab.TAB)
                .maxStackSize(16))
                .setRegistryName("toucan_spawn_eggs");

        lirondel_spawn_eggs = new ModSpawnEgg(RegisterEntity.LIRONDEL_ENTITY, 0xFF329F, 0x16777119, new Item.Properties()
                .group(EnviumTab.TAB)
                .maxStackSize(16))
                .setRegistryName("lirondel_spawn_eggs");

        marmot_spawn_eggs = new ModSpawnEgg(RegisterEntity.MARMOT_ENTITY, 0xFF329F, 0x16777119, new Item.Properties()
                .group(EnviumTab.TAB)
                .maxStackSize(16))
                .setRegistryName("marmot_spawn_eggs");

        meerkat_spawn_eggs = new ModSpawnEgg(RegisterEntity.MEERKAT_ENTITY, 0xFF329F, 0x16777119, new Item.Properties()
                .group(EnviumTab.TAB)
                .maxStackSize(16))
                .setRegistryName("meerkat_spawn_eggs");

        hang_glider = new HangGlider().setRegistryName("hang_glider");
    }

    @SubscribeEvent
    public static void registerBlocksItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                envium_lingot,

                envium_pickaxe,
                envium_sword,
                envium_shovel,
                envium_hoe,
                envium_axe,

                envium_helmet,
                envium_chestplate,
                envium_leggings,
                envium_boots,

                frites,
                frites_citrouille,

                envium_shard_random,
                envium_shard,
                envium_shard_quantity,

                toucan_spawn_eggs,
                lirondel_spawn_eggs,
                marmot_spawn_eggs,
                meerkat_spawn_eggs,

                hang_glider
        );
    }

}
