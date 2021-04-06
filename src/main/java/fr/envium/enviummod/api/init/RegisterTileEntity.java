package fr.envium.enviummod.api.init;

import fr.envium.enviummod.References;
import fr.envium.enviummod.core.tileentity.TileEnviumChest;
import fr.envium.enviummod.core.tileentity.TileEnviumFurnace;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegisterTileEntity {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, References.MODID);

    public static final RegistryObject<TileEntityType<TileEnviumChest>> TILE_ENVIUM_CHEST = TILE_ENTITIES.register("tile_envium_chest", () -> TileEntityType.Builder.of(TileEnviumChest::new, RegisterBlock.envium_chest).build(null));
    public static final RegistryObject<TileEntityType<TileEnviumFurnace>> TILE_ENVIUM_FURNACE = TILE_ENTITIES.register("tile_envium_furnace", () -> TileEntityType.Builder.of(TileEnviumFurnace::new, RegisterBlock.envium_furnace).build(null));

    //public static final RegistryObject<TileEntityType<IronChestTileEntity>> TILE_ENVIUM_IRON_CHEST = TILE_ENTITIES.register("tile_envium_iron_chest", () -> TileEntityType.Builder.create(IronChestTileEntity::new, RegisterBlock.envium_chest).build(null));

}
