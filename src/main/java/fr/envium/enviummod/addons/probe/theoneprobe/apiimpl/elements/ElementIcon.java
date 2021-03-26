package fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.elements;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.envium.enviummod.addons.probe.theoneprobe.api.IElement;
import fr.envium.enviummod.addons.probe.theoneprobe.api.IIconStyle;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.TheOneProbeImp;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.client.ElementIconRender;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.styles.IconStyle;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class ElementIcon implements IElement {

    private final ResourceLocation icon;
    private final int u;
    private final int v;
    private final int w;
    private final int h;
    private final IIconStyle style;

    public ElementIcon(ResourceLocation icon, int u, int v, int w, int h, IIconStyle style) {
        this.icon = icon;
        this.u = u;
        this.v = v;
        this.w = w;
        this.h = h;
        this.style = style;
    }

    public ElementIcon(PacketBuffer buf) {
        icon = buf.readResourceLocation();
        u = buf.readInt();
        v = buf.readInt();
        w = buf.readInt();
        h = buf.readInt();
        style = new IconStyle()
                .width(buf.readInt())
                .height(buf.readInt())
                .textureWidth(buf.readInt())
                .textureHeight(buf.readInt());
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y) {
        ElementIconRender.render(icon, matrixStack, x, y, w, h, u, v, style.getTextureWidth(), style.getTextureHeight());
    }

    @Override
    public int getWidth() {
        return style.getWidth();
    }

    @Override
    public int getHeight() {
        return style.getHeight();
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeResourceLocation(icon);
        buf.writeInt(u);
        buf.writeInt(v);
        buf.writeInt(w);
        buf.writeInt(h);
        buf.writeInt(style.getWidth());
        buf.writeInt(style.getHeight());
        buf.writeInt(style.getTextureWidth());
        buf.writeInt(style.getTextureHeight());
    }

    @Override
    public int getID() {
        return TheOneProbeImp.ELEMENT_ICON;
    }
}
