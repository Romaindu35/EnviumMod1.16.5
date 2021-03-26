package fr.envium.enviummod.core.items.tools;

import fr.envium.enviummod.api.tab.EnviumTab;
import fr.envium.enviummod.core.other.ModMaterial;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;

public class ToolsModHoe extends HoeItem
{
	public ToolsModHoe() {
		super(ModMaterial.EnviumMaterial.ENVIUM_MATERIAL, 8, 2.0F, new Item.Properties().group(EnviumTab.TAB));
	}
}
