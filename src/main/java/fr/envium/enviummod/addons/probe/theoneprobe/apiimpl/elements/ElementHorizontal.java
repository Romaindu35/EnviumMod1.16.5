package fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.elements;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.envium.enviummod.addons.probe.theoneprobe.api.ElementAlignment;
import fr.envium.enviummod.addons.probe.theoneprobe.api.IElement;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.TheOneProbeImp;
import net.minecraft.network.PacketBuffer;

public class ElementHorizontal extends AbstractElementPanel {

    public static final int SPACING = 5;

    public ElementHorizontal(Integer borderColor, int spacing, ElementAlignment alignment) {
        super(borderColor, spacing, alignment);
    }

    public ElementHorizontal(PacketBuffer buf) {
        super(buf);
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y) {
        super.render(matrixStack, x, y);
        if (borderColor != null) {
            x += 3;
            y += 3;
        }
        int totHeight = getHeight();
        for (IElement element : children) {
            int h = element.getHeight();
            int cy = y;
            switch (alignment) {
                case ALIGN_TOPLEFT:
                    break;
                case ALIGN_CENTER:
                    cy = y + (totHeight - h) / 2;
                    break;
                case ALIGN_BOTTOMRIGHT:
                    cy = y + totHeight - h;
                    break;
            }
            element.render(matrixStack, x, cy);
            x += element.getWidth() + spacing;
        }
    }

    private int getBorderSpacing() {
        return borderColor == null ? 0 : 6;
    }

    @Override
    public int getWidth() {
        int w = 0;
        for (IElement element : children) {
            w += element.getWidth();
        }
        return w + spacing * (children.size() - 1) + getBorderSpacing();
    }

    @Override
    public int getHeight() {
        int h = 0;
        for (IElement element : children) {
            int hh = element.getHeight();
            if (hh > h) {
                h = hh;
            }
        }
        return h + getBorderSpacing();
    }

    @Override
    public int getID() {
        return TheOneProbeImp.ELEMENT_HORIZONTAL;
    }
}
