package fr.envium.enviummod.core.items.tools;

import fr.envium.enviummod.api.tab.EnviumTab;
import fr.envium.enviummod.core.other.ModMaterial;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;

public class ItemHammer extends PickaxeItem {
	
	//private static final Block[] effective_on = new Block[]{Blocks.COBBLESTONE, Blocks.STONE, Blocks.COAL_ORE, BlocksMod.envium_ore, BlocksMod.envium_block};
    private static Block theBlock;
 
    public ItemHammer() {
        super(ModMaterial.EnviumMaterial.ENVIUM_MATERIAL, 5, 8.0F, new Item.Properties().group(EnviumTab.TAB));
    }
 
    /*public RayTraceResult rayTrace(double blockReachDistance, float partialTicks, World w, EntityLivingBase e)
    {
        Vec3d vec3d = e.getPositionEyes(partialTicks);
        Vec3d vec3d1 = e.getLook(partialTicks);
        Vec3d vec3d2 = vec3d.addVector(vec3d1.x * blockReachDistance, vec3d1.y * blockReachDistance, vec3d1.z * blockReachDistance);
        return w.rayTraceBlocks(vec3d, vec3d2, false, false, true);
    }
    @Override
    public boolean onBlockDestroyed(ItemStack breaker, World w, IBlockState state, BlockPos pos, EntityLivingBase e)
    {
        int size = effective_on.length;
        for (int counter = 0; counter < size; counter++)
        {
            theBlock = effective_on[counter];
            if (state.getBlock() == theBlock)
            {
                if (e instanceof PlayerEntity && !w.isRemote)
                {
                    PlayerEntity p = (PlayerEntity) e;
                    RayTraceResult r = this.rayTrace(5.0D, 0.0F, w, e);
                    if (r.typeOfHit == RayTraceResult.Type.BLOCK)
                    {
                        int x = pos.getX();
                        int y = pos.getY();
                        int z = pos.getZ();
                        EnumFacing side = r.sideHit;
                        // Y
                        // UP - DOWN
                        if (side == EnumFacing.DOWN || side == EnumFacing.UP)
                        {
                            this.destroyAndDropBlock(w, p, breaker, x + 1, y, z - 1);
                            this.destroyAndDropBlock(w, p, breaker, x + 1, y, z);
                            this.destroyAndDropBlock(w, p, breaker, x + 1, y, z + 1);
                            this.destroyAndDropBlock(w, p, breaker, x, y, z - 1);
                            // Middle block
                            this.destroyAndDropBlock(w, p, breaker, x, y, z + 1);
                            this.destroyAndDropBlock(w, p, breaker, x - 1, y, z - 1);
                            this.destroyAndDropBlock(w, p, breaker, x - 1, y, z);
                            this.destroyAndDropBlock(w, p, breaker, x - 1, y, z + 1);
                        }
                        // Z
                        // NORTH - SOUTH
                        else if (side == EnumFacing.NORTH || side == EnumFacing.SOUTH)
                        {
                            this.destroyAndDropBlock(w, p, breaker, x + 1, y + 1, z);
                            this.destroyAndDropBlock(w, p, breaker, x, y + 1, z);
                            this.destroyAndDropBlock(w, p, breaker, x - 1, y + 1, z);
                            this.destroyAndDropBlock(w, p, breaker, x + 1, y, z);
                            // Middle block
                            this.destroyAndDropBlock(w, p, breaker, x - 1, y, z);
                            this.destroyAndDropBlock(w, p, breaker, x + 1, y - 1, z);
                            this.destroyAndDropBlock(w, p, breaker, x, y - 1, z);
                            this.destroyAndDropBlock(w, p, breaker, x - 1, y - 1, z);
                        }
                        // X
                        // EAST - WEST
                        else if (side == EnumFacing.EAST || side == EnumFacing.WEST)
                        {
                            this.destroyAndDropBlock(w, p, breaker, x, y + 1, z + 1);
                            this.destroyAndDropBlock(w, p, breaker, x, y + 1, z);
                            this.destroyAndDropBlock(w, p, breaker, x, y + 1, z - 1);
                            this.destroyAndDropBlock(w, p, breaker, x, y, z + 1);
                            // Middle block
                            this.destroyAndDropBlock(w, p, breaker, x, y, z - 1);
                            this.destroyAndDropBlock(w, p, breaker, x, y - 1, z + 1);
                            this.destroyAndDropBlock(w, p, breaker, x, y - 1, z);
                            this.destroyAndDropBlock(w, p, breaker, x, y - 1, z - 1);
                        }
                        return true;
                    }
                }
            }
        }
        return super.onBlockDestroyed(breaker, w, state, pos, e);
    }
 
    private void destroyAndDropBlock(World w, PlayerEntity p, ItemStack breaker, int x, int y, int z)
    {
        int siz = effective_on.length;
        BlockPos pos = new BlockPos(x, y, z);
        for(int IFor = 0; IFor< siz; IFor++)
        {
            theBlock = effective_on[IFor];
            if (w.getBlockState(pos).getBlock() == theBlock)
            {
                w.getBlockState(pos).getBlock().harvestBlock(w, p, pos, w.getBlockState(pos), w.getTileEntity(pos), breaker);
                w.setBlockToAir(pos);
            }
        }
    }*/
}
