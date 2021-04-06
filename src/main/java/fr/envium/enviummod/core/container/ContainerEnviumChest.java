package fr.envium.enviummod.core.container;

import fr.envium.enviummod.core.tileentity.TileEnviumChest;
import fr.envium.enviummod.api.init.RegisterContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class ContainerEnviumChest extends Container {

    public TileEnviumChest tile;
    private PlayerInventory inv;


    public ContainerEnviumChest(int windowId, PlayerInventory playerInv, PacketBuffer data) {
        super(RegisterContainer.ENVIUM_CHEST.get(), windowId);

        this.tile = (TileEnviumChest) playerInv.player.level.getBlockEntity(data.readBlockPos());;
        this.inv = playerInv;

        int i;
        for(i = 0; i < 8; ++i) {
            for(int j = 0; j < 12; ++j) {
                this.addSlot(new Slot(tile, j + i * 12, -20 + j * 18,-27 + i * 18));
            }
        }



        for(i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInv, j + i * 9 + 9, 7 + j * 18, 121 + i * 18));
            }
        }

        for(i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInv, i, 7 + i * 18, 179));
        }
    }


    @Override
    public boolean stillValid(PlayerEntity player) {
        return tile.stillValid(player);
    }



    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack stackToReturn = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            stackToReturn = stack.copy();

            if (index < 96) {
                if (!this.moveItemStackTo(stack, 96, 132, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, 0, 96, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

        }
        return stackToReturn;
    }
}
