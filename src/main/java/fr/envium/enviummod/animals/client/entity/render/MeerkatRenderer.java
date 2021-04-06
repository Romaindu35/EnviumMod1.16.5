package fr.envium.enviummod.animals.client.entity.render;

import fr.envium.enviummod.References;
import fr.envium.enviummod.animals.client.entity.model.MeerkatModel;
import fr.envium.enviummod.animals.client.entity.model.ToucanModel;
import fr.envium.enviummod.animals.entity.Meerkat;
import fr.envium.enviummod.animals.entity.Toucan;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class MeerkatRenderer extends MobRenderer<Meerkat, MeerkatModel<Meerkat>> {

        protected static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/entity/meerkat.png");

        public MeerkatRenderer(EntityRendererManager renderManagerIn) {
            super(renderManagerIn, new MeerkatModel(), 0.5F);
        }

        @Override
        public ResourceLocation getTextureLocation(Meerkat entity) {
            return TEXTURE;
        }
    }
