package fr.envium.enviummod.addons.neat;

import fr.envium.enviummod.api.config.Config;
import fr.envium.enviummod.api.config.ConfigHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;

@Mod(Neat.MOD_ID)
public class Neat {
	
	public static final String MOD_ID = "neat";

	public Neat() {
        ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        NeatConfig.init();
	}
	
	public void setup(FMLClientSetupEvent event) {
		NeatConfig.load();
		//ConfigHandler.Client.load();
		MinecraftForge.EVENT_BUS.register(new ToggleKeybind());
		MinecraftForge.EVENT_BUS.register(new HealthBarRenderer());
	}
}
