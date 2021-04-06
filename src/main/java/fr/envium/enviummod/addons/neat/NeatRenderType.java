package fr.envium.enviummod.addons.neat;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

import net.minecraft.client.renderer.RenderState.TransparencyState;

public class NeatRenderType extends RenderState {

    public NeatRenderType(String string, Runnable r, Runnable r1) {
        super(string, r, r1);
    }

    public static RenderType getNoIconType() {
        RenderType.State renderTypeState = RenderType.State.builder().setTransparencyState(TransparencyState.TRANSLUCENT_TRANSPARENCY).createCompositeState(true);
        return RenderType.create("neat_icon", DefaultVertexFormats.POSITION_COLOR, 0, 0, false, false, renderTypeState);
    }

    public static RenderType getIconType(ResourceLocation location) {
        RenderType.State renderTypeState = RenderType.State.builder().setTextureState(BLOCK_SHEET).setTransparencyState(TRANSLUCENT_TRANSPARENCY).createCompositeState(true);
        return RenderType.create("neat_icon", DefaultVertexFormats.POSITION_COLOR_TEX, 7, 256, true, true, renderTypeState);
    }

    public static RenderType getHealthBarType() {
        RenderType.State renderTypeState = RenderType.State.builder().setTransparencyState(TRANSLUCENT_TRANSPARENCY).createCompositeState(true);
        return RenderType.create("neat_health_bar", DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL, 7, 256, true, true, renderTypeState);
    }
}