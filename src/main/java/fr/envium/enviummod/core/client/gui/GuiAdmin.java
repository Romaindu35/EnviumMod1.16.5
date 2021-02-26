package fr.envium.enviummod.core.client.gui;

import fr.envium.enviummod.References;
import fr.envium.enviummod.core.server.enums.InventoryAction;
import fr.envium.enviummod.api.packets.NetworkRegistryHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;
import fr.envium.enviummod.core.packets.InventoryPacket;

@OnlyIn(Dist.CLIENT)
public class GuiAdmin extends Screen {

    private int xSize = 250;
    private int ySize = 200;

    public static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/gui/gui_admin.png");

    public GuiAdmin(ITextComponent titleIn) {
        super(titleIn);
    }

    public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
        int i = (this.width - xSize) / 2;
        int j = (this.height - ySize) / 2;
        this.renderBackground();
        this.minecraft.getTextureManager().bindTexture(TEXTURE);
        this.blit(20, 20, 0, 0, xSize, ySize);
        this.addButton(new Button(20,20, 100,20,"Inventory change", (p_onPress_1_) -> {
            String player = Minecraft.getInstance().player.getName().getString();
            System.out.println(player);
            NetworkRegistryHandler.network.send(PacketDistributor.SERVER.noArg(), new InventoryPacket(player, InventoryAction.SAVE_READ));
        }));
        super.render(p_render_1_, p_render_2_, p_render_3_);
    }

}
