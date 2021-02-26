package fr.envium.enviummod.animals.client.entity.render;

import fr.envium.enviummod.References;
import fr.envium.enviummod.animals.client.entity.model.ToucanModel;
import fr.envium.enviummod.animals.entity.Toucan;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class ToucanRenderer extends MobRenderer<Toucan, ToucanModel<Toucan>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/toucan.png");

    public ToucanRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ToucanModel<>(), 0.5F);
    }

    @Override
    public ResourceLocation getEntityTexture(Toucan entity) {
        return TEXTURE;
    }
}
