package fr.envium.enviummod.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class BlockMod extends Block {
    public BlockMod() {
        super(Properties.create(Material.ROCK)
                .hardnessAndResistance(2.0F, 6.0F)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1));
        //setRegistryName("envium_block");
    }
}
