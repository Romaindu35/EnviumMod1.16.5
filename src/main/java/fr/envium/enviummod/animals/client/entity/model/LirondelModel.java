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
        texWidth = 32;
        texHeight = 32;

        body = new ModelRenderer(this);
        body.setPos(0.0F, 24.0F, 0.0F);


        head = new ModelRenderer(this);
        head.setPos(0.0F, 0.0F, 0.0F);
        body.addChild(head);
        head.texOffs(16, 24).addBox(-2.0F, -8.0F, -11.0F, 4.0F, 4.0F, 4.0F, 0.0F, false);
        head.texOffs(0, 27).addBox(-1.0F, -6.0F, -15.0F, 2.0F, 1.0F, 4.0F, 0.0F, false);

        corps = new ModelRenderer(this);
        corps.setPos(0.0F, 0.0F, 0.0F);
        body.addChild(corps);
        corps.texOffs(0, 0).addBox(-2.0F, -4.0F, -8.0F, 4.0F, 4.0F, 16.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(Lirondel entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

}
