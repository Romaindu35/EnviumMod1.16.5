package fr.envium.enviummod.core.blocks.wood;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockWart extends Block {
    public BlockWart() {
        super(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid());
    }
}
