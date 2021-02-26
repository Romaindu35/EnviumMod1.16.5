package fr.envium.enviummod.core.client.gui;

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
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public void init()
    {
        super.init();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        xSize = 240;
        ySize = 238;
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.renderBackground();
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        this.blit(i, j, 0, 0, this.xSize, this.ySize);
    }

}
