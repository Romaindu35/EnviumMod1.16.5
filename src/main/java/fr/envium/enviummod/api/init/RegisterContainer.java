package fr.envium.enviummod.api.init;

import fr.envium.enviummod.References;
import fr.envium.enviummod.core.container.ContainerEnviumChest;
import fr.envium.enviummod.core.container.ContainerEnviumFurnace;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegisterContainer {

    public static final DeferredRegister<ContainerType<?>> CONTAINERS = new DeferredRegister(ForgeRegistries.CONTAINERS, References.MODID);

    public static final RegistryObject<ContainerType<ContainerEnviumChest>> ENVIUM_CHEST = CONTAINERS.register("envium_chest", () -> IForgeContainerType.create(ContainerEnviumChest::new));
    public static final RegistryObject<ContainerType<ContainerEnviumFurnace>> ENVIUM_FURNACE = CONTAINERS.register("envium_furnace", () -> IForgeContainerType.create(ContainerEnviumFurnace::new));


    //public static final RegistryObject<ContainerType<IronChestContainer>> IRON_CHEST = CONTAINERS.register("iron_chest", () -> new ContainerType<>(IronChestContainer::createIronContainer));


}
