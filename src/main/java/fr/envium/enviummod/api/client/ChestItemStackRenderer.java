package fr.envium.enviummod.api.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public class ChestItemStackRenderer<T extends TileEntity> extends ItemStackTileEntityRenderer {

    private final Supplier<T> te;

    public ChestItemStackRenderer(Supplier<T> te) {
        this.te = te;
    }

    @Override
    public void render(ItemStack itemStackIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        TileEntityRendererDispatcher.instance.renderItem(this.te.get(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn);
    }

}
