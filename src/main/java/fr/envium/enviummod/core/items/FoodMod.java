package fr.envium.enviummod.core.items;

import fr.envium.enviummod.api.tab.EnviumTab;
import net.minecraft.item.Food;
import net.minecraft.item.Item;

public class FoodMod extends Item {

	public FoodMod(int hunger, float saturation) {
		super(new Item.Properties().tab(EnviumTab.TAB).food(new Food.Builder().nutrition(hunger).saturationMod(saturation).build()));
	}
}
