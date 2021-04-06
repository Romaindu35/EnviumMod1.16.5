package fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import fr.envium.enviummod.addons.probe.theoneprobe.rendering.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;

public class ElementIconRender {

    public static void render(ResourceLocation icon, MatrixStack matrixStack, int x, int y, int w, int h, int u, int v, int txtw, int txth) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        if (icon == null) {
            return;
        }

        if (u == -1) {
            TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(AtlasTexture.LOCATION_BLOCKS).apply(icon);
            if (sprite == null) {
                return;
            }
            Minecraft.getInstance().getTextureManager().bind(AtlasTexture.LOCATION_BLOCKS);
            RenderHelper.drawTexturedModalRect(matrixStack.last().pose(), x, y, sprite, w, h);
        } else {
            Minecraft.getInstance().getTextureManager().bind(icon);
            RenderHelper.drawTexturedModalRect(matrixStack.last().pose(), x, y, u, v, w, h, txtw, txth);
        }
    }
}
