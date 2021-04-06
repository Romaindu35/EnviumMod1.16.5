package fr.envium.enviummod.api.utils;

import net.minecraft.block.Block;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.server.ServerWorld;

public class ChunkUtils {
    @SuppressWarnings("resource")
    public static void regenerateChunk(ServerWorld world, int x, int z)
    {
        System.out.println(world);
        System.out.println(x);
        System.out.println(z);
        ChunkGenerator gen = world.getChunkSource().generator;
        Chunk chunk = world.getChunk(x, z);
        System.out.println(chunk);
        try
        {
            System.out.println("0");
            System.out.println(world.getServer());
            world.getServer().submitAsync(() ->
            {
                /*gen.generateBiomes(chunk);
                gen.makeBase(world, chunk);*/
                System.out.println("1");
                for(int posX = 0; posX < 16; posX++)
                    for(int posZ = 0; posZ < 16; posZ++)
                    {
                        System.out.println("3");
                        BlockPos pos = new BlockPos(posX * 16, 100, posZ + z * 16);
                        Biome biome = chunk.getBiomes().getNoiseBiome(pos.getX(), pos.getY(), pos.getZ());

                        for(Decoration deco : Decoration.values()) {
                            //biome.decorate(deco, gen, world, world.getSeed(), new SharedSeedRandom(world.getSeed()), pos);
                            System.out.println("4");
                        }
                    }

            });
        }
        catch(Exception e)
        {
            System.out.println("erreur");
            //EnviumMod.LOGGER.log(Level.ERROR, "Error while regenerate chunk at X:" + chunk.getPos().x + " Z: " + chunk.getPos().z + " Or X:" + chunk.getPos().x * 16 + " Z:" + chunk.getPos().z * 16);
        }

    }

    public static int getSpecifiedBlockInChunk(Chunk chunk, World world, Block block)
    {
        int count = 0;

        int x = chunk.getPos().x;
        int z = chunk.getPos().z;

        for(int posX = 0; posX < 16; posX++)
            for(int posZ = 0; posZ < 16; posZ++)
            {
                for(int y = 0; y < 255; y++)
                {
                    BlockPos pos = new BlockPos(posX + x * 16, y, posZ + z * 16);
                    Block block1 = chunk.getBlockState(pos).getBlock();

                    if(block1 == block)
                        count++;

                    if(world.canSeeSkyFromBelowWater(pos))
                        break;
                }
            }

        return count;
    }
}

