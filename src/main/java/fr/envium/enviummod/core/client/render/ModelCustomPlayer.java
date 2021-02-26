package fr.envium.enviummod.core.client.render;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;

public class ModelCustomPlayer extends PlayerModel<AbstractClientPlayerEntity> {

    public ModelCustomPlayer(float modelSize, boolean smallArmsIn) {
        super(modelSize, smallArmsIn);
    }

    @Override
    public void setRotationAngles(AbstractClientPlayerEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setRotationAngles(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        this.bipedLeftArm.rotateAngleZ = -0.5f;
        this.bipedRightArm.rotateAngleZ = 1.9f;
        this.bipedLeftLeg.rotateAngleZ = -0.9f;
        this.bipedRightLeg.rotateAngleZ = 0.1f;
    }
}
