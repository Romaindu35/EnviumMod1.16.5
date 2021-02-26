package fr.envium.enviummod.core.container;

import fr.envium.enviummod.api.container.ContainerData;
import fr.envium.enviummod.core.tileentity.TileEnviumFurnace;
import fr.envium.enviummod.api.init.RegisterContainer;
import fr.envium.enviummod.api.init.RegisterItem;
import fr.envium.enviummod.core.items.EnviumShard;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;

import java.util.Map;

public class ContainerEnviumFurnace extends ContainerData {

    private TileEnviumFurnace tile;
    private PlayerInventory inv;
    private int	timePassed = 0;
    private int	burnTimeLeft = 0;
    private Map<String, Integer> data;


    public ContainerEnviumFurnace(int windowId, PlayerInventory playerInv, PacketBuffer data) {
        super(RegisterContainer.ENVIUM_FURNACE.get(), windowId);
        this.tile = (TileEnviumFurnace) playerInv.player.world.getTileEntity(data.readBlockPos());
        this.inv = playerInv;
        int i;

        this.addSlot(new SlotSingleItem(tile, 0, 56, 53, Items.COAL, Items.CHARCOAL, Items.LAVA_BUCKET));
        this.addSlot(new Slot(tile, 1, 56, 17));
        this.addSlot(new SlotEnviumShard(tile, 2, 19, 35, RegisterItem.envium_shard));
        this.addSlot(new SlotOutput(tile, 3, 116, 35));

        for(i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInv, i, 8 + i * 18, 142));
        }
    }


    @Override
    public boolean canInteractWith(PlayerEntity player) {
        return tile.isUsableByPlayer(player);
    }

    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
    }


    @Override
    public void updateProgressBar(int id, int data) {this.tile.setField(id, data);}

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack stackToReturn = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            stackToReturn = stack.copy();

            if (index < 5) {
                if (!this.mergeItemStack(stack, 5, 40, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index > 4) {
                if(stackToReturn.getItem() == RegisterItem.envium_shard) {
                    if (!this.mergeItemStack(stack, 2, 3, true)) {
                        return ItemStack.EMPTY;
                    }
                } else if(stackToReturn.getItem() == Items.COAL) {
                    if (!this.mergeItemStack(stack, 0, 1, true)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    if (!this.mergeItemStack(stack, 0, 4, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (stack.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

        }
        return stackToReturn;
    }

    public class SlotOutput extends Slot {

        public SlotOutput(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return false;
        }
    }

    public class SlotSingleItem extends Slot {

        private Item[] item;

        public SlotSingleItem(IInventory inventoryIn, int index, int xPosition, int yPosition, Item... item) {
            super(inventoryIn, index, xPosition, yPosition);
            this.item = item;
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            boolean containsItem = false;
            for (int i = 0; i < item.length; i++) {
                if (stack.getItem() == item[i])
                    containsItem = true;

            }
            return stack.isEmpty() || containsItem;
        }
    }

    public class SlotEnviumShard extends Slot {

        private Item item;

        public SlotEnviumShard(IInventory inventoryIn, int index, int xPosition, int yPosition, Item item) {
            super(inventoryIn, index, xPosition, yPosition);
            this.item = item;
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            if(stack.getItem() instanceof EnviumShard) {
                return true;
            }
            return false;
        }
    }

    public TileEnviumFurnace getTile() {
        return this.tile;
    }
}
