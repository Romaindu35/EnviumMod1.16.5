package fr.envium.enviummod.core.blocks.wood;

import net.minecraft.block.DoorBlock;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class BlockDoor extends DoorBlock {
    public BlockDoor() {
        super(Properties.create(Material.WOOD)
                .hardnessAndResistance(2.0F, 6.0F)
                .harvestTool(ToolType.AXE)
                .harvestLevel(1));
    }
}
