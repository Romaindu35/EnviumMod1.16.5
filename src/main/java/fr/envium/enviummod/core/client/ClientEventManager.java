package fr.envium.enviummod.core.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.envium.enviummod.core.client.gui.*;
import fr.envium.enviummod.core.client.render.RenderCustomPlayer;
import fr.envium.enviummod.core.client.render.RenderOverlay;
import fr.envium.enviummod.core.server.enums.InventoryAction;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class ClientEventManager {

    RenderCustomPlayer playerRenderer;
    private static boolean inventorySync = false;

    @SubscribeEvent
    public void preRenderPlayer(RenderPlayerEvent.Pre event) {
        /*event.setCanceled(true);
        playerRenderer = new RenderCustomPlayer(event.getRenderer().getRenderManager(), true);
        playerRenderer.render((AbstractClientPlayerEntity) event.getPlayer(), 0, 0, new MatrixStack(), new RenderTypeBuffer(), 1);
*/
    }
    /*@SubscribeEvent
    public void keyDetect(InputEvent.KeyInputEvent event) {
        if (event.getKey() == 80) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.currentScreen == null)
                Minecraft.getInstance().displayGuiScreen(new GuiAdmin(new StringTextComponent("GuiAdmin")));
        }
    }*/

    @SubscribeEvent
    public void onGuiDrawing(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            draw(event.getMatrixStack(), event.getWindow());
        }
    }

    @SubscribeEvent
    public void onOpenGui(GuiOpenEvent event) {
        if (event.getGui() != null && event.getGui().getClass() == MainMenuScreen.class) {
            event.setGui(new CustomMainMenuScreen());
        }
        if (event.getGui() != null && event.getGui().getClass() == MultiplayerScreen.class) {
            event.setGui(new CustomMainMenuScreen());
        }
        if (event.getGui() != null && event.getGui().getClass() == MultiplayerScreen.class) {
            event.setGui(new MainMenuScreen());
        }

        /*if (event.getGui() != null && event.getGui().getClass() == CreativeScreen.class ){
            System.out.println("gui custom");
            event.setGui(new CreativeScreenCustom(Minecraft.getInstance().player));
        }

        if (event.getGui() != null && event.getGui().getClass() == InventoryScreen.class ){
            System.out.println("gui custom");
            event.setGui(new InventoryScreenCustom(Minecraft.getInstance().player));
        }*/
    }

    @SubscribeEvent
    public void onInitGuiEvent(GuiScreenEvent.InitGuiEvent event) {
        if (event.getGui() == null)
            return;
        if (event.getGui().getClass() == InventoryScreen.class || event.getGui().getClass() == CreativeScreen.class) {
            event.addWidget(new Button(event.getGui().width /2 - 120, event.getGui().height / 2 -100, 30, 20, new StringTextComponent("Metier"), (x) -> {
                Minecraft.getInstance().displayGuiScreen(new MetierScreen.PrincipalScreen());
            }));
        }

    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void joinEvent(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            /*if (!inventorySync)
                return;*/
            //NetworkRegistryHandler.network.send(PacketDistributor.SERVER.noArg(), new InventoryPacket("", InventoryAction.READ));
            System.out.println(Minecraft.getInstance().getSession().getPlayerID());
            System.out.println(player.getName().getString());
            System.out.println(InventoryAction.READ);
            /*NetworkRegistryHandler.network.send(PacketDistributor.SERVER.noArg(),
                    new InventoryPacket(player.getName().getString(), InventoryAction.READ));*/
            System.out.println(inventorySync+ "avant");
            inventorySync = !inventorySync;
            System.out.println(inventorySync + "après");
        }
    }

    /*@SubscribeEvent
    public void tickEvent(TickEvent event) {
        GLFW.glfwSetWindowTitle(Minecraft.getInstance().getMainWindow().getHandle(), "Envium | " + Minecraft.getInstance().player.getName());
    }*/

    private void draw(MatrixStack matrixStack, MainWindow window) {
        RenderOverlay.drawText(matrixStack, window);
    }

}
