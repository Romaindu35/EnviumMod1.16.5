package fr.envium.enviummod.animals.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import fr.envium.enviummod.animals.entity.Lirondel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class LirondelModel<T extends Lirondel> extends EntityModel<Lirondel> {

    private final ModelRenderer body;
    private final ModelRenderer head;
    private final ModelRenderer corps;

    public LirondelModel() {
        textureWidth = 32;
        textureHeight = 32;

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 24.0F, 0.0F);


        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(head);
        head.setTextureOffset(16, 24).addBox(-2.0F, -8.0F, -11.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);
        head.setTextureOffset(0, 27).addBox(-1.0F, -6.0F, -15.0F, 2.0F, 1.0F, 4.0F, 0.0F, false);

        corps = new ModelRenderer(this);
        corps.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(corps);
        corps.setTextureOffset(0, 0).addBox(-2.0F, -4.0F, -8.0F, 4.0F, 4.0F, 16.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(Lirondel entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

}
