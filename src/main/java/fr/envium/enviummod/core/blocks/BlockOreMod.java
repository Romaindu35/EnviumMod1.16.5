package fr.envium.enviummod.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class BlockOreMod extends Block
{
	private static boolean multipleQuantity = false;
	private static int minDrop;
	private static int maxDrop;

	public BlockOreMod(int minDrop, int maxDrop)
	{
		super(Properties.create(Material.ROCK)
				.harvestTool(ToolType.PICKAXE)
				.hardnessAndResistance(2, 8)
				.harvestLevel(3));
		//setRegistryName("envium_ore");
		multipleQuantity = true;
		BlockOreMod.minDrop = minDrop;
		BlockOreMod.maxDrop = maxDrop;
	}

	public Item getItemDropped(BlockState state, Random rand, int fortune)
	{
			return Item.getItemFromBlock(this);
	}
	
	public int quantityDropped(Random rand)
	{
		return multipleQuantity ? minDrop + rand.nextInt(maxDrop - minDrop) : 1;
	}
	
	/*@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
	{
		Random rand = world instanceof World ? ((World)world).rand : new Random();
		
		if(this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this))
		{
			if(this == BlocksMod.envium_ore)
			{
				return MathHelper.getInt(rand, 1, 5);
			}
		}
		
		return 0;
	}*/
}
