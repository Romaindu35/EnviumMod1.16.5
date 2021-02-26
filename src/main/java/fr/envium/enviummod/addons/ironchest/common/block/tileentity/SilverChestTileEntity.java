package fr.envium.enviummod.addons.ironchest.common.block.tileentity;

import fr.envium.enviummod.addons.ironchest.common.block.IronChestsBlocks;
import fr.envium.enviummod.addons.ironchest.common.block.IronChestsTypes;
import fr.envium.enviummod.addons.ironchest.common.inventory.IronChestContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class SilverChestTileEntity extends GenericIronChestTileEntity {

  public SilverChestTileEntity() {
    super(IronChestsTileEntityTypes.SILVER_CHEST.get(), IronChestsTypes.SILVER, IronChestsBlocks.SILVER_CHEST::get);
  }

  @Override
  protected Container createMenu(int id, PlayerInventory playerInventory) {
    return IronChestContainer.createSilverContainer(id, playerInventory, this);
  }
}