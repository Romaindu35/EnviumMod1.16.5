package fr.envium.enviummod.core.World;

import fr.envium.enviummod.api.init.RegisterBlock;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

public class ModOreGen {

    public static void generateOre() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            //if (biome == Biomes.PLAINS) {
                ConfiguredPlacement customConfig = Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 5, 5, 25));
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.ORE
                        .withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, RegisterBlock.envium_ore.getDefaultState(), 5))
                        .withPlacement(customConfig));
            //}
        }
        /*for (int i = 0; i< 100; i++)
            System.out.println(i);
        for (ResourceLocation recipe : ForgeRegistries.RECIPE_SERIALIZERS.getKeys()) {
            System.out.println(ForgeRegistries.RECIPE_SERIALIZERS.getValue(recipe));
        }*/
    }

}
