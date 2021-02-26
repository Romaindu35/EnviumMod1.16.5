package fr.envium.enviummod.core.client.render;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.util.ResourceLocation;

public class RenderCustomPlayer extends LivingRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> {

    public RenderCustomPlayer(EntityRendererManager renderManager, boolean useSmallArms)
    {
        //PlayerModel<AbstractClientPlayerEntity> playerModel = new ModelCustomPlayer(0.0F, useSmallArms);
        //super(renderManager, useSmallArms);
        super(renderManager, new ModelCustomPlayer(0.0F, useSmallArms), 0.5F);
        //super(renderManager, new ModelCustomPlayer(0.0F, useSmallArms), 0.5F);
        //Reste du constructeur
    }

    @Override
    public ResourceLocation getEntityTexture(AbstractClientPlayerEntity entity) {
        return null;
    }
}
