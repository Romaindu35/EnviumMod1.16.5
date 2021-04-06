package fr.envium.enviummod.api.tab;

import fr.envium.enviummod.api.init.RegisterItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class EnviumTab {

    /*public static final ItemGroup TAB = new EnviumTab("envium_creative_tabs");

    public EnviumTab(String label) {
        super(label);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(RegisterItem.envium_lingot);
    }*/


    public static final  ItemGroup TAB = new ItemGroup("envium_creative_tabs") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RegisterItem.envium_lingot);
        }
    };

    public static final  ItemGroup TAB_ANIMAL = new ItemGroup("envium_creative_tabs_animal") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(RegisterItem.marmot_spawn_eggs);
        }
    };
}
