package fr.envium.enviummod.core.client.render;

import com.mojang.blaze3d.vertex.IVertexBuilder;

public class VertexBuilder implements IVertexBuilder {

    @Override
    public IVertexBuilder vertex(double x, double y, double z) {
        return null;
    }

    @Override
    public IVertexBuilder color(int red, int green, int blue, int alpha) {
        return null;
    }

    @Override
    public IVertexBuilder uv(float u, float v) {
        return null;
    }

    @Override
    public IVertexBuilder overlayCoords(int u, int v) {
        return null;
    }

    @Override
    public IVertexBuilder uv2(int u, int v) {
        return null;
    }

    @Override
    public IVertexBuilder normal(float x, float y, float z) {
        return null;
    }

    @Override
    public void endVertex() {

    }
}
