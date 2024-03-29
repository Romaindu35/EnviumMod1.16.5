package fr.envium.enviummod.core.other;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.HashMap;
import java.util.Map.Entry;

public class RecipesBlockFurnaceMod {

	private static final HashMap <ItemStack[], ItemStack>recipes = new HashMap<ItemStack[], ItemStack>();
	static {
	    addRecipe(Items.APPLE, Items.BAKED_POTATO);
	}
	
	private static void addRecipe(Item ingredient1, Item resultat1) {
	    addRecipe(new ItemStack(ingredient1), new ItemStack(resultat1));
	}
	 
	private static void addRecipe(ItemStack ingredient1, ItemStack resultat1) {
	    recipes.put(new ItemStack[]{ingredient1}, resultat1);
	}
	
	private static boolean areKeysEqual(ItemStack[] key1, ItemStack[] key2) {
	    if(key1.length != key2.length) return false;
	 
	    for(int i = 0; i < key1.length; i++) {
	        ItemStack s1 = key1[i];
	        ItemStack s2 = key2[i];
	        if(s1.isEmpty() && !s2.isEmpty()) return false;
	        if(!s1.isEmpty() && s2.isEmpty()) return false;
	        if(s1.getItem() != s2.getItem()) return false;
	        if(s1.getDamageValue() != s2.getDamageValue()) return false;
	    }
	    return true;
	}
	
	public static ItemStack getRecipeResult(ItemStack[] ingredients) {
		for (Entry<ItemStack[], ItemStack> entry : recipes.entrySet()) {
			if (areKeysEqual(entry.getKey(), ingredients)) {
				return entry.getValue();
			}
		}
	    return null;
	}
}
