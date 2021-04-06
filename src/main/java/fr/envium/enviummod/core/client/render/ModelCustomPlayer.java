package fr.envium.enviummod.core.client.render;

import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.model.PlayerModel;

public class ModelCustomPlayer extends PlayerModel<AbstractClientPlayerEntity> {

    public ModelCustomPlayer(float modelSize, boolean smallArmsIn) {
        super(modelSize, smallArmsIn);
    }

    @Override
    public void setupAnim(AbstractClientPlayerEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        this.leftArm.zRot = -0.5f;
        this.rightArm.zRot = 1.9f;
        this.leftLeg.zRot = -0.9f;
        this.rightLeg.zRot = 0.1f;
    }
}
