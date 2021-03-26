package fr.envium.enviummod.core.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.envium.enviummod.References;
import fr.envium.enviummod.core.container.ContainerEnviumFurnace;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class GuiEnviumFurnace extends ContainerScreen<ContainerEnviumFurnace> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/gui/container/envium_furnace.png");

    public GuiEnviumFurnace(ContainerEnviumFurnace screenContainer, PlayerInventory playerInv, ITextComponent titleIn)
    {
        super(screenContainer, playerInv, titleIn);
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

   /*@Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        xSize = 240;
        ySize = 238;
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.renderBackground();
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        this.blit(i, j, 0, 0, this.xSize, this.ySize);
    }*/

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.renderBackground(matrixStack);
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);

        if (this.getContainer().getClientData() != null && !this.getContainer().getClientData().isEmpty())
        {
            Map<String, Integer> data = this.getContainer().getClientData();
            int timePassed = data.get("timePassed");
            float time = data.get("fullTimeRecipe");
            int textureWidth = (int) (23f / time * timePassed);
            this.blit(matrixStack, i + 79, j + 35, 176, 14, textureWidth, 16);
            if (data.get("isBurning").equals(1)) {
                int burningTime = data.get("burningTimeLeft");
                int textureHeight = (int) (12f / data.get("fullBurningTime") * burningTime);
                this.blit(matrixStack, i + 57, j + 38 + 12 - textureHeight, 177, 15 - textureHeight, 14, textureHeight);
            }
        }
    }
}
