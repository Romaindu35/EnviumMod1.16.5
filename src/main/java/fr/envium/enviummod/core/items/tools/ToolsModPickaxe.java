package fr.envium.enviummod.core.items.tools;

import fr.envium.enviummod.api.tab.EnviumTab;
import fr.envium.enviummod.core.other.ModMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;

public class ToolsModPickaxe extends PickaxeItem {

	public ToolsModPickaxe() {
		super(ModMaterial.EnviumMaterial.ENVIUM_MATERIAL, 5, 8.0F, new Item.Properties().tab(EnviumTab.TAB));
	}
}
