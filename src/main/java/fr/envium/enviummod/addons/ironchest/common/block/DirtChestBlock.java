package fr.envium.enviummod.addons.ironchest.common.block;

import fr.envium.enviummod.addons.ironchest.common.block.tileentity.DirtChestTileEntity;
import fr.envium.enviummod.addons.ironchest.common.block.tileentity.IronChestsTileEntityTypes;
import fr.envium.enviummod.addons.ironchest.common.block.tileentity.ObsidianChestTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class DirtChestBlock extends GenericIronChestBlock {

  public DirtChestBlock(Properties properties) {
    super(IronChestsTypes.DIRT, IronChestsTileEntityTypes.DIRT_CHEST::get, properties);
  }

  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new DirtChestTileEntity();
  }
}
