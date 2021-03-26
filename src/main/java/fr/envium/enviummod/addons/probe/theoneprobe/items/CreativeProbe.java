package fr.envium.enviummod.addons.probe.theoneprobe.items;

import fr.envium.enviummod.addons.probe.theoneprobe.TheOneProbe;
import net.minecraft.item.Item;

public class CreativeProbe extends Item {

    public CreativeProbe() {
        super(new Properties()
                .maxStackSize(1)
                .group(TheOneProbe.tabProbe)
        );
    }

}
