package fr.envium.enviummod.core.blocks.wood;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class BlockPlank extends Block {

    public BlockPlank() {
        super(Properties.create(Material.WOOD)
                .hardnessAndResistance(2.0F, 6.0F)
                .harvestTool(ToolType.AXE)
                .harvestLevel(1));
    }

}
