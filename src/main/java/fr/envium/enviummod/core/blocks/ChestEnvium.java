package fr.envium.enviummod.core.blocks;

import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ChestTileEntity;
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

import javax.annotation.Nullable;
import java.util.List;

public class ChestEnvium extends Block implements IWaterLoggable {
    public static final DirectionProperty FACING = HorizontalBlock.FACING;

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape IRON_CHEST_SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);


    public ChestEnvium() {
        super(Block.Properties.of(Material.STONE)
                .strength(2, 6));

        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }

        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return IRON_CHEST_SHAPE;
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

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        TileEntity tileentity = worldIn.getBlockEntity(pos);

        if (tileentity instanceof ChestTileEntity) {
            if (stack.hasCustomHoverName()) {
                ((ChestTileEntity) tileentity).setCustomName(stack.getHoverName());
            }
        }
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = worldIn.getBlockEntity(pos);

            if (tileentity instanceof ChestTileEntity) {
                InventoryHelper.dropContents(worldIn, pos, (ChestTileEntity) tileentity);
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isClientSide) {
            INamedContainerProvider inamedcontainerprovider = this.getMenuProvider(state, worldIn, pos);
            if (inamedcontainerprovider != null) {
                player.openMenu(inamedcontainerprovider);
                player.awardStat(this.getOpenStat());
            }

        }
        return ActionResultType.SUCCESS;
    }

    protected Stat<ResourceLocation> getOpenStat() {
        return Stats.CUSTOM.get(Stats.OPEN_CHEST);
    }

    @Override
    @Nullable
    public INamedContainerProvider getMenuProvider(BlockState state, World world, BlockPos pos) {
        TileEntity tileentity = world.getBlockEntity(pos);
        return tileentity instanceof INamedContainerProvider ? (INamedContainerProvider) tileentity : null;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public boolean triggerEvent(BlockState state, World worldIn, BlockPos pos, int id, int param) {
        super.triggerEvent(state, worldIn, pos, id, param);
        TileEntity tileentity = worldIn.getBlockEntity(pos);
        return tileentity != null && tileentity.triggerEvent(id, param);
    }

    private static boolean isBlocked(IWorld iWorld, BlockPos blockPos) {
        return isBelowSolidBlock(iWorld, blockPos) || isCatSittingOn(iWorld, blockPos);
    }

    private static boolean isBelowSolidBlock(IBlockReader iBlockReader, BlockPos worldIn) {
        BlockPos blockpos = worldIn.above();
        return iBlockReader.getBlockState(blockpos).isRedstoneConductor(iBlockReader, blockpos);
    }

    private static boolean isCatSittingOn(IWorld iWorld, BlockPos blockPos) {
        List<CatEntity> list = iWorld.getEntitiesOfClass(CatEntity.class, new AxisAlignedBB(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 2, blockPos.getZ() + 1));
        if (!list.isEmpty()) {
            for (CatEntity catentity : list) {
                if (catentity.orderedToSit) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, World worldIn, BlockPos pos) {
        return Container.getRedstoneSignalFromContainer((IInventory) worldIn.getBlockEntity(pos));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public boolean isPathfindable(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }


    /*@OnlyIn(Dist.CLIENT)
    public static TileEntityMerger.ICallback<ChestTileEntity, Float2FloatFunction> getLid(final IChestLid p_226917_0_) {
        return new TileEntityMerger.ICallback<ChestTileEntity, Float2FloatFunction>() {
            @Override
            public Float2FloatFunction acceptDouble(ChestTileEntity p_225539_1_, ChestTileEntity p_225539_2_) {
                return (p_226921_2_) -> {
                    return Math.max(p_225539_1_.getLidAngle(p_226921_2_), p_225539_2_.getLidAngle(p_226921_2_));
                };
            }

            @Override
            public Float2FloatFunction acceptSingle(ChestTileEntity p_225538_1_) {
                return p_225538_1_::getLidAngle;
            }

            @Override
            public Float2FloatFunction acceptNone() {
                return p_226917_0_::getLidAngle;
            }
        };
    }*/


    public static TileEntityMerger.Type getMergerType(BlockState blockState) {
        return TileEntityMerger.Type.SINGLE;
    }

    public static Direction getDirectionToAttached(BlockState state) {
        Direction direction = state.getValue(FACING);
        return direction.getCounterClockWise();
    }
}
