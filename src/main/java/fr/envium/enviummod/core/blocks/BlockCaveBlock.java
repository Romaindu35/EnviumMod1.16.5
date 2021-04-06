package fr.envium.enviummod.core.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockCaveBlock extends Block {
	
	public BlockCaveBlock()
	{

		super(Block.Properties.of(Material.GLASS).strength(0.3F).sound(SoundType.GLASS));
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
	}

	@Override
	public int getLightBlock(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 0;
	}
}
