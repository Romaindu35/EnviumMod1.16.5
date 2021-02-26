package fr.envium.enviummod.core.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockCaveBlock extends Block {
	
	public BlockCaveBlock()
	{

		super(Block.Properties.create(Material.GLASS).hardnessAndResistance(0.3F).sound(SoundType.GLASS));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
	}

	@Override
	public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return 0;
	}
}
