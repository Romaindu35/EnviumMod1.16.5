package fr.envium.enviummod.addons.ironchest.common.block.tileentity;

import fr.envium.enviummod.addons.ironchest.common.block.IronChestsBlocks;
import fr.envium.enviummod.addons.ironchest.common.block.IronChestsTypes;
import fr.envium.enviummod.addons.ironchest.common.inventory.IronChestContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class DiamondChestTileEntity extends GenericIronChestTileEntity {

  public DiamondChestTileEntity() {
    super(IronChestsTileEntityTypes.DIAMOND_CHEST.get(), IronChestsTypes.DIAMOND, IronChestsBlocks.DIAMOND_CHEST::get);
  }

  @Override
  protected Container createMenu(int id, PlayerInventory playerInventory) {
    return IronChestContainer.createDiamondContainer(id, playerInventory, this);
  }
}
