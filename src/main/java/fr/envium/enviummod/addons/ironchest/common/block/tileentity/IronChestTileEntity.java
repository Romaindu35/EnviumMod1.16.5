package fr.envium.enviummod.addons.ironchest.common.block.tileentity;

import fr.envium.enviummod.addons.ironchest.common.block.IronChestsBlocks;
import fr.envium.enviummod.addons.ironchest.common.block.IronChestsTypes;

public class IronChestTileEntity extends GenericIronChestTileEntity {

  public IronChestTileEntity() {
    super(IronChestsTileEntityTypes.IRON_CHEST.get(), IronChestsTypes.IRON, IronChestsBlocks.IRON_CHEST::get);
  }
}
