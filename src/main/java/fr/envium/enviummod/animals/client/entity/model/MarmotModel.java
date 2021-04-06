package fr.envium.enviummod.animals.client.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import fr.envium.enviummod.animals.entity.Marmot;
import fr.envium.enviummod.api.init.RegisterEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class MarmotModel<T extends Marmot> extends EntityModel<Marmot> {
    private final ModelRenderer body;
    private final ModelRenderer patte;
    private final ModelRenderer tete;
    private final ModelRenderer oreille;
    private final ModelRenderer oeil;
    private final ModelRenderer nez;
    private final ModelRenderer bouche;
    private final ModelRenderer neck;


    public MarmotModel() {
        texWidth = 64;
        texHeight = 64;

        body = new ModelRenderer(this);
        body.setPos(0.0F, 24.0F, 0.0F);
        body.texOffs(0, 0).addBox(-3.0F, -11.0F, -3.0F, 7.0F, 11.0F, 7.0F, 0.0F, false);

        patte = new ModelRenderer(this);
        patte.setPos(0.0F, 0.0F, 0.0F);
        body.addChild(patte);
        patte.texOffs(0, 61).addBox(-3.0F, -11.0F, -5.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        patte.texOffs(0, 61).addBox(3.0F, -11.0F, -5.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        patte.texOffs(0, 61).addBox(3.0F, -1.0F, -5.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
        patte.texOffs(0, 61).addBox(-3.0F, -1.0F, -5.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

        tete = new ModelRenderer(this);
        tete.setPos(0.0F, -11.0F, 1.0F);
        body.addChild(tete);
        tete.texOffs(36, 0).addBox(-3.0F, -6.0F, -4.0F, 7.0F, 5.0F, 7.0F, 0.0F, false);

        oreille = new ModelRenderer(this);
        oreille.setPos(0.0F, 11.0F, -1.0F);
        tete.addChild(oreille);
        oreille.texOffs(60, 61).addBox(-3.0F, -19.0F, -3.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);
        oreille.texOffs(60, 61).addBox(3.0F, -19.0F, -3.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);

        oeil = new ModelRenderer(this);
        oeil.setPos(0.0F, 11.0F, -1.0F);
        tete.addChild(oeil);
        oeil.texOffs(45, 62).addBox(-2.0F, -16.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        oeil.texOffs(45, 62).addBox(2.0F, -16.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        nez = new ModelRenderer(this);
        nez.setPos(0.0F, 11.0F, -1.0F);
        tete.addChild(nez);
        nez.texOffs(35, 62).addBox(0.0F, -15.0F, -4.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        bouche = new ModelRenderer(this);
        bouche.setPos(0.0F, 11.0F, -1.0F);
        tete.addChild(bouche);
        bouche.texOffs(25, 62).addBox(-1.0F, -13.0F, -4.0F, 3.0F, 1.0F, 1.0F, 0.0F, false);

        neck = new ModelRenderer(this);
        neck.setPos(0.0F, 11.0F, -1.0F);
        tete.addChild(neck);
        neck.texOffs(0, 46).addBox(-2.0F, -12.0F, -2.0F, 5.0F, 1.0F, 5.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(Marmot entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.body.xRot = -300.0F;
        this.tete.xRot = 300.0F;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        matrixStack.translate(0.0, -0.3, 0.5);
        //matrixStack.translate(0.0, 0.0, 0.0);
        body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
