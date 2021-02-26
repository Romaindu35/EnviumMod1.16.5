package fr.envium.enviummod.core.other;

public class MaterialMod {

/*
	public static final Material PILLOW = new Material(MaterialColor.GRAY_TERRACOTTA, false, true, false, false, true, true, true, PushReaction.NORMAL);
	public static final IItemTier ENVIUM_MATERIAL = MaterialMod.ENVIUM_MATERIAL;
	public static final IArmorMaterial ENVIUM_ARMOR_MATERIAL = MaterialMod.ENVIUM_ARMOR_MATERIAL;


	public enum ENVIUM_MATERIAL implements IItemTier {

		ENVIUM_MATERIAL(3, 2048, 10.0F, 5.0F, 8.0F, 25, () -> { return Ingredient.fromItems(RegisterItem.envium_lingot);});

		private final int harvestLevel;
		private final int maxUses;
		private final float efficiency;
		private final float attackDamage;
		private final float attackSpeed;
		private final int enchantability;

		ENVIUM_MATERIAL(int harvestLevel, int maxUses, float efficiency, float attackDamage, float attackSpeed, int enchantability, Supplier<Ingredient> repairMaterial) {
			this.harvestLevel = harvestLevel;
			this.maxUses = maxUses;
			this.efficiency = efficiency;
			this.attackDamage = attackDamage;
			this.attackSpeed = attackSpeed;
			this.enchantability = enchantability;
			new LazyValue<>(repairMaterial);
		}

		@Override
		public int getMaxUses() {
			return maxUses;
		}

		@Override
		public float getEfficiency() {
			return efficiency;
		}

		@Override
		public float getAttackDamage() {
			return attackDamage;
		}

		@Override
		public int getHarvestLevel() {
			return harvestLevel;
		}

		@Override
		public int getEnchantability() {
			return enchantability;
		}


		@Override
		public Ingredient getRepairMaterial() {
			return Ingredient.fromItems(ItemsMod.envium_lingot.get());
		}
	}




	;public enum ENVIUM_ARMOR_MATERIAL implements IArmorMaterial {

		ENVIUM_ARMOR_MATERIAL(2000, new int[] {3, 8, 6, 3}, 10, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, () -> { return Ingredient.fromItems(RegisterItem.envium_lingot);}, "enviumArmorMaterial", 2.0F);

		private final int durability;
		private final int[] damageReductionAmount;
		private final int enchantability;
		private final SoundEvent soundEvent;
		private final String name;
		private final float toughness;

		ENVIUM_ARMOR_MATERIAL(int durability, int[] damageReductionAmount, int enchantability, SoundEvent soundEvent, Supplier<Ingredient> repairMaterial, String name, float toughness) {
			this.durability = durability;
			this.damageReductionAmount = damageReductionAmount;
			this.enchantability = enchantability;
			this.soundEvent = soundEvent;
			new LazyValue<>(repairMaterial);
			this.name = name;
			this.toughness = toughness;
		}

		@Override
		@ParametersAreNonnullByDefault
		public int getDurability(EquipmentSlotType slotIn) {
			return durability;
		}

		@Override
		@ParametersAreNonnullByDefault
		public int getDamageReductionAmount(EquipmentSlotType slotIn) {
			return 0;
		}

		@Override
		public int getEnchantability() {
			return enchantability;
		}

		@Override
		@Nonnull
		public SoundEvent getSoundEvent() {
			return soundEvent;
		}

		@Override
		@Nonnull
		public Ingredient getRepairMaterial() {
			return Ingredient.fromItems(RegisterItem.envium_lingot);
		}

		@Override
		@Nonnull
		public String getName() {
			return name;
		}

		@Override
		public float getToughness() {
			return toughness;
		}
	}*/
}
