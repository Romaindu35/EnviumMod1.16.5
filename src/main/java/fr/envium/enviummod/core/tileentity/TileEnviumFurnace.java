package fr.envium.enviummod.core.tileentity;

import fr.envium.enviummod.core.container.ContainerEnviumFurnace;
import fr.envium.enviummod.api.init.RegisterItem;
import fr.envium.enviummod.api.init.RegisterTileEntity;
import fr.envium.enviummod.api.packets.NetworkRegistryHandler;
import fr.envium.enviummod.core.packets.PacketUpdateTileEntityData;
import io.netty.buffer.Unpooled;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TileEnviumFurnace extends LockableTileEntity implements ITickableTileEntity, ISidedInventory {

    private NonNullList<ItemStack> stacks = NonNullList.withSize(5, ItemStack.EMPTY);
    private String customName;
    private int	timePassed = 0;
    private int	burningTimeLeft	= 0;
    private int id;
    private Map<String, Integer> data = new HashMap<>();

    protected TileEnviumFurnace(TileEntityType<?> typeIn) {
        super(typeIn);
    }

    public TileEnviumFurnace()
    {
        super(RegisterTileEntity.TILE_ENVIUM_FURNACE.get());
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.stacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.stacks);

        if (compound.hasUniqueId("CustomName")) {
            this.customName = compound.getString("CustomName");
        }
        this.burningTimeLeft = compound.getInt("burningTimeLeft");
        this.timePassed = compound.getInt("timePassed");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        ItemStackHelper.saveAllItems(compound, this.stacks);

        if (this.hasCustomName()) {
            compound.putString("CustomName", this.customName);
        }

        compound.putInt("burningTimeLeft", this.burningTimeLeft);
        compound.putInt("timePassed", this.timePassed);

        return compound;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return true;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new StringTextComponent("tile.enviummod.envium_furnace");
    }

    @Override
    public boolean hasCustomName() {
        return this.customName != null && !this.customName.isEmpty();
    }


    public int getField(int id) {
        switch (id) {
            case 0:
                return this.burningTimeLeft;
            case 1:
                return this.timePassed;
        }
        return 0;
    }

    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.burningTimeLeft = value;
                break;
            case 1:
                this.timePassed = value;
        }
    }

    public int getFieldCount() {
        return 2;
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        /*this.data.put("timePassed", this.timePassed);
        this.data.put("burnTimeLeft", this.burningTimeLeft);
        NetworkRegistryHandler.network.send(PacketDistributor.ALL.noArg(), new PacketUpdateTileEntityData(id, this.data));*/
        this.id = id;
        //NetworkRegistryHandler.network.send(PacketDistributor.ALL.noArg(), new MySimplePacket(12));
        return new ContainerEnviumFurnace(id, player, new PacketBuffer(Unpooled.buffer()).writeBlockPos(getPos()).writeVarInt(getFullBurnTime()));
    }


    @Override
    public int getSizeInventory() {
        return this.stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.stacks) {
            if (!stack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return this.stacks.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.stacks, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(stacks, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.stacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        return this.world.getTileEntity(this.pos) != this ? false : player
                .getDistanceSq((double) this.pos.getX() + 0.5D,
                        (double) this.pos.getY() + 0.5D,
                        (double) this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void clear() {
        for(int i = 0; i < this.stacks.size(); i++) {
            this.stacks.set(i, ItemStack.EMPTY);
        }
    }

    public boolean hasFuelEmpty() {
        return this.getStackInSlot(0).isEmpty();
    }

    public ItemStack getRecipeResult() {
        IInventory iInventory = new Inventory(this.getStackInSlot(1));
        Optional<FurnaceRecipe> recipeOptionnal = this.world.getRecipeManager().getRecipe(IRecipeType.SMELTING, iInventory, this.world);
        //System.out.println(recipeOptionnal.isPresent());
        //System.out.println(recipeOptionnal.toString());
        ItemStack itemStack = recipeOptionnal.map(recipe -> recipe.getCraftingResult(iInventory)).orElse(ItemStack.EMPTY);
        //System.out.println(itemStack);
        //System.out.println(recipeOptionnal.map(recipe -> recipe.getCraftingResult(iInventory)).orElse(ItemStack.EMPTY));
        //return RecipesFurnace.getRecipeResult(new ItemStack[] { this.getStackInSlot(1) });
        return itemStack;
    }

    public boolean canSmelt() {
        ItemStack result = this.getRecipeResult();
        if (result != ItemStack.EMPTY && result != null) {
            ItemStack slot3 = this.getStackInSlot(3);
            if (slot3.isEmpty())
                return true;
            if (slot3.getItem() == result.getItem() && slot3.getDamage() == result.getDamage()) {
                int newStackSize = slot3.getCount() + result.getCount();
                if (newStackSize <= this.getInventoryStackLimit() && newStackSize <= slot3.getMaxStackSize()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void smelt() {
        ItemStack result = this.getRecipeResult();
        this.decrStackSize(1, 1);
        ItemStack stack3 = this.getStackInSlot(3);
        ItemStack shard = this.getStackInSlot(2);
        if (stack3.isEmpty()) {
            this.setInventorySlotContents(3, result.copy());
            if(shard.getItem() == RegisterItem.envium_shard_quantity) {
                ItemStack stack3_reply = this.getStackInSlot(3);
                if (!stack3_reply.isEmpty()) {
                    stack3_reply.setCount(stack3_reply.getCount() + result.getCount());
                }
            }
        } else {
            if(shard.getItem() == RegisterItem.envium_shard_quantity)
                stack3.setCount(stack3.getCount() + result.getCount() * 2);
            else
                stack3.setCount(stack3.getCount() + result.getCount());
        }
    }

    public int getFullRecipeTime() {
        ItemStack upgrade = this.getStackInSlot(2);
        if(upgrade.getCount() == 0) {
            return 180;
        }
        if(upgrade.getCount() > 16) {
            return 180 / 16 * 5;
        }
        if(upgrade.getCount() < 7 && upgrade.getCount() > 0) {
            return 160;
        }
        return 180 / upgrade.getCount() * 5;
    }

    public int getFullBurnTime() {
        return 300;
    }

    public boolean isBurning() {
        return burningTimeLeft > 0;
    }

    @Override
    public void tick() {
        if (!this.world.isRemote) {
            if (this.isBurning()) {
                this.burningTimeLeft--;
            }
            if (!this.isBurning() && this.canSmelt() && !this.hasFuelEmpty()) {
                this.burningTimeLeft = this.getFullBurnTime();
                this.decrStackSize(0, 1);
            }

            if (this.isBurning() && this.canSmelt()) {
                this.world.setBlockState(this.pos, this.getBlockState().with(FurnaceBlock.LIT, true));
                this.timePassed++;
                if (timePassed >= this.getFullRecipeTime()) {
                    timePassed = 0;
                    this.smelt();
                }
            } else {
                timePassed = 0;
            }
            if (!isBurning()) {
                this.world.setBlockState(this.pos, this.getBlockState().with(FurnaceBlock.LIT, false));
            }
            data.put("burningTimeLeft", burningTimeLeft);
            data.put("fullBurningTime", getFullBurnTime());
            data.put("timePassed", timePassed);
            data.put("fullTimeRecipe", getFullRecipeTime());
            data.put("isBurning", isBurning() ? 1 : 0);
            NetworkRegistryHandler.network.send(PacketDistributor.ALL.noArg(), new PacketUpdateTileEntityData(id, data));
            this.markDirty();
        }
    }

    public int getTimePassed() {
        return timePassed;
    }

    public int getBurningTimeLeft() {
        return burningTimeLeft;
    }

    public NonNullList getInventory() {
        return stacks;
    }
}
