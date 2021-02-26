package fr.envium.enviummod.addons.ironchest.common.block;

import fr.envium.enviummod.addons.ironchest.common.block.tileentity.CopperChestTileEntity;
import fr.envium.enviummod.addons.ironchest.common.block.tileentity.IronChestsTileEntityTypes;
import fr.envium.enviummod.addons.ironchest.common.block.tileentity.SilverChestTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class SilverChestBlock extends GenericIronChestBlock {

  public SilverChestBlock(Properties properties) {
    super(IronChestsTypes.SILVER, IronChestsTileEntityTypes.SILVER_CHEST::get, properties);
  }

  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new SilverChestTileEntity();
  }
}
