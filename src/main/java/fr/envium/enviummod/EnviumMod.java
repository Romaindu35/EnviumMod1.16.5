package fr.envium.enviummod;

import fr.envium.enviummod.api.config.ConfigHandler;
import fr.envium.enviummod.api.init.*;
import fr.envium.enviummod.api.packets.NetworkRegistryHandler;
import fr.envium.enviummod.core.World.ModOreGen;
import fr.envium.enviummod.core.World.WorldEventCustom;
import fr.envium.enviummod.core.client.ClientManager;
import fr.envium.enviummod.core.commands.CommandRegenChunk;
import fr.envium.enviummod.core.commands.JobsCommands;
import fr.envium.enviummod.core.items.ModSpawnEgg;
import fr.envium.enviummod.core.jobs.Jobs;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static fr.envium.enviummod.References.MODID;

@Mod(MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EnviumMod
{
    public static final Logger LOGGER = LogManager.getLogger(References.NAME);

    public static final Status status = Status.DEVELOPMENT;

    private EnviumMod instance;

    public static KeyBinding keyBindingAdmin;
    public static KeyBinding keyBindingMetier;

    public EnviumMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(new ClientManager()::preInit);
        bus.addListener(new ClientManager()::clientSetup);
        bus.addListener(new ClientManager()::loadComplete);
        bus.addListener(this::setup);
        bus.addListener(this::serverStarting);


        RegisterItem.init();
        RegisterBlock.init();
        RegisterTileEntity.TILE_ENTITIES.register(bus);
        RegisterContainer.CONTAINERS.register(bus);
        RegisterEntity.ENTITY_TYPES.register(bus);

        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.SERVER_SPECS);
        //ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHandler.CLIENT_SPECS);
        //ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.CLIENT_SPECS);
        //ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ConfigHandler.CLIENT_SPECS);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.SERVER_SPECS);

        MinecraftForge.EVENT_BUS.addListener(WorldEventCustom::onWorldLoaded);
        MinecraftForge.EVENT_BUS.addListener(WorldEventCustom::onWorldSaved);
        MinecraftForge.EVENT_BUS.addListener(this::serverStarting);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        NetworkRegistryHandler.registerMessage();
        initJobs();
    }

    private void serverStarting(final FMLServerStartingEvent event)
    {
        JobsCommands.register(event.getCommandDispatcher());
        CommandRegenChunk.register(event.getCommandDispatcher());
    }

    @SubscribeEvent
    public static void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
        ModSpawnEgg.initSpawnEggs();
    }

    @SubscribeEvent
    public static void loadCompleteEvent(FMLLoadCompleteEvent event) {
        ModOreGen.generateOre();
    }

    public void initJobs() {
        Jobs.getInstance();
    }

    public EnviumMod getInstance()
    {
        return this.instance == null ? instance = new EnviumMod() : instance;
    }
}
