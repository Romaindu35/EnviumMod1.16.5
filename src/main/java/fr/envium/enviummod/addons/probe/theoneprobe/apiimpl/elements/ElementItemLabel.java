package fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.elements;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.envium.enviummod.addons.probe.theoneprobe.api.IElement;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.TheOneProbeImp;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.client.ElementTextRender;
import fr.envium.enviummod.addons.probe.theoneprobe.network.NetworkTools;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class ElementItemLabel implements IElement {

    private final ItemStack itemStack;

    public ElementItemLabel(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ElementItemLabel(PacketBuffer buf) {
        if (buf.readBoolean()) {
            itemStack = NetworkTools.readItemStack(buf);
        } else {
            itemStack = ItemStack.EMPTY;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y) {
        if (!itemStack.isEmpty()) {
            ElementTextRender.render(itemStack.getHoverName(), matrixStack, x, y);
        }
    }

    @Override
    public int getWidth() {
        if (!itemStack.isEmpty()) {
            return ElementTextRender.getWidth(itemStack.getHoverName());
        } else {
            return 10;
        }
    }

    @Override
    public int getHeight() {
        return 10;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        if (!itemStack.isEmpty()) {
            buf.writeBoolean(true);
            NetworkTools.writeItemStack(buf, itemStack);
        } else {
            buf.writeBoolean(false);
        }
    }

    @Override
    public int getID() {
        return TheOneProbeImp.ELEMENT_ITEMLABEL;
    }
}
