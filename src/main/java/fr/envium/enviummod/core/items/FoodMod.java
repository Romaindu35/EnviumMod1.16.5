package fr.envium.enviummod.core.items;

import fr.envium.enviummod.api.tab.EnviumTab;
import net.minecraft.item.Food;
import net.minecraft.item.Item;

public class FoodMod extends Item {

	public FoodMod(int hunger, float saturation) {
		super(new Item.Properties().group(EnviumTab.TAB).food(new Food.Builder().hunger(hunger).saturation(saturation).build()));
	}
}
