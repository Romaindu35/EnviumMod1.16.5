package fr.envium.enviummod.core.client;

import fr.envium.enviummod.animals.client.entity.render.LirondelRenderer;
import fr.envium.enviummod.animals.client.entity.render.MarmotRenderer;
import fr.envium.enviummod.animals.client.entity.render.MeerkatRenderer;
import fr.envium.enviummod.animals.client.entity.render.ToucanRenderer;
import fr.envium.enviummod.api.init.RegisterBlock;
import fr.envium.enviummod.api.init.RegisterContainer;
import fr.envium.enviummod.api.init.RegisterEntity;
import fr.envium.enviummod.api.init.RegisterTileEntity;
import fr.envium.enviummod.core.client.gui.*;
import fr.envium.enviummod.core.client.gui.toast.JobsToastGui;
import fr.envium.enviummod.core.tileentity.renderer.EnviumChestEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ClientManager {

    private static final ClientManager INSTANCE = new ClientManager();

    public void preInit(FMLClientSetupEvent event) {
        setToastGui();
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ToggleKeybind());
        registerScreenToContainer();
        registerEntityModel();
        registerRenderLookup();
        //DiscordInitialise.getInstance().init();
    }

    public void loadComplete(FMLLoadCompleteEvent e)
    {

    }

    public void registerScreenToContainer()
    {
        DeferredWorkQueue.runLater(() -> {
            ScreenManager.register(RegisterContainer.ENVIUM_CHEST.get(), GuiEnviumChest::new);
            ScreenManager.register(RegisterContainer.ENVIUM_FURNACE.get(), GuiEnviumFurnace::new);

            ClientRegistry.bindTileEntityRenderer(RegisterTileEntity.TILE_ENVIUM_CHEST.get(), EnviumChestEntityRenderer::new);
        });
    }

    public void registerEntityModel() {
        RenderingRegistry.registerEntityRenderingHandler(RegisterEntity.TOUCAN_ENTITY.get(), ToucanRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(RegisterEntity.LIRONDEL_ENTITY.get(), LirondelRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(RegisterEntity.MARMOT_ENTITY.get(), MarmotRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(RegisterEntity.MEERKAT_ENTITY.get(), MeerkatRenderer::new);
    }

    public void registerRenderLookup() {
        RenderTypeLookup.setRenderLayer(RegisterBlock.cave_block, RenderType.translucent());
        RenderTypeLookup.setRenderLayer(RegisterBlock.pillow, RenderType.translucent());
    }

    @OnlyIn(Dist.CLIENT)
    public static void setToastGui() {
        Field sessionField = ObfuscationReflectionHelper.findField(Minecraft.class,  "toast");
        ObfuscationReflectionHelper.setPrivateValue(Field.class, sessionField, sessionField.getModifiers() & ~Modifier.FINAL, "modifiers");
        ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, Minecraft.getInstance(), new JobsToastGui(), "toast");
    }

    @SubscribeEvent
    public void onInitGuiEvent(GuiScreenEvent.InitGuiEvent event) {
        if (event.getGui() == null)
            return;
        if (event.getGui().getClass() == InventoryScreen.class || event.getGui().getClass() == CreativeScreen.class) {
            event.addWidget(new Button(event.getGui().width /2 - 120, event.getGui().height / 2 -100, 30, 20, new StringTextComponent("Metier"), (x) -> {
                Minecraft.getInstance().setScreen(new MetierScreen.PrincipalScreen());
            }));
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
    }
}
