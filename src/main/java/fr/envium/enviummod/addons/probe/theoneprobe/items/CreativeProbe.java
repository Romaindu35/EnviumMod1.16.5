package fr.envium.enviummod.addons.probe.theoneprobe.items;

import fr.envium.enviummod.addons.probe.theoneprobe.TheOneProbe;
import net.minecraft.item.Item;

import net.minecraft.item.Item.Properties;

public class CreativeProbe extends Item {

    public CreativeProbe() {
        super(new Properties()
                .stacksTo(1)
                .tab(TheOneProbe.tabProbe)
        );
    }

}
