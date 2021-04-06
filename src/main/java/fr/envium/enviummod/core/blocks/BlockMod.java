package fr.envium.enviummod.core.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

import net.minecraft.block.AbstractBlock.Properties;

public class BlockMod extends Block {
    public BlockMod() {
        super(Properties.of(Material.STONE)
                .strength(2.0F, 6.0F)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1));
        //setRegistryName("envium_block");
    }
}
