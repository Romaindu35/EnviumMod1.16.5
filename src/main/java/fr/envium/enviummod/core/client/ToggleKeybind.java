package fr.envium.enviummod.core.client;

import fr.envium.enviummod.EnviumMod;
import fr.envium.enviummod.addons.neat.NeatConfig;
import fr.envium.enviummod.core.client.gui.GuiAdmin;
import fr.envium.enviummod.core.client.gui.MetierScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class ToggleKeybind {

    public ToggleKeybind() {
        EnviumMod.keyBindingAdmin = new KeyBinding("enviummod.keybind.guiadmin", GLFW.GLFW_KEY_UNKNOWN,  "key.categories.enviummod");
        EnviumMod.keyBindingMetier = new KeyBinding("enviummod.keybind.guimetier", GLFW.GLFW_KEY_UNKNOWN, "key.categories.enviummod");
        ClientRegistry.registerKeyBinding(EnviumMod.keyBindingAdmin);
        ClientRegistry.registerKeyBinding(EnviumMod.keyBindingMetier);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        if (EnviumMod.keyBindingAdmin.consumeClick()) {
            Minecraft.getInstance().setScreen(new GuiAdmin(new StringTextComponent("gui_admin")));
        } else if (EnviumMod.keyBindingMetier.consumeClick()) {
            Minecraft.getInstance().setScreen(new MetierScreen.PrincipalScreen());
        }
    }

}
