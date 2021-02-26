package fr.envium.enviummod.api.integration.jei;

import fr.envium.enviummod.References;
import mezz.jei.api.IModPlugin;
import net.minecraft.util.ResourceLocation;

@mezz.jei.api.JeiPlugin
public class JeiPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(References.MODID, "default");
    }

    public JeiPlugin() {

    }

}
