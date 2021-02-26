package fr.envium.enviummod.addons.probe.theoneprobe;

import fr.envium.enviummod.addons.probe.theoneprobe.api.IProbeInfoEntityProvider;
import fr.envium.enviummod.addons.probe.theoneprobe.api.IProbeInfoProvider;
import fr.envium.enviummod.addons.probe.theoneprobe.api.ITheOneProbe;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.TheOneProbeImp;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.providers.*;
import fr.envium.enviummod.addons.probe.theoneprobe.config.Config;
import fr.envium.enviummod.addons.probe.theoneprobe.items.AddProbeTagRecipeSerializer;
import fr.envium.enviummod.addons.probe.theoneprobe.network.PacketHandler;
import fr.envium.enviummod.addons.probe.theoneprobe.playerdata.PlayerGotNote;
import fr.envium.enviummod.addons.probe.theoneprobe.proxy.ClientProxy;
import fr.envium.enviummod.addons.probe.theoneprobe.proxy.IProxy;
import fr.envium.enviummod.addons.probe.theoneprobe.proxy.ServerProxy;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod("theoneprobe_envium")
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TheOneProbe {
    public static final String MODID = "theoneprobe";

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
    public static final Logger logger = LogManager.getLogger();

    public static TheOneProbeImp theOneProbeImp = new TheOneProbeImp();

    public static boolean baubles = false;
    public static boolean tesla = false;
    public static boolean redstoneflux = false;

    public static ItemGroup tabProbe = new ItemGroup("Probe") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(RegisterItem.PROBE.get());
        }
    };


    public TheOneProbe() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        RegisterItem.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void init(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandlers());
        registerCapabilities();
        TheOneProbeImp.registerElements();
        TheOneProbe.theOneProbeImp.registerProvider(new DefaultProbeInfoProvider());
        TheOneProbe.theOneProbeImp.registerProvider(new DebugProbeInfoProvider());
        TheOneProbe.theOneProbeImp.registerProvider(new BlockProbeInfoProvider());
        TheOneProbe.theOneProbeImp.registerEntityProvider(new DefaultProbeInfoEntityProvider());
        TheOneProbe.theOneProbeImp.registerEntityProvider(new DebugProbeInfoEntityProvider());
        TheOneProbe.theOneProbeImp.registerEntityProvider(new EntityProbeInfoEntityProvider());
        PacketHandler.registerMessages(MODID);
        configureProviders();
        configureEntityProviders();
        proxy.setup(event);
    }

    private void processIMC(final InterModProcessEvent event) {
        event.getIMCStream().forEach(message -> {
            if ("getTheOneProbe".equalsIgnoreCase(message.getMethod())) {
                Supplier<Function<ITheOneProbe, Void>> supplier = message.getMessageSupplier();
                supplier.get().apply(theOneProbeImp);
            }
        });
    }

    @SubscribeEvent
    public static void registerRecipes(final RegistryEvent.Register<IRecipeSerializer<?>> e) {
        e.getRegistry().register(new AddProbeTagRecipeSerializer().setRegistryName(new ResourceLocation(TheOneProbe.MODID, "probe_helmet")));
    }


    private static void registerCapabilities(){
        CapabilityManager.INSTANCE.register(PlayerGotNote.class, new Capability.IStorage<PlayerGotNote>() {

            @Override
            public void readNBT(Capability<PlayerGotNote> capability, PlayerGotNote playerGotNote, Direction direction, INBT inbt) {
                throw new UnsupportedOperationException();
            }

            @Override
            public INBT writeNBT(Capability<PlayerGotNote> capability, PlayerGotNote instance, Direction side) {
                throw new UnsupportedOperationException();
            }

        }, () -> {
            throw new UnsupportedOperationException();
        });
    }

    private void configureProviders() {
        List<IProbeInfoProvider> providers = TheOneProbe.theOneProbeImp.getProviders();
        String[] defaultValues = new String[providers.size()];
        int i = 0;
        for (IProbeInfoProvider provider : providers) {
            defaultValues[i++] = provider.getID();
        }

        String[] sortedProviders = defaultValues; // @todo TheOneProbe.config.getStringList("sortedProviders", Config.CATEGORY_PROVIDERS, defaultValues, "Order in which providers should be used");
        String[] excludedProviders = new String[] {}; // @todo TheOneProbe.config.getStringList("excludedProviders", Config.CATEGORY_PROVIDERS, new String[] {}, "Providers that should be excluded");
        Set<String> excluded = new HashSet<>();
        Collections.addAll(excluded, excludedProviders);

        TheOneProbe.theOneProbeImp.configureProviders(sortedProviders, excluded);
    }

    private void configureEntityProviders() {
        List<IProbeInfoEntityProvider> providers = TheOneProbe.theOneProbeImp.getEntityProviders();
        String[] defaultValues = new String[providers.size()];
        int i = 0;
        for (IProbeInfoEntityProvider provider : providers) {
            defaultValues[i++] = provider.getID();
        }

        String[] sortedProviders = defaultValues; // @todo TheOneProbe.config.getStringList("sortedEntityProviders", Config.CATEGORY_PROVIDERS, defaultValues, "Order in which entity providers should be used");
        String[] excludedProviders = new String[] {}; // @todo TheOneProbe.config.getStringList("excludedEntityProviders", Config.CATEGORY_PROVIDERS, new String[] {}, "Entity providers that should be excluded");
        Set<String> excluded = new HashSet<>();
        Collections.addAll(excluded, excludedProviders);

        TheOneProbe.theOneProbeImp.configureEntityProviders(sortedProviders, excluded);
    }

}
