package fr.envium.enviummod.animals.client.entity.render;

import fr.envium.enviummod.References;
import fr.envium.enviummod.animals.client.entity.model.MarmotModel;
import fr.envium.enviummod.animals.entity.Marmot;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class MarmotRenderer extends MobRenderer<Marmot, MarmotModel<Marmot>> {

    protected static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/marmot.png");

    public MarmotRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new MarmotModel<>(), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Marmot entity) {
        return TEXTURE;
    }
}
