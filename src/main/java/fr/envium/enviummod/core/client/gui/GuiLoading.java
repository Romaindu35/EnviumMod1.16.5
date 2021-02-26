package fr.envium.enviummod.core.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.envium.enviummod.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.LoadingGui;
import net.minecraft.resources.IAsyncReloader;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.util.Optional;
import java.util.function.Consumer;

public class GuiLoading extends LoadingGui {
    private static final ResourceLocation ENVIUM_BACKGROUND_TEXTURE = new ResourceLocation(References.MODID, "textures/gui/fond_ecran_minecraft.png");
    private static final ResourceLocation ENVIUM_LOGO = new ResourceLocation(References.MODID, "textures/gui/title/envium_title.png");
    private final Minecraft mc;
    private final IAsyncReloader asyncReloader;
    private final Consumer<Optional<Throwable>> completedCallback;
    private final boolean reloading;
    private float progress;
    private long fadeOutStart = -1L;
    private long fadeInStart = -1L;

    public GuiLoading(Minecraft p_i225928_1_, IAsyncReloader p_i225928_2_, Consumer<Optional<Throwable>> p_i225928_3_, boolean p_i225928_4_) {
        this.mc = p_i225928_1_;
        this.asyncReloader = p_i225928_2_;
        this.completedCallback = p_i225928_3_;
        this.reloading = p_i225928_4_;
    }

    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        int width = this.mc.getMainWindow().getScaledWidth();
        int height = this.mc.getMainWindow().getScaledHeight();
        long time = Util.milliTime();
        if (this.reloading && (this.asyncReloader.asyncPartDone() || this.mc.currentScreen != null) && this.fadeInStart == -1L) {
            this.fadeInStart = time;
        }

        float f = this.fadeOutStart > -1L ? (float)(time - this.fadeOutStart) / 1000.0F : -1.0F;
        float f1 = this.fadeInStart > -1L ? (float)(time - this.fadeInStart) / 500.0F : -1.0F;
        float f2;
        if (f >= 1.0F) {
            if (this.mc.currentScreen != null) {
                this.mc.currentScreen.render(0, 0, p_render_3_);
            }

            int l = MathHelper.ceil((1.0F - MathHelper.clamp(f - 1.0F, 0.0F, 1.0F)) * 255.0F);
            fill(0, 0, width, height, 16777215 | l << 24);
            f2 = 1.0F - MathHelper.clamp(f - 1.0F, 0.0F, 1.0F);
        } else if (this.reloading) {
            if (this.mc.currentScreen != null && f1 < 1.0F) {
                this.mc.currentScreen.render(p_render_1_, p_render_2_, p_render_3_);
            }

            int j1 = MathHelper.ceil(MathHelper.clamp((double)f1, 0.15D, 1.0D) * 255.0D);
            fill(0, 0, width, height, 16777215 | j1 << 24);
            f2 = MathHelper.clamp(f1, 0.0F, 1.0F);
        } else {
            fill(0, 0, width, height, -1);
            f2 = 1.0F;
        }
        this.mc.getTextureManager().bindTexture(ENVIUM_BACKGROUND_TEXTURE);
        RenderSystem.enableBlend();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.blit(0, 0, 0.0F, 0.0F, 430, 250, 430, 250);

        this.mc.getTextureManager().bindTexture(ENVIUM_LOGO);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.blit(width / 2 - 50, height/ 2 - 90, 0.0F, 0.0F, 100, 100, 100, 100);

        float f3 = this.asyncReloader.estimateExecutionSpeed();
        this.progress = MathHelper.clamp(this.progress * 0.95F + f3 * 0.050000012F, 0.0F, 1.0F);
        //net.minecraftforge.fml.client.ClientModLoader.renderProgressText();
        if (f < 1.0F) {
            int i = MathHelper.ceil((float)(430 - 2 - 1) * this.progress);
            fill(0, 200, 430, 250, Color.MAGENTA.getRGB());
            fill(0, 200, i, 205, Color.YELLOW.getRGB());
        }

        if (f >= 2.0F) {
            this.mc.setLoadingGui((LoadingGui)null);
        }

        if (this.fadeOutStart == -1L && this.asyncReloader.fullyDone() && (!this.reloading || f1 >= 2.0F)) {
            this.fadeOutStart = Util.milliTime(); // Moved up to guard against inf loops caused by callback
            try {
                this.asyncReloader.join();
                this.completedCallback.accept(Optional.empty());
            } catch (Throwable throwable) {
                this.completedCallback.accept(Optional.of(throwable));
            }

            if (this.mc.currentScreen != null) {
                this.mc.currentScreen.init(this.mc, this.mc.getMainWindow().getScaledWidth(), this.mc.getMainWindow().getScaledHeight());
            }
        }

    }

    public boolean isPauseScreen() {
        return true;
    }
}
