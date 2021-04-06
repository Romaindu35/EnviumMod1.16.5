package fr.envium.enviummod.core.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ItemRenderer<T extends TileEntity> extends ItemStackTileEntityRenderer {

    private final Supplier<T> te;

    public ItemRenderer(Supplier<T> te) {
        this.te = te;
    }

    @Override
    public void renderByItem(ItemStack itemStackIn, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        TileEntityRendererDispatcher.instance.renderItem(this.te.get(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }

}
