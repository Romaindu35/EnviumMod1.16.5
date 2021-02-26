package fr.envium.enviummod.core.World;

public class WorldGenMod
{

	/*/@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
	{
		switch(world.provider.getDimensionType())
		{
			case NETHER:
				GenerateNether(world, chunkX * 16, chunkZ * 16, random);
				break;
			case OVERWORLD:
				GenerateOverworld(world, chunkX * 16, chunkZ * 16, random);
				break;
			case THE_END:
				GenerateEnd(world, chunkX * 16, chunkZ * 16, random);
				break;
		}
	}

	private void addOre(Block block, Block blockSpawn, Random random, World world, int posX, int posZ, int minY, int maxY, int minFillon, int maxFillon, int spawnChance)
	{
		for(int i = 0; i < spawnChance; i++)
		{
			int defaultChunkSize = 16;
			int Xpos = posX + random.nextInt(defaultChunkSize);
			int Ypos = minY + random.nextInt(maxY - minY);
			int Zpos = posZ + random.nextInt(defaultChunkSize);
			
			IBlockState state = block.getDefaultState();
			BlockPos blockPos = new BlockPos(Xpos, Ypos, Zpos);
			
			new WorldGenMinable(state, maxFillon).generate(world, random, blockPos);
		}
	}
	
	private void GenerateNether(World world, int i, int j, Random random) {
	
	}

	private void GenerateOverworld(World world, int i, int j, Random random) {
		addOre(BlocksMod.envium_ore, Blocks.STONE, random, world, i, j, 2, 30, 1, 5, 10);
	}

	private void GenerateEnd(World world, int i, int j, Random random) {
		
	}*/
}
