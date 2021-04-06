package fr.envium.enviummod.core.items;

import fr.envium.enviummod.api.init.RegisterBlock;
import fr.envium.enviummod.api.init.RegisterItem;
import fr.envium.enviummod.api.tab.EnviumTab;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class EnviumShardRandom extends Item {

    public EnviumShardRandom() {
        super(new Item.Properties().tab(EnviumTab.TAB).stacksTo(1));
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {

        //return super.onItemRightClick(worldIn, playerIn, handIn);
        //return new ItemStack(RegisterItem.envium_lingot);
        /*Random random = new Random();
        int ranomInt = random.nextInt(2);*/
        ItemStack itemStack = null;

        double ranomInt = Math.random() * 100;
        
        if (ranomInt < 96) {
            itemStack = new ItemStack(RegisterItem.envium_shard);
        } else if (ranomInt > 95) {
            itemStack = new ItemStack(RegisterItem.envium_shard_quantity);
        } else {
            itemStack = new ItemStack(RegisterBlock.envium_block, 3);
        }
        //ItemStack inMain = playerIn.getHeldItem(handIn);
        /*if (inMain.getCount() != 1 && inMain.getCount() != 0) {
            playerIn.inventory.addItemStackToInventory(itemStack);
            int countFinal = inMain.getCount() - 2;
            System.out.println(countFinal);
            ItemStack itemStackFinal = new ItemStack(RegisterItem.envium_shard_random, countFinal);
            System.out.println(itemStackFinal.serializeNBT().toString());
            //return ActionResult.resultSuccess(new ItemStack(RegisterItem.envium_lingot, 2));
            //return ActionResult.resultConsume(new ItemStack(RegisterItem.envium_lingot, 2));
        }*/
        return ActionResult.consume(itemStack);
    }
}
