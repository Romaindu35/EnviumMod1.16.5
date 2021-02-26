package fr.envium.enviummod.addons.probe.theoneprobe;

import fr.envium.enviummod.addons.probe.theoneprobe.commands.ModCommands;
import fr.envium.enviummod.addons.probe.theoneprobe.config.Config;
import fr.envium.enviummod.addons.probe.theoneprobe.playerdata.PlayerGotNote;
import fr.envium.enviummod.addons.probe.theoneprobe.playerdata.PlayerProperties;
import fr.envium.enviummod.addons.probe.theoneprobe.playerdata.PropertiesDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

public class ForgeEventHandlers {

    @SubscribeEvent
    public void serverLoad(FMLServerStartingEvent event) {
        ModCommands.register(event.getCommandDispatcher());
    }


    // @todo 1.15
//    @SubscribeEvent
//    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
//        Config.setupStyleConfig();
//        Config.updateDefaultOverlayStyle();
//    }

    @SubscribeEvent
    public void onEntityConstructing(AttachCapabilitiesEvent<Entity> event){
        if (event.getObject() instanceof PlayerEntity) {
            if (!event.getObject().getCapability(PlayerProperties.PLAYER_GOT_NOTE).isPresent()) {
                event.addCapability(new ResourceLocation(TheOneProbe.MODID, "properties"), new PropertiesDispatcher());
            }
        }
    }

    @SubscribeEvent
    public void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            // We need to copyFrom the capabilities
            LazyOptional<PlayerGotNote> capability = event.getOriginal().getCapability(PlayerProperties.PLAYER_GOT_NOTE);
            capability.ifPresent(oldStore -> {
                event.getPlayer().getCapability(PlayerProperties.PLAYER_GOT_NOTE).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (Config.spawnNote.get()) {
            event.getPlayer().getCapability(PlayerProperties.PLAYER_GOT_NOTE).ifPresent(note -> {
                if (!note.isPlayerGotNote()) {
                    boolean success = event.getPlayer().inventory.addItemStackToInventory(new ItemStack(RegisterItem.PROBE_NOTE.get()));
                    if (success) {
                        note.setPlayerGotNote(true);
                    }
                }
            });
        }
    }
}