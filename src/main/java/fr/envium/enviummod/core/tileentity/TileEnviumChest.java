package fr.envium.enviummod.core.tileentity;

import fr.envium.enviummod.api.init.RegisterTileEntity;
import fr.envium.enviummod.core.blocks.ChestEnvium;
import fr.envium.enviummod.core.container.ContainerEnviumChest;
import io.netty.buffer.Unpooled;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.jetbrains.annotations.Nullable;

public class TileEnviumChest extends LockableTileEntity implements ITickableTileEntity, ISidedInventory {

	private NonNullList<ItemStack> stacks = NonNullList.withSize(96, ItemStack.EMPTY);

	private int ticksSinceSync;
	protected float lidAngle;
	protected float prevLidAngle;
	protected int numPlayersUsing;

	protected TileEnviumChest(TileEntityType<?> typeIn) {
		super(typeIn);
	}

	public TileEnviumChest()
	{
		super(RegisterTileEntity.TILE_ENVIUM_CHEST.get());
	}

	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(nbt, this.stacks);
	}

	@Override
	public CompoundNBT save(CompoundNBT compound) {
		super.save(compound);
		ItemStackHelper.saveAllItems(compound, stacks);
		return compound;
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return new int[0];
	}

	@Override
	public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction) {
		return true;
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
		return false;
	}

	@Override
	protected ITextComponent getDefaultName() {
		return new StringTextComponent("coffre envium");
	}

	@Override
	protected Container createMenu(int id, PlayerInventory player) {
		return new ContainerEnviumChest(id, player, new PacketBuffer(Unpooled.buffer()).writeBlockPos(getBlockPos()));
	}

	@Override
	public int getContainerSize() {
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
	public ItemStack getItem(int index) {
		return this.stacks.get(index);
	}

	@Override
	public ItemStack removeItem(int index, int count) {
		return ItemStackHelper.removeItem(this.stacks, index, count);
	}

	@Override
	public ItemStack removeItemNoUpdate(int index) {
		return ItemStackHelper.takeItem(stacks, index);
	}

	@Override
	public void setItem(int index, ItemStack stack) {
		this.stacks.set(index, stack);

		if (stack.getCount() > this.getMaxStackSize())
		{
			stack.setCount(this.getMaxStackSize());
		}
	}

	@Override
	public boolean stillValid(PlayerEntity player) {
		return this.level.getBlockEntity(this.worldPosition) == this && player.distanceToSqr((double) this.worldPosition.getX() + 0.5D, (double) this.worldPosition.getY() + 0.5D, (double) this.worldPosition.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void clearContent() {
		for (int i = 0; i < this.stacks.size(); i++)
		{
			this.stacks.set(i, ItemStack.EMPTY);
		}
	}

	@Override
	public void tick() {
		int i = this.worldPosition.getX();
		int j = this.worldPosition.getY();
		int k = this.worldPosition.getZ();
		++this.ticksSinceSync;
		this.numPlayersUsing = ChestTileEntity.getOpenCount(this.level, this, this.ticksSinceSync, i, j, k, this.numPlayersUsing);
		this.prevLidAngle = this.lidAngle;
		float f = 0.1F;
		if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
			this.playSound(SoundEvents.CHEST_OPEN);
		}

		if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
			float f1 = this.lidAngle;
			if (this.numPlayersUsing > 0) {
				this.lidAngle += 0.1F;
			} else {
				this.lidAngle -= 0.1F;
			}

			if (this.lidAngle > 1.0F) {
				this.lidAngle = 1.0F;
			}

			float f2 = 0.5F;
			if (this.lidAngle < 0.5F && f1 >= 0.5F) {
				this.playSound(SoundEvents.CHEST_CLOSE);
			}

			if (this.lidAngle < 0.0F) {
				this.lidAngle = 0.0F;
			}
		}
	}

	private void playSound(SoundEvent soundIn) {
		ChestType chesttype = this.getBlockState().getValue(ChestBlock.TYPE);
		if (chesttype != ChestType.LEFT) {
			double d0 = (double)this.worldPosition.getX() + 0.5D;
			double d1 = (double)this.worldPosition.getY() + 0.5D;
			double d2 = (double)this.worldPosition.getZ() + 0.5D;
			if (chesttype == ChestType.RIGHT) {
				Direction direction = ChestEnvium.getDirectionToAttached(this.getBlockState());
				d0 += (double)direction.getStepX() * 0.5D;
				d2 += (double)direction.getStepZ() * 0.5D;
			}

			this.level.playSound(null, d0, d1, d2, soundIn, SoundCategory.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
		}
	}
}
