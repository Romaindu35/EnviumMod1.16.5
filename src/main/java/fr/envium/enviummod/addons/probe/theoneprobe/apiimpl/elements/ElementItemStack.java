package fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.elements;

import fr.envium.enviummod.addons.probe.theoneprobe.api.IElementNew;
import fr.envium.enviummod.addons.probe.theoneprobe.api.IItemStyle;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.TheOneProbeImp;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.client.ElementItemStackRender;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.styles.ItemStyle;
import fr.envium.enviummod.addons.probe.theoneprobe.network.NetworkTools;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class ElementItemStack implements IElementNew {

    private final ItemStack itemStack;
    private final IItemStyle style;

    public ElementItemStack(ItemStack itemStack, IItemStyle style) {
        this.itemStack = itemStack;
        this.style = style;
    }

    public ElementItemStack(PacketBuffer buf) {
        if (buf.readBoolean()) {
            itemStack = NetworkTools.readItemStack(buf);
        } else {
            itemStack = ItemStack.EMPTY;
        }
        style = new ItemStyle()
                .width(buf.readInt())
                .height(buf.readInt());
    }

    @Override
    public void render(int x, int y) {
        ElementItemStackRender.render(itemStack, style, x, y);
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
        if (!itemStack.isEmpty()) {
            buf.writeBoolean(true);
            NetworkTools.writeItemStack(buf, itemStack);
        } else {
            buf.writeBoolean(false);
        }
        buf.writeInt(style.getWidth());
        buf.writeInt(style.getHeight());
    }

    @Override
    public int getID() {
        return TheOneProbeImp.ELEMENT_ITEM;
    }
}
