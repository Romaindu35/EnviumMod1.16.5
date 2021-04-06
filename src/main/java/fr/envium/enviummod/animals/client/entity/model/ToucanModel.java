package fr.envium.enviummod.animals.client.entity.model;// Made with Blockbench 3.7.4
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import fr.envium.enviummod.animals.entity.Toucan;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class ToucanModel<T extends Toucan> extends EntityModel<Toucan> {
	private final ModelRenderer body;
	private final ModelRenderer bec;
	private final ModelRenderer bec_haut;
	private final ModelRenderer bec_bas;
	private final ModelRenderer cou;
	private final ModelRenderer corps;
	private final ModelRenderer corps_avant;
	private final ModelRenderer corps_centre;
	private final ModelRenderer corps_arriere;
	private final ModelRenderer paw;
	private final ModelRenderer paw_left;
	private final ModelRenderer cube_r1;
	private final ModelRenderer paw_left_r1;
	private final ModelRenderer paw_right;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer queue;

	public ToucanModel() {
		texWidth = 32;
		texHeight = 32;

		body = new ModelRenderer(this);
		body.setPos(0.0F, 24.0F, 0.0F);
		

		bec = new ModelRenderer(this);
		bec.setPos(0.0F, -3.0F, -4.0F);
		body.addChild(bec);
		

		bec_haut = new ModelRenderer(this);
		bec_haut.setPos(0.0F, 0.0F, 0.0F);
		bec.addChild(bec_haut);
		setRotationAngle(bec_haut, 0.1745F, 0.0F, 0.0F);
		bec_haut.texOffs(9, 10).addBox(-1.0F, -2.0F, -5.0F, 2.0F, 1.0F, 5.0F, 0.0F, false);

		bec_bas = new ModelRenderer(this);
		bec_bas.setPos(0.0F, 0.0F, 0.0F);
		bec.addChild(bec_bas);
		bec_bas.texOffs(11, 0).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 1.0F, 5.0F, 0.0F, false);

		cou = new ModelRenderer(this);
		cou.setPos(0.0F, -4.0F, -1.0F);
		body.addChild(cou);
		setRotationAngle(cou, 0.2618F, 0.0F, 0.0F);
		cou.texOffs(0, 8).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 4.0F, 3.0F, 0.0F, false);

		corps = new ModelRenderer(this);
		corps.setPos(0.0F, 0.0F, 0.0F);
		body.addChild(corps);
		

		corps_avant = new ModelRenderer(this);
		corps_avant.setPos(0.0F, 0.0F, -1.0F);
		corps.addChild(corps_avant);
		setRotationAngle(corps_avant, -0.1745F, 0.0F, 0.0F);
		corps_avant.texOffs(18, 6).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 3.0F, 3.0F, 0.0F, false);

		corps_centre = new ModelRenderer(this);
		corps_centre.setPos(0.0F, -0.5F, 1.0F);
		corps.addChild(corps_centre);
		corps_centre.texOffs(14, 16).addBox(-2.0F, -2.5F, -2.0F, 4.0F, 3.0F, 3.0F, 0.0F, false);

		corps_arriere = new ModelRenderer(this);
		corps_arriere.setPos(0.0F, 0.0F, 2.0F);
		corps.addChild(corps_arriere);
		setRotationAngle(corps_arriere, 0.1745F, 0.0F, 0.0F);
		corps_arriere.texOffs(0, 16).addBox(-2.0F, -3.0F, 0.0F, 4.0F, 3.0F, 3.0F, 0.0F, false);

		paw = new ModelRenderer(this);
		paw.setPos(0.0F, 0.0F, 0.0F);
		corps.addChild(paw);
		

		paw_left = new ModelRenderer(this);
		paw_left.setPos(0.0F, 0.0F, 0.0F);
		paw.addChild(paw_left);
		

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(-2.0F, 2.0F, 1.0F);
		paw_left.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 0.6981F, 0.0F);
		cube_r1.texOffs(0, 0).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		cube_r1.texOffs(0, 0).addBox(0.0F, -1.0F, 0.0F, 2.0F, 1.0F, 1.0F, 0.0F, false);

		paw_left_r1 = new ModelRenderer(this);
		paw_left_r1.setPos(-2.0F, 0.0F, 1.0F);
		paw_left.addChild(paw_left_r1);
		setRotationAngle(paw_left_r1, 0.0F, 0.7418F, 0.0F);
		paw_left_r1.texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		paw_right = new ModelRenderer(this);
		paw_right.setPos(2.0F, 1.0F, 0.0F);
		paw.addChild(paw_right);
		

		cube_r2 = new ModelRenderer(this);
		cube_r2.setPos(0.0F, 0.0F, 2.0F);
		paw_right.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.7854F, 0.0F);
		cube_r2.texOffs(0, 0).addBox(0.0F, 0.0F, -3.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);
		cube_r2.texOffs(0, 0).addBox(0.0F, -1.0F, -2.0F, 1.0F, 2.0F, 1.0F, 0.0F, false);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setPos(0.0F, 0.0F, 2.0F);
		paw_right.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, -0.7854F, 0.0F);
		cube_r3.texOffs(0, 0).addBox(-2.0F, 0.0F, -2.0F, 1.0F, 1.0F, 2.0F, 0.0F, false);

		queue = new ModelRenderer(this);
		queue.setPos(0.0F, 0.0F, 5.0F);
		body.addChild(queue);
		setRotationAngle(queue, 0.5236F, 0.0F, 0.0F);
		queue.texOffs(0, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 1.0F, 7.0F, 0.0F, false);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		body.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	@Override
	public void setupAnim(Toucan entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}


	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}