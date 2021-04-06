package fr.envium.enviummod.core.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.envium.enviummod.References;
import fr.envium.enviummod.core.server.enums.InventoryAction;
import fr.envium.enviummod.api.packets.NetworkRegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

@OnlyIn(Dist.CLIENT)
public class GuiAdmin extends Screen {

    private int xSize = 250;
    private int ySize = 200;

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/gui/gui_admin.png");

    public GuiAdmin(ITextComponent titleIn) {
        super(titleIn);
    }

    public void render(MatrixStack matrixStack, int p_render_1_, int p_render_2_, float p_render_3_) {
        int i = (this.width - xSize) / 2;
        int j = (this.height - ySize) / 2;
        this.renderBackground(matrixStack);
        this.minecraft.getTextureManager().bind(TEXTURE);
        this.blit(matrixStack, 20, 20, 0, 0, xSize, ySize);
        this.addButton(new Button(20,20, 100,20,new StringTextComponent("Inventory change"), (p_onPress_1_) -> {
            String player = Minecraft.getInstance().player.getName().getString();
            System.out.println(player);
            //NetworkRegistryHandler.network.send(PacketDistributor.SERVER.noArg(), new InventoryPacket(player, InventoryAction.SAVE_READ));
        }));
        super.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
    }

}
