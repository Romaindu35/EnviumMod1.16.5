package fr.envium.enviummod.core.client.gui.toast;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.envium.enviummod.api.init.RegisterItem;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class JobsToast implements IToast {

    private int xp;
    private boolean reload;
    private boolean hasPlayedSound;

    public JobsToast(int xp) {
        this.xp = xp;
    }

    public IToast.Visibility draw(ToastGui toastGui, long delta) {
        toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
        RenderSystem.color3f(1.0F, 1.0F, 1.0F);
        DisplayInfo displayinfo = this.displayInfo(xp);
        toastGui.blit(0, 0, 0, 0, 160, 32);
        if (displayinfo != null) {
            List<String> list = toastGui.getMinecraft().fontRenderer.listFormattedStringToWidth(displayinfo.getTitle().getFormattedText(), 125);
            int l = 16 - list.size() * 9 / 2;
            for (String s : list) {
                toastGui.getMinecraft().fontRenderer.drawString(s, 30.0F, (float) l, 16777215);
                l += 9;
            }
            toastGui.getMinecraft().getItemRenderer().renderItemAndEffectIntoGUI((LivingEntity) null, displayinfo.getIcon(), 8, 8);
            return delta >= 5000L ? IToast.Visibility.HIDE : IToast.Visibility.SHOW;
        } else {
            return IToast.Visibility.HIDE;
        }
    }

    public DisplayInfo displayInfo(int xp) {
        return new DisplayInfo(
                new ItemStack(RegisterItem.envium_lingot),
                //new StringTextComponent("Vous avez gagne " + xp + " xp"),
                new StringTextComponent(I18n.format("jobs.win_xp_message") + xp + " xp"),
                new StringTextComponent("Description"),
                null,
                FrameType.GOAL,
                true,
                false,
                false
        );
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public boolean getReload() {
        return reload;
    }

    public void setReload(boolean reload) {
        this.reload = reload;
    }
}
