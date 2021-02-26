package fr.envium.enviummod.addons.ironchest.common.block;

import fr.envium.enviummod.addons.ironchest.common.block.tileentity.IronChestTileEntity;
import fr.envium.enviummod.addons.ironchest.common.block.tileentity.IronChestsTileEntityTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class IronChestBlock extends GenericIronChestBlock {

  public IronChestBlock(Block.Properties properties) {
    super(IronChestsTypes.IRON, IronChestsTileEntityTypes.IRON_CHEST::get, properties);
  }

  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new IronChestTileEntity();
  }
}
