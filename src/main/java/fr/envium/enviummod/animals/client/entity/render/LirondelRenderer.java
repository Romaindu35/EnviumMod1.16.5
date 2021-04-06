package fr.envium.enviummod.animals.client.entity.render;

import fr.envium.enviummod.References;
import fr.envium.enviummod.animals.client.entity.model.LirondelModel;
import fr.envium.enviummod.animals.entity.Lirondel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class LirondelRenderer extends MobRenderer<Lirondel, LirondelModel<Lirondel>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/lirondel.png");

    public LirondelRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new LirondelModel<>(), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Lirondel entity) {
        return TEXTURE;
    }

}
