package fr.envium.enviummod.addons.probe.theoneprobe.keys;

import fr.envium.enviummod.addons.probe.theoneprobe.config.Config;
import fr.envium.enviummod.addons.probe.theoneprobe.gui.GuiConfig;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KeyInputHandler {

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyBindings.toggleLiquids.consumeClick()) {
            Config.setLiquids(!Config.showLiquids.get());
        } else if (KeyBindings.toggleVisible.consumeClick()) {
            if (!Config.holdKeyToMakeVisible.get()) {
                Config.setVisible(!Config.isVisible.get());
            }
        } else if (KeyBindings.keyBindingTheOneProbeConfigGui.consumeClick()) {
            Minecraft.getInstance().setScreen(new GuiConfig());
        }
    }
}
