package fr.envium.enviummod.addons.ironchest.common.block.tileentity;

import fr.envium.enviummod.addons.ironchest.common.block.IronChestsBlocks;
import fr.envium.enviummod.addons.ironchest.common.block.IronChestsTypes;
import fr.envium.enviummod.addons.ironchest.common.inventory.IronChestContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;

public class GoldChestTileEntity extends GenericIronChestTileEntity {

  public GoldChestTileEntity() {
    super(IronChestsTileEntityTypes.GOLD_CHEST.get(), IronChestsTypes.GOLD, IronChestsBlocks.GOLD_CHEST::get);
  }

  @Override
  protected Container createMenu(int id, PlayerInventory playerInventory) {
    return IronChestContainer.createGoldContainer(id, playerInventory, this);
  }
}
