package fr.envium.enviummod.core.blocks;

import fr.envium.enviummod.core.other.SoundTypeMod;
import net.minecraft.block.Block;
import net.minecraft.block.BreakableBlock;
import net.minecraft.block.material.Material;

public class BlockPillow extends BreakableBlock {
    public BlockPillow() {
        super(Block.Properties.create(Material.ROCK)
            .sound(SoundTypeMod.PILLOW).notSolid());
        //setRegistryName("pillow");
    }
}
