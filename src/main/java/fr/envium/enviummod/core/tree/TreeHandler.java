package fr.envium.enviummod.core.tree;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;

import java.util.*;

public class TreeHandler {

    private static Map<UUID, Tree> m_Trees = new HashMap<>();
    private Tree tree;

    private static <T> T getLastElement(final Iterable<T> elements) {
        final Iterator<T> itr = elements.iterator();
        T lastElement = itr.next();

        while (itr.hasNext()) {
            lastElement = itr.next();
        }

        return lastElement;
    }

    public int AnalyzeTree(World world, BlockPos blockPos, PlayerEntity playerEntity) {

        Queue<BlockPos> queuedBlocks = new LinkedList<>();
        Set<BlockPos> tmpBlocks = new HashSet<>();
        Set<BlockPos> checkedBlocks = new HashSet<>();
        BlockPos currentPos;
        Block logBlock = world.getBlockState(blockPos).getBlock();
        tree = new Tree();

        queuedBlocks.add(blockPos);
        tree.InsertWood(blockPos);

        while (!queuedBlocks.isEmpty()) {

            currentPos = queuedBlocks.remove();
            checkedBlocks.add(currentPos);

            tmpBlocks.addAll(LookAroundBlock(logBlock, currentPos, world, checkedBlocks));
            queuedBlocks.addAll(tmpBlocks);
            checkedBlocks.addAll(tmpBlocks);
            tmpBlocks.clear();
        }

        Set<BlockPos> tmpLeaves = new HashSet<>();
        tmpLeaves.addAll(tree.GetM_Leaves());

        for (BlockPos blockPos1 : tmpLeaves) {
            checkedBlocks.add(blockPos1);
            LookAroundBlock(null, blockPos1, world, checkedBlocks);
        }

        tree.setM_Position(blockPos);
        m_Trees.put(playerEntity.getUniqueID(), tree);

        return tree.GetLogCount();
    }

    private Queue<BlockPos> LookAroundBlock(Block logBlock, BlockPos currentPos, World world, Set<BlockPos> checkedBlocks) {

        Queue<BlockPos> queuedBlocks = new LinkedList<>();
        BlockPos tmpPos;

        for (int i = -1; i <= 1; i++) {
            tmpPos = new BlockPos(currentPos.getX() + 1, currentPos.getY() + i, currentPos.getZ());
            if (CheckBlock(world, tmpPos, checkedBlocks, logBlock)) {
                queuedBlocks.add(tmpPos);
            }

            tmpPos = new BlockPos(currentPos.getX(), currentPos.getY() + i, currentPos.getZ() + 1);
            if (CheckBlock(world, tmpPos, checkedBlocks, logBlock)) {
                queuedBlocks.add(tmpPos);
            }

            tmpPos = new BlockPos(currentPos.getX() - 1, currentPos.getY() + i, currentPos.getZ());
            if (CheckBlock(world, tmpPos, checkedBlocks, logBlock)) {
                queuedBlocks.add(tmpPos);
            }

            tmpPos = new BlockPos(currentPos.getX(), currentPos.getY() + i, currentPos.getZ() - 1);
            if (CheckBlock(world, tmpPos, checkedBlocks, logBlock)) {
                queuedBlocks.add(tmpPos);
            }

            tmpPos = new BlockPos(currentPos.getX() + 1, currentPos.getY() + i, currentPos.getZ() + 1);
            if (CheckBlock(world, tmpPos, checkedBlocks, logBlock)) {
                queuedBlocks.add(tmpPos);
            }

            tmpPos = new BlockPos(currentPos.getX() - 1, currentPos.getY() + i, currentPos.getZ() - 1);
            if (CheckBlock(world, tmpPos, checkedBlocks, logBlock)) {
                queuedBlocks.add(tmpPos);
            }

            tmpPos = new BlockPos(currentPos.getX() - 1, currentPos.getY() + i, currentPos.getZ() + 1);
            if (CheckBlock(world, tmpPos, checkedBlocks, logBlock)) {
                queuedBlocks.add(tmpPos);
            }

            tmpPos = new BlockPos(currentPos.getX() + 1, currentPos.getY() + i, currentPos.getZ() - 1);
            if (CheckBlock(world, tmpPos, checkedBlocks, logBlock)) {
                queuedBlocks.add(tmpPos);
            }

            tmpPos = new BlockPos(currentPos.getX(), currentPos.getY() + i, currentPos.getZ());
            if (CheckBlock(world, tmpPos, checkedBlocks, logBlock)) {
                queuedBlocks.add(tmpPos);
            }
        }

        return queuedBlocks;
    }

