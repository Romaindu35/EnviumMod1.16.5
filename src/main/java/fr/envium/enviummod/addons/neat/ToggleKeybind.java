package fr.envium.enviummod.addons.neat;

import fr.envium.enviummod.EnviumMod;
import fr.envium.enviummod.api.config.ConfigHandler;
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

	KeyBinding key;
	boolean down;
	
	public ToggleKeybind() {
		/*EnviumMod.keyBindingAdmin = new KeyBinding("enviummod.keybind.guiadmin",GLFW.GLFW_KEY_UNKNOWN,  "key.categories.enviummod");
		EnviumMod.keyBindingMetier = new KeyBinding("enviummod.keybind.guimetier", GLFW.GLFW_KEY_UNKNOWN, "key.categories.enviummod");*/
		key = new KeyBinding("enviummod.keybind.toggle", GLFW.GLFW_KEY_UNKNOWN, "key.categories.enviummod");
		/*ClientRegistry.registerKeyBinding(EnviumMod.keyBindingAdmin);
		ClientRegistry.registerKeyBinding(EnviumMod.keyBindingMetier);*/
		ClientRegistry.registerKeyBinding(key);
	}
	
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event) {
		Minecraft mc = Minecraft.getInstance();
		boolean wasDown = down;
		down = key.isKeyDown();
		if(mc.isGameFocused() && down && !wasDown)
			NeatConfig.draw = !NeatConfig.draw;
	}
	
}
