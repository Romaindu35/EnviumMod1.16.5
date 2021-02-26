package fr.envium.enviummod.core.blocks.wood;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockHyphae extends RotatedPillarBlock {
    public BlockHyphae(MaterialColor verticalColor) {
        super(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid());
        this.verticalColor = verticalColor;
    }

    private final MaterialColor verticalColor;


    public MaterialColor getMaterialColor(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.get(AXIS) == Direction.Axis.Y ? this.verticalColor : this.materialColor;
    }
}