    private boolean CheckBlock(World world, BlockPos blockPos, Set<BlockPos> checkedBlocks, Block originBlock) {

        if (checkedBlocks.contains(blockPos)) {
            return false;
        }

        if (world.getBlockState(blockPos).getBlock() != originBlock) {

            if (world.getBlockState(blockPos).getBlock().canBeReplacedByLeaves(world.getBlockState(blockPos), world, blockPos) && tree.GetM_Leaves().isEmpty()) {
                tree.InsertLeaf(blockPos);
            }


            if (world.getBlockState(blockPos).getBlock().canBeReplacedByLeaves(world.getBlockState(blockPos), world, blockPos)) {
                tree.InsertLeaf(blockPos);

            }
            return false;
        }

        tree.InsertWood(blockPos);

        return true;
    }

    public void DestroyTree(World world, PlayerEntity playerEntity) {

        int soundReduced = 0;

        if (m_Trees.containsKey(playerEntity.getUniqueID())) {

            Tree tmpTree = m_Trees.get(playerEntity.getUniqueID());

            for (BlockPos blockPos : tmpTree.GetM_Wood()) {

                if (soundReduced <= 1) {
                    world.destroyBlock(blockPos, true);
                } else {
                    world.getBlockState(blockPos).getBlock().onBlockHarvested(world, blockPos, world.getBlockState(blockPos), playerEntity);
                }

                world.setBlockState(blockPos, Blocks.AIR.getDefaultState());

                soundReduced++;
            }

            if (!tmpTree.GetM_Leaves().isEmpty()) {

                BlockPos tmpPosition = getLastElement(tmpTree.GetM_Leaves());
                PlantSapling(world, tmpPosition, tmpTree.getM_Position());
            }

            soundReduced = 0;

            //if (ConfigurationHandler.decayLeaves) {

                for (BlockPos blockPos : tmpTree.GetM_Leaves()) {

                    if (soundReduced <= 1) {
                        world.destroyBlock(blockPos, true);
                    } else {
                        world.getBlockState(blockPos).getBlock().onBlockHarvested(world, blockPos, world.getBlockState(blockPos), playerEntity);
                    }

                    world.setBlockState(blockPos, Blocks.AIR.getDefaultState());

                    soundReduced++;
                }
            //}
        }
    }

    private void PlantSapling(World world, BlockPos blockPos, BlockPos originPos) {

        Set<ItemStack> leafDrop = new HashSet<>();
        BlockPos plantPos1 = new BlockPos(originPos.getX() - 1, originPos.getY(), originPos.getZ() - 1);
        int counter = 0;

        while (leafDrop.isEmpty() && counter <= 100) {
            NonNullList<ItemStack> tmpList = NonNullList.create();
            //world.getBlockState(blockPos).getBlock().(tmpList, world, blockPos, world.getBlockState(blockPos), 3);
            leafDrop.addAll(tmpList);

            counter++;
        }

        if (leafDrop.isEmpty()) {
            return;
        }

        FakePlayer fakePlayer = FakePlayerFactory.getMinecraft((ServerWorld) world);
        fakePlayer.setHeldItem(Hand.MAIN_HAND, leafDrop.iterator().next());

        for (ItemStack itemStack : leafDrop) {
            //itemStack.onItemUse(fakePlayer, world, plantPos1, Hand.MAIN_HAND, 0, 0, 0);
            //itemStack.onItemUse(new ItemUseContext(fakePlayer, Hand.MAIN_HAND, new BlockRayTraceResult()));
        }
    }
}
