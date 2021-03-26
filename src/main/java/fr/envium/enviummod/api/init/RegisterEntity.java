package fr.envium.enviummod.api.init;

import fr.envium.enviummod.References;
import fr.envium.enviummod.animals.entity.Lirondel;
import fr.envium.enviummod.animals.entity.Marmot;
import fr.envium.enviummod.animals.entity.Meerkat;
import fr.envium.enviummod.animals.entity.Toucan;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegisterEntity {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, References.MODID);
    public static final RegistryObject<EntityType<Toucan>> TOUCAN_ENTITY = ENTITY_TYPES
            .register("toucan_entity",
                    () -> EntityType.Builder.<Toucan>create(Toucan::new, EntityClassification.CREATURE)
                            .size(0.8F, 0.5F)
                            .build(new ResourceLocation(References.MODID, "toucan_entity").toString()));

    public static final RegistryObject<EntityType<Lirondel>> LIRONDEL_ENTITY = ENTITY_TYPES
            .register("lirondel_entity",
                    () -> EntityType.Builder.<Lirondel>create(Lirondel::new, EntityClassification.CREATURE)
                            .size(0.9F, 0.5F)
                            .build(new ResourceLocation(References.MODID, "lirondel_entity").toString()));
    public static final RegistryObject<EntityType<Marmot>> MARMOT_ENTITY = ENTITY_TYPES
            .register("marmot_entity",
                    () -> EntityType.Builder.<Marmot>create(Marmot::new, EntityClassification.CREATURE)
                            .size(0.9F, 1.1F)
                            .build(new ResourceLocation(References.MODID, "marmot_entity").toString()));
    public static final RegistryObject<EntityType<Meerkat>> MEERKAT_ENTITY = ENTITY_TYPES
            .register("meerkat_entity",
                    () -> EntityType.Builder.<Meerkat>create(Meerkat::new, EntityClassification.CREATURE)
                            .size(0.9F, 1.2F)
                            .build(new ResourceLocation(References.MODID, "meerkat_entity").toString()));

}
