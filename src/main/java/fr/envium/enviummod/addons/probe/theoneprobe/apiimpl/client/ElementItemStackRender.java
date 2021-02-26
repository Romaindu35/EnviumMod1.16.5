package fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.client;

import fr.envium.enviummod.addons.probe.theoneprobe.api.IItemStyle;
import fr.envium.enviummod.addons.probe.theoneprobe.rendering.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class ElementItemStackRender {

    public static void render(ItemStack itemStack, IItemStyle style, int x, int y) {
        ItemRenderer itemRender = Minecraft.getInstance().getItemRenderer();
        if (!itemStack.isEmpty()) {
            int size = itemStack.getCount();
            String amount;
            if (size <= 1) {
                amount = "";
            } else if (size < 100000) {
                amount = String.valueOf(size);
            } else if (size < 1000000) {
                amount = String.valueOf(size / 1000) + "k";
            } else if (size < 1000000000) {
                amount = String.valueOf(size / 1000000) + "m";
            } else {
                amount = String.valueOf(size / 1000000000) + "g";
            }

            if (!RenderHelper.renderItemStack(Minecraft.getInstance(), itemRender, itemStack, x + (style.getWidth() - 18) / 2, y + (style.getHeight() - 18) / 2, amount)) {
                // There was a crash rendering this item
                RenderHelper.renderText(Minecraft.getInstance(), x, y, TextFormatting.RED + "ERROR: " + itemStack.getDisplayName());
            }
        }
    }

}
