package fr.envium.enviummod.core.blocks;

import fr.envium.enviummod.core.tileentity.TileEnviumFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockFurnaceMod extends Block {

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	public static final BooleanProperty LIT = BooleanProperty.create("lit");

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, LIT);

	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
		return state.getValue(LIT) ? super.getLightValue(state, world, pos) : 0;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}

	public BlockFurnaceMod() {
		super(Block.Properties.of(Material.STONE));
		this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
	}

	@Override
	public void setPlacedBy(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			TileEntity tileentity = worldIn.getBlockEntity(pos);

			if (tileentity instanceof TileEnviumFurnace) {
				((TileEnviumFurnace) tileentity).setCustomName(stack
						.getHoverName());
			}
		}
		super.setPlacedBy(worldIn, pos, state, placer, stack);
	}

	@Override
	public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit)
	{
		if(hand.equals(Hand.MAIN_HAND))
		{
			if (world.isClientSide)
			{
				return ActionResultType.PASS;
			}
			else
			{
				TileEntity tileentity = world.getBlockEntity(pos);
				if(tileentity instanceof TileEnviumFurnace)
				{
					NetworkHooks.openGui((ServerPlayerEntity) player, (TileEnviumFurnace) tileentity, pos);
					return ActionResultType.SUCCESS;
				}

			}
		}

		return ActionResultType.FAIL;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new TileEnviumFurnace();
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Override
	public void destroy(IWorld worldIn, @NotNull BlockPos pos, @NotNull BlockState state) {
		TileEntity tileentity = worldIn.getBlockEntity(pos);

		if (tileentity instanceof TileEnviumFurnace) {
			InventoryHelper.dropContents((World) worldIn, pos,
					(TileEnviumFurnace) tileentity);
			System.out.println("destroy");
		}

		super.destroy(worldIn, pos, state);
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, World worldIn, BlockPos pos) {
		return Container.getRedstoneSignalFromBlockEntity(worldIn.getBlockEntity(pos));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (stateIn.getValue(LIT)) {
			double d0 = (double) pos.getX() + 0.5D;
			double d1 = pos.getY();
			double d2 = (double) pos.getZ() + 0.5D;
			if (rand.nextDouble() < 0.1D) {
				worldIn.playLocalSound(d0, d1, d2, SoundEvents.FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 1.0F, 1.0F,
						false);
			}

			Direction direction = stateIn.getValue(FACING);
			Direction.Axis direction$axis = direction.getAxis();
			double d4 = rand.nextDouble() * 0.6D - 0.3D;
			double d5 = direction$axis == Direction.Axis.X ? (double) direction.getStepX() * 0.52D : d4;
			double d6 = rand.nextDouble() * 6.0D / 16.0D;
			double d7 = direction$axis == Direction.Axis.Z ? (double) direction.getStepZ() * 0.52D : d4;
			worldIn.addParticle(ParticleTypes.SMOKE, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
			worldIn.addParticle(ParticleTypes.FLAME, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
		}
	}

	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity tileentity = worldIn.getBlockEntity(pos);
			if (tileentity instanceof TileEnviumFurnace) {
				InventoryHelper.dropContents(worldIn, pos, (TileEnviumFurnace)tileentity);
				worldIn.updateNeighbourForOutputSignal(pos, this);
			}

			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}

	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.MODEL;
	}
}
