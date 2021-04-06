package fr.envium.enviummod.core.client.gui;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mojang.blaze3d.matrix.MatrixStack;
import fr.envium.enviummod.References;
import fr.envium.enviummod.api.packets.NetworkRegistryHandler;
import fr.envium.enviummod.core.packets.MetierPackets;
import fr.envium.enviummod.core.packets.PacketXpInfoListener;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CCustomPayloadPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class MetierScreen {



    @OnlyIn(Dist.CLIENT)
    public static class PrincipalScreen extends Screen {
        private int xSize;
        private int ySize;
        private int leftTopX;
        private int leftTopY;
        public static String xp_miner;
        public static String xp_lumberjack;
        public static String xp_chasseur;

        public PrincipalScreen() {
            super(new StringTextComponent("gui.metier"));
            this.xSize = 250;
            this.ySize = 170;
            leftTopX = (this.width - this.xSize) / 2;
            leftTopY = (this.height - this.ySize) / 2;

            /*ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("Hello World!");
            PacketBuffer packetBuffer =  new PacketBuffer();
            CCustomPayloadPacket packet = new CCustomPayloadPacket(new ResourceLocation(References.MODID, "PacketXpInfoListener"), packetBuffer);*/
            //Minecraft.getInstance().player.connection.sendPacket(new PacketXpInfoListener());

            NetworkRegistryHandler.bukkit_channel.send(PacketDistributor.SERVER.noArg(), new PacketXpInfoListener());
        }
        private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/gui/gui_metier.png");
        @Override
        public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
            this.renderBackground(matrixStack);
            drawGuiContainerBackgroundLayer(matrixStack);
            drawGuiContainerForegroundLayer(matrixStack);
            super.render(matrixStack, mouseX, mouseY, partialTicks);
        }

        protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getInstance().getTextureManager().bindTexture(texture);
            int k = (this.width - this.xSize) / 2;
            int l = (this.height - this.ySize) / 2;
            this.blit(matrixStack, k, l, 0, 0, this.xSize, this.ySize);
        }

        @Override
        public boolean keyPressed(int key, int b, int c) {
            if (key == 256) {
                this.minecraft.player.closeScreen();
                return true;
            }
            return super.keyPressed(key, b, c);
        }

        @Override
        public void tick() {
            super.tick();
        }

        protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack) {
            this.font.drawString(matrixStack, "Metier", this.width /2 - 20, this.height /2 - 70, -16777216);
            this.font.drawString(matrixStack, xp_miner, this.width /2 - 90, this.height /2 - 25, -16777216);
            this.font.drawString(matrixStack, xp_lumberjack, this.width /2 + 40, this.height /2 - 25, -16777216);
            this.font.drawString(matrixStack, xp_chasseur, this.width /2 -30, this.height /2 + 55, -16777216);
        }

        @Override
        public void init(Minecraft minecraft, int width, int height) {
            super.init(minecraft, width, height);
            minecraft.keyboardListener.enableRepeatEvents(true);
            this.addButton(new Button(this.width /2 - 100, this.height /2  - 50, 50, 20, new StringTextComponent("Miner"), e -> {

            }));
            this.addButton(new Button(this.width /2  + 30, this.height /2 - 50, 80, 20, new StringTextComponent("Bucheron"), e -> {

            }));
            this.addButton(new Button(this.width /2 - 40, this.height /2 + 30, 80, 20, new StringTextComponent("Chasseur"), e -> {

            }));
            //Miner////Bucheron
            //////Chasseur///
        }
    }

    @OnlyIn(Dist.CLIENT)
    static class MetierInfo extends Screen {
        private static final ResourceLocation TEXTURE = new ResourceLocation(References.MODID, "textures/gui/gui_metier.png");
        private int xSize = 300;
        private int ySize = 200;

        protected MetierInfo(String button) {
            super(new StringTextComponent("enviummod.metier_info"));
            System.out.println(button);
        }

        public void tick() {

        }

        protected void init() {
            super.init();
        }

        public void render(MatrixStack matrixStack, int p_render_1_, int p_render_2_, float p_render_3_) {
            int i = (this.width - this.xSize) / 2;
            int j = (this.height - this.ySize) / 2;
            this.renderBackground(matrixStack);
            this.minecraft.getTextureManager().bindTexture(TEXTURE);
            this.blit(matrixStack, i, j, 0, 0, this.xSize, this.ySize);
            super.render(matrixStack, p_render_1_, p_render_2_, p_render_3_);
        }
    }
}

