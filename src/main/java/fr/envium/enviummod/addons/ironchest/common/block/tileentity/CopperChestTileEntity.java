package fr.envium.enviummod.addons.ironchest.common.block.tileentity;

import fr.envium.enviummod.addons.ironchest.common.block.IronChestsBlocks;
import fr.envium.enviummod.addons.ironchest.common.block.IronChestsTypes;
import fr.envium.enviummod.addons.ironchest.common.inventory.IronChestContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class CopperChestTileEntity extends GenericIronChestTileEntity {

  public CopperChestTileEntity() {
    super(IronChestsTileEntityTypes.COPPER_CHEST.get(), IronChestsTypes.COPPER, IronChestsBlocks.COPPER_CHEST::get);
  }

  @Override
  protected Container createMenu(int id, PlayerInventory playerInventory) {
    return IronChestContainer.createCopperContainer(id, playerInventory, this);
  }
}