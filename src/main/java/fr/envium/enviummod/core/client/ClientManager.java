package fr.envium.enviummod.core.client;

import fr.envium.enviummod.animals.client.entity.render.LirondelRenderer;
import fr.envium.enviummod.animals.client.entity.render.MarmotRenderer;
import fr.envium.enviummod.animals.client.entity.render.MeerkatRenderer;
import fr.envium.enviummod.animals.client.entity.render.ToucanRenderer;
import fr.envium.enviummod.api.client.DiscordInitialise;
import fr.envium.enviummod.api.init.RegisterBlock;
import fr.envium.enviummod.api.init.RegisterContainer;
import fr.envium.enviummod.api.init.RegisterEntity;
import fr.envium.enviummod.api.init.RegisterTileEntity;
import fr.envium.enviummod.core.client.gui.*;
import fr.envium.enviummod.core.client.gui.toast.JobsToastGui;
import fr.envium.enviummod.core.tileentity.renderer.EnviumChestEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ClientManager {

    private static final ClientManager INSTANCE = new ClientManager();
    //private static Minecraft mc = Minecraft.getInstance();
    private PlayerEntity playerEntity;
    private World world;

    public void preInit(FMLClientSetupEvent event) {
        setToastGui();
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new InitGui());
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ClientEventManager());
        MinecraftForge.EVENT_BUS.register(new ToggleKeybind());
        bindScreenContainers();
        RenderingRegistry.registerEntityRenderingHandler(RegisterEntity.TOUCAN_ENTITY.get(), ToucanRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(RegisterEntity.LIRONDEL_ENTITY.get(), LirondelRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(RegisterEntity.MARMOT_ENTITY.get(), MarmotRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(RegisterEntity.MEERKAT_ENTITY.get(), MeerkatRenderer::new);
        RenderTypeLookup.setRenderLayer(RegisterBlock.cave_block, RenderType.translucent());
        RenderTypeLookup.setRenderLayer(RegisterBlock.pillow, RenderType.translucent());
        //DiscordInitialise.getInstance().init();
    }

    public void loadComplete(FMLLoadCompleteEvent e)
    {

    }

    public void bindScreenContainers()
    {
        DeferredWorkQueue.runLater(() -> {
            //ScreenManager.registerFactory(RegisterContainer.ENVIUM_CHEST.get(), GuiEnviumChest::new);
            ScreenManager.register(RegisterContainer.ENVIUM_CHEST.get(), GuiEnviumChest::new);
            ScreenManager.register(RegisterContainer.ENVIUM_FURNACE.get(), GuiEnviumFurnace::new);

            //ScreenManager.registerFactory(RegisterContainer.IRON_CHEST.get(), IronChestScreen::new);

            ClientRegistry.bindTileEntityRenderer(RegisterTileEntity.TILE_ENVIUM_CHEST.get(), EnviumChestEntityRenderer::new);
        });
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onKeyInputEvent(InputEvent.KeyInputEvent event) {
        /*System.out.println("loloollol");
        if (EnviumMod.keyBindingAdmin.isPressed()) {
            System.out.println("prout");
            keyTestTyped();
        } else if (EnviumMod.keyBindingMetier.isPressed()) {
            keyMetierTyped();
        }*/

    }

    @OnlyIn(Dist.CLIENT)
    public static void keyMetierTyped() {
        Minecraft mc = Minecraft.getInstance();
        mc.setScreen(new MetierScreen.PrincipalScreen());
    }

    @OnlyIn(Dist.CLIENT)
    public static void keyTestTyped() {
        Minecraft mc = Minecraft.getInstance();
        System.out.println("clé de la clé");
        mc.setScreen(new GuiAdmin(new StringTextComponent("gui_admin")));
    }

    @OnlyIn(Dist.CLIENT)
    public static void setToastGui() {
        Field sessionField = ObfuscationReflectionHelper.findField(Minecraft.class,  "toast");
        ObfuscationReflectionHelper.setPrivateValue(Field.class, sessionField, sessionField.getModifiers() & ~Modifier.FINAL, "modifiers");
        ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getInstance(), new JobsToastGui(), "toast");
    }
}
