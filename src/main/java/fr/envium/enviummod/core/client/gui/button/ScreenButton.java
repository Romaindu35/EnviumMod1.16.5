package fr.envium.enviummod.core.client.gui.button;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import fr.envium.enviummod.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ScreenButton extends AbstractButton {

    Minecraft minecraft = Minecraft.getInstance();
    /*private final Screen screen;

    public ScreenButton(int x, int y, int width, int height, String name, Screen screen)
    {
        super(x, y, width, height, name);
        this.screen = screen;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
    {
        if(this.visible)
        {
            FontRenderer fontrenderer = mc.fontRenderer;
            boolean mouseHover = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int j = 14737632;
            if(mouseHover) // si la souris est sur le bouton
            {
                j = 25656566;
            }
            else
            {
                j = 14737632;
            }
            //GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawCenteredString(fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
        }
    }

    @Override
    public void renderButton(int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_) {
        FontRenderer fontrenderer = minecraft.fontRenderer;
        RenderSystem.color4f(0.4F, 1.0F, 1.0F, this.alpha);
        int i = this.getYImage(this.isHovered());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.blit(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
        this.blit(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
        this.renderBg(minecraft, p_renderButton_1_, p_renderButton_2_);
        int j = getFGColor();
        this.drawCenteredString(fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    public void onClick(double p_onClick_1_, double p_onClick_3_) {
        System.out.println("On click");
        this.minecraft.displayGuiScreen(screen);
    }*/
    protected final ScreenButton.IPressable onPress;
    protected ResourceLocation WIDGETS_TEXTURES = new ResourceLocation(References.MODID, "textures/gui/transparent.png");

    public ScreenButton(int widthIn, int heightIn, int width, int height, String text, ScreenButton.IPressable onPress) {
        super(widthIn, heightIn, width, height, ITextComponent.nullToEmpty(text));
        this.onPress = onPress;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        alpha = 0.0F;
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = minecraft.font;
        minecraft.getTextureManager().bind(WIDGETS_TEXTURES);
        //RenderSystem.color4f(0.4F, 1.0F, 1.0F, this.alpha);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.getYImage(this.isHovered());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        this.blit(matrixStack, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height);
        this.blit(matrixStack, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
        this.renderBg(matrixStack, minecraft, mouseX, mouseY);
        int j = getFGColor();
        drawCenteredString(matrixStack, fontrenderer, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    public void onPress() {
        this.onPress.onPress(this);
    }

    @Override
    public void onClick(double p_onClick_1_, double p_onClick_3_) {
        this.onPress();
    }

    @OnlyIn(Dist.CLIENT)
    public interface IPressable {
        void onPress(ScreenButton p_onPress_1_);
    }
}
