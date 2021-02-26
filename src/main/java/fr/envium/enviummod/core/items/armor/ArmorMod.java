package fr.envium.enviummod.core.items.armor;

import fr.envium.enviummod.References;
import fr.envium.enviummod.api.init.RegisterItem;
import fr.envium.enviummod.api.tab.EnviumTab;
import fr.envium.enviummod.core.other.ModMaterial;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class ArmorMod extends ArmorItem {

	public ArmorMod(EquipmentSlotType slot) {
		super(ModMaterial.EnviumArmorMaterial.ENVIUM, slot, new Item.Properties().group(EnviumTab.TAB));
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		if (stack.getItem() == RegisterItem.envium_leggings) {
			return References.MODID + ":textures/models/armor/envium_leggings.png";
		} else if (stack.getItem() == RegisterItem.envium_helmet || stack.getItem() == RegisterItem.envium_chestplate
				|| stack.getItem() == RegisterItem.envium_boots) {
			return References.MODID + ":textures/models/armor/envium_armor.png";
		} else {
			return null;
		}
	}

	@Override
	public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
		if (stack.getItem() == RegisterItem.envium_helmet) {
			player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 11));
		}
	}
}
