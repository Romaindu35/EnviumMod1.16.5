package fr.envium.enviummod.core.blocks;

import fr.envium.enviummod.api.init.RegisterTileEntity;
import fr.envium.enviummod.core.tileentity.TileEnviumChest;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.BiPredicate;

public class EnviumChest extends Block {

	public static final DirectionProperty FACING = HorizontalBlock.FACING;

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	protected static final VoxelShape CHEST_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);

	public EnviumChest() {
		super(Block.Properties.of(Material.STONE)
			.strength(2, 6));
		this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return CHEST_SHAPE;
	}

	@Override
	protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACING, WATERLOGGED);

	}

	@Override
	public void setPlacedBy(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		if (stack.hasCustomHoverName()) {
			TileEntity tileentity = worldIn.getBlockEntity(pos);

			if (tileentity instanceof TileEnviumChest) {
				((TileEnviumChest) tileentity).setCustomName(stack
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

				if(tileentity instanceof TileEnviumChest)
				{
					NetworkHooks.openGui((ServerPlayerEntity) player, (TileEnviumChest) tileentity, pos);
					System.out.println("open gui");

					return ActionResultType.SUCCESS;
				}

			}
		}

		return ActionResultType.FAIL;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new TileEnviumChest();
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Override
	public void destroy(IWorld worldIn, @NotNull BlockPos pos, @NotNull BlockState state) {
		TileEntity tileentity = worldIn.getBlockEntity(pos);

		if (tileentity instanceof TileEnviumChest) {
			InventoryHelper.dropContents((World) worldIn, pos,
					(TileEnviumChest) tileentity);
		}

		super.destroy(worldIn, pos, state);
	}

	public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			TileEntity tileentity = worldIn.getBlockEntity(pos);
			if (tileentity instanceof TileEnviumChest) {
				InventoryHelper.dropContents(worldIn, pos, (TileEnviumChest)tileentity);
				worldIn.updateNeighbourForOutputSignal(pos, this);
			}

			super.onRemove(state, worldIn, pos, newState, isMoving);
		}
	}

	public TileEntityMerger.ICallbackWrapper<TileEnviumChest> getWrapper(BlockState blockState, World world, BlockPos blockPos, boolean p_225536_4_) {
		BiPredicate<IWorld, BlockPos> biPredicate;
		if (p_225536_4_) {
			biPredicate = (p_226918_0_, p_226918_1_) -> false;
		}
		else {
			biPredicate = EnviumChest::isBlocked;
		}

		return TileEntityMerger.combineWithNeigbour(RegisterTileEntity.TILE_ENVIUM_CHEST.get(), EnviumChest::getMergerType, EnviumChest::getDirectionToAttached, FACING, blockState, world, blockPos, biPredicate);
	}

	public static TileEntityMerger.Type getMergerType(BlockState blockState) {
		return TileEntityMerger.Type.SINGLE;
	}

	public static Direction getDirectionToAttached(BlockState state) {
		Direction direction = state.getValue(FACING);
		return direction.getCounterClockWise();
	}

	/*@OnlyIn(Dist.CLIENT)
	public static TileEntityMerger.ICallback<TileEnviumChest, Float2FloatFunction> getLid(final IChestLid p_226917_0_) {
		return new TileEntityMerger.ICallback<TileEnviumChest, Float2FloatFunction>() {
			@Override
			public Float2FloatFunction acceptDouble(TileEnviumChest p_225539_1_, TileEnviumChest p_225539_2_) {
				return (p_226921_2_) -> {
					return Math.max(p_225539_1_.getLidAngle(p_226921_2_), p_225539_2_.getLidAngle(p_226921_2_));
				};
			}

			@Override
			public Float2FloatFunction acceptSingle(TileEnviumChest p_225538_1_) {
				return p_225538_1_::getLidAngle;
			}

			@Override
			public Float2FloatFunction acceptNone() {
				return p_226917_0_::getLidAngle;
			}
		};
	}
*/
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	/*@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}*/

	@Override
	public BlockRenderType getRenderShape(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		Direction direction = context.getHorizontalDirection().getOpposite();
		FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());

		return this.defaultBlockState().setValue(FACING, direction).setValue(WATERLOGGED, ifluidstate.getType() == Fluids.WATER);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	private static boolean isBlocked(IWorld iWorld, BlockPos blockPos) {
		return isBelowSolidBlock(iWorld, blockPos) || isCatSittingOn(iWorld, blockPos);
	}

	private static boolean isBelowSolidBlock(IBlockReader iBlockReader, BlockPos worldIn) {
		BlockPos blockpos = worldIn.above();
		return iBlockReader.getBlockState(blockpos).isRedstoneConductor(iBlockReader, blockpos);
	}

	private static boolean isCatSittingOn(IWorld iWorld, BlockPos blockPos) {
		List<CatEntity> list = iWorld.getEntitiesOfClass(CatEntity.class, new AxisAlignedBB((double) blockPos.getX(), (double) (blockPos.getY() + 1), (double) blockPos.getZ(), (double) (blockPos.getX() + 1), (double) (blockPos.getY() + 2), (double) (blockPos.getZ() + 1)));
		if (!list.isEmpty()) {
			for (CatEntity catentity : list) {
				if (catentity.orderedToSit) {
					return true;
				}
			}
		}

		return false;
	}
}
