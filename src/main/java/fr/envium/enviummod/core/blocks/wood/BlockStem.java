package fr.envium.enviummod.core.blocks.wood;

import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class BlockStem extends RotatedPillarBlock {

    private final MaterialColor verticalColor;

    public BlockStem(MaterialColor verticalColorIn) {
        super(Properties.create(Material.WOOD)
                .hardnessAndResistance(2.0F, 6.0F)
                .harvestTool(ToolType.AXE)
                .harvestLevel(1));
        this.verticalColor = verticalColorIn;
    }

    public MaterialColor getMaterialColor(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.get(AXIS) == Direction.Axis.Y ? this.verticalColor : this.materialColor;
    }
}
