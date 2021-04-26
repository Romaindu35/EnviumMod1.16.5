package fr.envium.enviummod.core.client.gui;

import com.google.common.util.concurrent.Runnables;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import fr.envium.enviummod.EnviumMod;
import fr.envium.enviummod.References;
import fr.envium.enviummod.Status;
import fr.envium.enviummod.core.client.gui.button.ScreenButton;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.network.ServerPinger;
import net.minecraft.client.renderer.RenderSkybox;
import net.minecraft.client.renderer.RenderSkyboxCube;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;


@OnlyIn(Dist.CLIENT)
public class CustomMainMenuScreen extends Screen {
    public static final RenderSkyboxCube PANORAMA_RESOURCES = new RenderSkyboxCube(new ResourceLocation("textures/gui/title/background/panorama"));
    private static final ResourceLocation PANORAMA_OVERLAY_TEXTURES = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
    private static final ResourceLocation ACCESSIBILITY_TEXTURES = new ResourceLocation("textures/gui/accessibility.png");
    private final boolean showTitleWronglySpelled;
    @Nullable
    //private String splashText;
    private Button buttonResetDemo;
    private static final ResourceLocation MINECRAFT_TITLE_TEXTURES = new ResourceLocation(References.MODID, "textures/gui/title/envium_title.png");
    /** Has the check for a realms notification screen been performed? */
    //private boolean hasCheckedForRealmsNotification;
    /**
     * A screen generated by realms for notifications; drawn in adition to the main menu (buttons and such from both are
     * drawn at the same time). May be null.
     */
    //private Screen realmsNotification;
    private int widthCopyright;
    private int widthCopyrightRest;
    private final RenderSkybox panorama = new RenderSkybox(PANORAMA_RESOURCES);
    private final boolean showFadeInAnimation;
    private long firstRenderTime;
    //private net.minecraftforge.client.gui.NotificationModUpdateScreen modUpdateNotification;

    private final ServerPinger serverPinger = new ServerPinger();
    private final ServerData server = new ServerData("envium", "mc.envium.fr:25565", false);
    private static final ThreadPoolExecutor EXECUTOR = new ScheduledThreadPoolExecutor(5, (new ThreadFactoryBuilder()).setNameFormat("Server Pinger #%d").setDaemon(true).build());


    public CustomMainMenuScreen() {
        this(false);
    }

    public CustomMainMenuScreen(boolean fadeIn) {
        super(new TranslationTextComponent("narrator.screen.title"));
        this.showFadeInAnimation = fadeIn;
        this.showTitleWronglySpelled = (double)(new Random()).nextFloat() < 1.0E-4D;
    }


   /* public static CompletableFuture<Void> loadAsync(TextureManager texMngr, Executor backgroundExecutor) {
        return CompletableFuture.allOf(texMngr.loadAsync(MINECRAFT_TITLE_TEXTURES, backgroundExecutor), texMngr.loadAsync(MINECRAFT_TITLE_EDITION, backgroundExecutor), texMngr.loadAsync(PANORAMA_OVERLAY_TEXTURES, backgroundExecutor), PANORAMA_RESOURCES.loadAsync(texMngr, backgroundExecutor));
    }*/

