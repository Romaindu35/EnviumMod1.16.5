package fr.envium.enviummod.core.mixin;

import fr.envium.enviummod.core.client.gui.GuiLoading;
import net.minecraft.client.GameConfiguration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.LoadingGui;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    /**
     * @author
     */
    /*@Overwrite
    public void setLoadingGui(LoadingGui loadingGui) {
        if (loadingGui == null) {
            Minecraft.getInstance().loadingGui = null;
            return;
        } else {
            List<IResourcePack> list = Minecraft.getInstance().resourcePackRepository.getEnabledPacks().stream().map(ResourcePackInfo::getResourcePack).collect(Collectors.toList());
            Minecraft.getInstance().loadingGui = new GuiLoading(Minecraft.getInstance(), Minecraft.getInstance().resourceManager.reloadResources(Util.getServerExecutor(), Minecraft.getInstance(), Minecraft.RESOURCE_RELOAD_INIT_TASK, list), (p_229990_2_) -> {
                Util.acceptOrElse(p_229990_2_, Minecraft.getInstance()::restoreResourcePacks, () -> {
                    //Minecraft.getInstance().languageManager.parseLanguageMetadata(list);
                    if (SharedConstants.developmentMode) {
                        Minecraft.getInstance().checkMissingData();
                    }

                    if (net.minecraftforge.fml.client.ClientModLoader.completeModLoading()) return; // Do not overwrite the error screen
                    // FORGE: Move opening initial screen to after startup and events are enabled.
                    // Also Fixes MC-145102
                    Minecraft.getInstance().displayGuiScreen(new MainMenuScreen(true));
                });
            }, false);
        }
        System.out.println("Minecraft was been initialise");
    }*/

    @Inject(at = @At("HEAD"), method = "createTitle", cancellable = true)
    private void getWindowTitle(CallbackInfoReturnable<String> cir) {
        StringBuilder stringbuilder = new StringBuilder("Envium");
        stringbuilder.append(" - ");
        stringbuilder.append(Minecraft.getInstance().getUser().getName());
        System.out.println("*********** Mixins Minecraft (Title was been updated) ***********");
        cir.setReturnValue(stringbuilder.toString());
    }

    @Inject(at = @At("RETURN"), method = "Lnet/minecraft/client/Minecraft;<init>(Lnet/minecraft/client/GameConfiguration;)V", cancellable = true)
    private void init(GameConfiguration p_i45547_1_, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        InputStream inputStream16 = ClassLoader.getSystemResourceAsStream("assets/enviummod/textures/icons/icon_16x16.png");
        InputStream inputStream32 = ClassLoader.getSystemResourceAsStream("assets/enviummod/textures/icons/icon32.png");
        mc.getWindow().setIcon(inputStream16, inputStream32);
    }

}
