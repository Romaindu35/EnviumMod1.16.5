package fr.envium.enviummod.core.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.envium.enviummod.References;
import fr.envium.enviummod.core.container.ContainerEnviumChest;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiEnviumChest extends ContainerScreen<ContainerEnviumChest> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/gui/container/envium_chest.png");

    public GuiEnviumChest(ContainerEnviumChest screenContainer, PlayerInventory inv, ITextComponent titleIn)
    {
        super(screenContainer, inv, titleIn);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void init()
    {
        super.init();
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY)
    {
        imageWidth = 240;
        imageHeight = 238;
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.renderBackground(matrixStack);
        this.minecraft.getTextureManager().bind(TEXTURE);
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

}
