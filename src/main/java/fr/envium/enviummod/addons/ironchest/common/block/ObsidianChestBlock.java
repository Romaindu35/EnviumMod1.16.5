package fr.envium.enviummod.addons.ironchest.common.block;

import fr.envium.enviummod.addons.ironchest.common.block.tileentity.IronChestsTileEntityTypes;
import fr.envium.enviummod.addons.ironchest.common.block.tileentity.ObsidianChestTileEntity;
import fr.envium.enviummod.addons.ironchest.common.block.tileentity.SilverChestTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class ObsidianChestBlock extends GenericIronChestBlock {

  public ObsidianChestBlock(Properties properties) {
    super(IronChestsTypes.OBSIDIAN, IronChestsTileEntityTypes.OBSIDIAN_CHEST::get, properties);
  }

  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new ObsidianChestTileEntity();
  }
}
