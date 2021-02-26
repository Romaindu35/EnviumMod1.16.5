package fr.envium.enviummod.core.items;

import fr.envium.enviummod.api.tab.EnviumTab;
import net.minecraft.item.Item;

public class EnviumLingot extends Item {
    public EnviumLingot() {
        super(new Item.Properties().group(EnviumTab.TAB));
        //setRegistryName("envium_lingot");
    }
}
