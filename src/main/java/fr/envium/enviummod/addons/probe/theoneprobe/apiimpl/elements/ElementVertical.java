package fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.elements;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.envium.enviummod.addons.probe.theoneprobe.api.ElementAlignment;
import fr.envium.enviummod.addons.probe.theoneprobe.api.IElement;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.TheOneProbeImp;
import net.minecraft.network.PacketBuffer;

public class ElementVertical extends AbstractElementPanel {

    public static final int SPACING = 2;

    public ElementVertical(Integer borderColor, int spacing, ElementAlignment alignment) {
        super(borderColor, spacing, alignment);
    }

    public ElementVertical(PacketBuffer buf) {
        super(buf);
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y) {
        super.render(matrixStack, x, y);
        if (borderColor != null) {
            x += 3;
            y += 3;
        }
        int totWidth = getWidth();
        for (IElement element : children) {
            int w = element.getWidth();
            int cx = x;
            switch (alignment) {
                case ALIGN_TOPLEFT:
                    break;
                case ALIGN_CENTER:
                    cx = x + (totWidth - w) / 2;
                    break;
                case ALIGN_BOTTOMRIGHT:
                    cx = x + totWidth - w;
                    break;
            }
            element.render(matrixStack, cx, y);
            y += element.getHeight() + spacing;
        }
    }

    private int getBorderSpacing() {
        return borderColor == null ? 0 : 6;
    }

    @Override
    public int getHeight() {
        int h = 0;
        for (IElement element : children) {
            h += element.getHeight();
        }
        return h + spacing * (children.size() - 1) + getBorderSpacing();
    }

    @Override
    public int getWidth() {
        int w = 0;
        for (IElement element : children) {
            int ww = element.getWidth();
            if (ww > w) {
                w = ww;
            }
        }
        return w + getBorderSpacing();
    }

    @Override
    public int getID() {
        return TheOneProbeImp.ELEMENT_VERTICAL;
    }
}
