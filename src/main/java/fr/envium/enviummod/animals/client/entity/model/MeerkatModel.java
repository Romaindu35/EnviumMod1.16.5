package fr.envium.enviummod.animals.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import fr.envium.enviummod.animals.entity.Meerkat;
import fr.envium.enviummod.animals.entity.Toucan;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class MeerkatModel<T extends Meerkat> extends EntityModel<Meerkat> {

    private final ModelRenderer body;
    private final ModelRenderer patte;
    private final ModelRenderer tete;
    private final ModelRenderer oreille;
    private final ModelRenderer oeil;
    private final ModelRenderer nez;
    private final ModelRenderer bouche;
    private final ModelRenderer queue;
    private final ModelRenderer neck;

    public MeerkatModel() {
        textureWidth = 64;
        textureHeight = 64;

        body = new ModelRenderer(this);
        body.setRotationPoint(0F, 24.0F, 0.0F);
        body.setTextureOffset(0, 29).addBox(-2.0F, -11.0F, -2.0F, 5.0F, 10.0F, 5.0F, 0.0F, false);

        patte = new ModelRenderer(this);
        patte.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(patte);
        patte.setTextureOffset(0, 61).addBox(-2.0F, -10.0F, -4.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        patte.setTextureOffset(0, 0).addBox(-2.0F, -1.0F, -4.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        patte.setTextureOffset(12, 0).addBox(2.0F, -1.0F, -4.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        patte.setTextureOffset(0, 53).addBox(2.0F, -10.0F, -4.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        tete = new ModelRenderer(this);
        tete.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(tete);
        tete.setTextureOffset(44, 0).addBox(-2.0F, -15.0F, -2.0F, 5.0F, 3.0F, 5.0F, 0.0F, false);

        oreille = new ModelRenderer(this);
        oreille.setRotationPoint(0.0F, 0.0F, 0.0F);
        tete.addChild(oreille);
        oreille.setTextureOffset(24, 61).addBox(2.0F, -17.0F, -1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        oreille.setTextureOffset(30, 61).addBox(-2.0F, -17.0F, -1.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);

        oeil = new ModelRenderer(this);
        oeil.setRotationPoint(0.0F, 0.0F, 0.0F);
        tete.addChild(oeil);
        oeil.setTextureOffset(44, 62).addBox(-1.0F, -15.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        oeil.setTextureOffset(38, 62).addBox(1.0F, -15.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        nez = new ModelRenderer(this);
        nez.setRotationPoint(0.0F, 0.0F, 0.0F);
        tete.addChild(nez);
        nez.setTextureOffset(60, 62).addBox(0.0F, -14.0F, -3.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        bouche = new ModelRenderer(this);
        bouche.setRotationPoint(0.0F, 0.0F, 0.0F);
        tete.addChild(bouche);
        bouche.setTextureOffset(47, 62).addBox(-1.0F, -13.0F, -3.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        queue = new ModelRenderer(this);
        queue.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(queue);
        queue.setTextureOffset(24, 0).addBox(0.0F, -1.0F, 2.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);

        neck = new ModelRenderer(this);
        neck.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(neck);
        neck.setTextureOffset(52, 11).addBox(-1.0F, -12.0F, -1.0F, 3.0F, 1.0F, 3.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(Meerkat entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
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
