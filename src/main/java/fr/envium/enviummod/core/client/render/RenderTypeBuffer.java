package fr.envium.enviummod.core.client.render;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;

public class RenderTypeBuffer implements IRenderTypeBuffer {
    @Override
    public IVertexBuilder getBuffer(RenderType p_getBuffer_1_) {
        return new VertexBuilder();
    }
}