    public boolean isPauseScreen() {
        return false;
    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    protected void init() {
        this.widthCopyright = this.font.width("Copyright Mojang AB.");
        this.widthCopyrightRest = this.width - this.widthCopyright - 2;
        int i = 24;
        int j = this.height / 4 + 48;
        //this.addSinglePlayerMultiplayerButtons(j, 24);
        /*Button modbutton = null;


            modbutton = this.addButton(new Button(this.width / 2 - 100, j + 24 * 2, 98, 20, I18n.format("fml.menu.mods"), button -> {
                this.minecraft.displayGuiScreen(new net.minecraftforge.fml.client.gui.screen.ModListScreen(this));
            }));
*/
        /*this.addButton(new ImageButton(this.width / 2 - 124, j + 72 + 12, 20, 20, 0, 106, 20, Button.WIDGETS_LOCATION, 256, 256, (p_213090_1_) -> {
            this.minecraft.displayGuiScreen(new LanguageScreen(this, this.minecraft.gameSettings, this.minecraft.getLanguageManager()));
        }, I18n.format("narrator.button.language")));*/
        this.addButton(new ScreenButton(this.width / 2 -25, this.height / 2 + 50, 50, 10, I18n.get("menu.enviummod.play"), (p_213089_1_) -> {
            this.minecraft.setScreen(new ConnectingScreen(this, this.minecraft, new ServerData("Envium", "vps.envium.fr:25565", false)));
        }));
        this.addButton(new ScreenButton(this.width / 2 - 100 -25, this.height / 2 + 25 , 50, 10, I18n.get("menu.enviummod.options"), (p_213096_1_) -> {
            this.minecraft.setScreen(new OptionsScreen(this, this.minecraft.options));
        }));
        this.addButton(new ScreenButton(this.width / 2 + 100 -25, this.height / 2 + 25, 50, 10, I18n.get("menu.enviummod.quit"), (p_213094_1_) -> {
            this.minecraft.stop();
        }));

        if (EnviumMod.status.equals(Status.DEVELOPMENT)) {
            this.addButton(new Button(this.width - 50, 2, 50, 20, new TranslationTextComponent("menu.enviummod.dev"), (p_213089_1_) -> {
                this.minecraft.setScreen(new WorldSelectionScreen(this));
            }));
            this.addButton(new Button(this.width - 100, 2, 50, 20, new TranslationTextComponent("menu.enviummod.dev"), (p_213089_1_) -> {
                this.minecraft.setScreen(new ConnectingScreen(this, this.minecraft, new ServerData("Envium", "localhost:25565", false)));
            }));
        }
        /*this.addButton(new ImageButton(this.width / 2 + 104, j + 72 + 12, 20, 20, 0, 0, 20, ACCESSIBILITY_TEXTURES, 32, 64, (p_213088_1_) -> {
            this.minecraft.displayGuiScreen(new AccessibilityScreen(this, this.minecraft.gameSettings));
        }, I18n.format("narrator.button.accessibility")));*/
    }

    /**
     * Adds Singleplayer and Multiplayer buttons on Main Menu for players who have bought the game.
     */
    private void addSinglePlayerMultiplayerButtons(int yIn, int rowHeightIn) {
        this.addButton(new Button(this.width / 2 - 100, yIn, 200, 20, new TranslationTextComponent("menu.singleplayer"), (p_213089_1_) -> {
            this.minecraft.setScreen(new WorldSelectionScreen(this));
        }));
        this.addButton(new Button(this.width / 2 - 100, yIn + rowHeightIn * 1, 200, 20, new TranslationTextComponent("menu.multiplayer"), (p_213086_1_) -> {
            if (this.minecraft.options.skipMultiplayerWarning) {
                this.minecraft.setScreen(new MultiplayerScreen(this));
            } else {
                this.minecraft.setScreen(new MultiplayerWarningScreen(this));
            }

        }));
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        if (this.firstRenderTime == 0L && this.showFadeInAnimation) {
            this.firstRenderTime = Util.getMillis();
        }
        float f = this.showFadeInAnimation ? (float)(Util.getMillis() - this.firstRenderTime) / 1000.0F : 1.0F;
        fill(matrixStack, 0, 0, this.width, this.height, -1);
        this.panorama.render(partialTicks, MathHelper.clamp(f, 0.0F, 1.0F));
        this.minecraft.getTextureManager().bind(PANORAMA_OVERLAY_TEXTURES);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.showFadeInAnimation ? (float)MathHelper.ceil(MathHelper.clamp(f, 0.0F, 1.0F)) : 1.0F);
        blit(matrixStack, 0, 0, this.width, this.height, 0.0F, 0.0F, 16, 128, 16, 128);
        serverInfoPing();
        serverInfoRender(matrixStack);
        float f1 = this.showFadeInAnimation ? MathHelper.clamp(f - 1.0F, 0.0F, 1.0F) : 1.0F;
        int l = MathHelper.ceil(f1 * 255.0F) << 24;
        if ((l & -67108864) != 0) {
            this.minecraft.getTextureManager().bind(MINECRAFT_TITLE_TEXTURES);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, f1);
            blit(matrixStack, this.width / 2 - 50, this.height/ 2 - 90, 0.0F, 0.0F, 100, 100, 100, 100);
            //x, y, ?, ?, postion_x, taille_X, position_y, taille_Y

            this.widthCopyrightRest = this.width - this.font.width("Copyright Mojang AB.") - 2;
            drawString(matrixStack, this.font, "Copyright Mojang AB.", this.widthCopyrightRest, this.height - 20, 16777215 | l);
            this.widthCopyrightRest = this.width - this.font.width("Not affiliated with Mojang AB.") - 2;
            drawString(matrixStack, this.font, "Not affiliated with Mojang AB.", this.widthCopyrightRest, this.height - 10, 16777215 | l);
            for(Widget widget : this.buttons) {
                widget.setAlpha(f1);
            }
            super.render(matrixStack, mouseX, mouseY, partialTicks);

        }
    }

    public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
        if (super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_)) {
            return true;
        } else {
            if (p_mouseClicked_1_ > (double)this.widthCopyrightRest && p_mouseClicked_1_ < (double)(this.widthCopyrightRest + this.widthCopyright) && p_mouseClicked_3_ > (double)(this.height - 10) && p_mouseClicked_3_ < (double)this.height) {
                this.minecraft.setScreen(new WinGameScreen(false, Runnables.doNothing()));
            }
            return false;
        }
    }

    private void serverInfoPing() {
        if(!this.server.pinged)
        {
            this.server.pinged = true;
            this.server.ping = -2L;
            this.server.motd = new StringTextComponent("");
            this.server.status = new StringTextComponent("");
            EXECUTOR.submit(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        CustomMainMenuScreen.this.serverPinger.pingServer(CustomMainMenuScreen.this.server, this);
                    }
                    catch(UnknownHostException unknowHostException)
                    {
                        CustomMainMenuScreen.this.server.ping = -1L;
                        CustomMainMenuScreen.this.server.motd = new StringTextComponent(TextFormatting.DARK_RED + "Impossible de résoudre le nom d'hôte");
                        // on peut aussi utiliser I18n pour passer par les fichiers de langage
                    }
                    catch(Exception exception)
                    {
                        CustomMainMenuScreen.this.server.ping = -1L;
                        CustomMainMenuScreen.this.server.motd = new StringTextComponent( TextFormatting.DARK_RED + "Impossible de se connecter au serveur");
                    }
                }
            });
        }
    }

    private void serverInfoRender(MatrixStack matrixStack) {
        if(this.server.ping >= 0L) {
            drawString(matrixStack, this.minecraft.font, new StringTextComponent(this.server.status.getString() + TextFormatting.RESET + " joueurs"), 2, 2, 0x245791);
            drawString(matrixStack, this.minecraft.font, this.server.ping + " ms", 2, 12, 0x245791);

        }
    }
}