package fr.envium.enviummod.addons.probe.theoneprobe.keys;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

    public static KeyBinding toggleLiquids;
    public static KeyBinding toggleVisible;
    public static KeyBinding keyBindingTheOneProbeConfigGui;

    public static void init() {
        toggleLiquids = new KeyBinding("key.toggleLiquids", KeyConflictContext.IN_GAME, InputMappings.UNKNOWN, "key.categories.theoneprobe");
        toggleVisible = new KeyBinding("key.toggleVisible", KeyConflictContext.IN_GAME, InputMappings.UNKNOWN, "key.categories.theoneprobe");
        keyBindingTheOneProbeConfigGui = new KeyBinding("enviummod.theOneProbe.configGui", GLFW.GLFW_KEY_UNKNOWN, "key.categories.enviummod");

        ClientRegistry.registerKeyBinding(toggleLiquids);
        ClientRegistry.registerKeyBinding(toggleVisible);
        ClientRegistry.registerKeyBinding(keyBindingTheOneProbeConfigGui);
    }
}
