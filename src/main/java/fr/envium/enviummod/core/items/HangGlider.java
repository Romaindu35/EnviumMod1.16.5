package fr.envium.enviummod.core.items;

import fr.envium.enviummod.api.tab.EnviumTab;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HangGlider extends Item {

    public HangGlider() {
        super(new Properties().group(EnviumTab.TAB));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        updatePosition(playerIn);

        return ActionResult.resultFail(playerIn.getHeldItem(handIn));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    public static void updatePosition(PlayerEntity player){

        System.out.println(player.collidedVertically);
                if (!player.collidedVertically) { //if falling (flying)
                    System.out.println("x");
                    // Init variables
                    final double horizontalSpeed;
                    final double verticalSpeed;

                    horizontalSpeed = 1;
                    verticalSpeed = 0.0256;

                    double x = Math.cos(Math.toRadians(player.rotationYaw + 90)) * horizontalSpeed;
                    double z = Math.sin(Math.toRadians(player.rotationYaw + 90)) * horizontalSpeed;
                    player.prevPosX += x;
                    player.prevPosZ += z; //ToDo: Wrong, need multiplication to slow down


                    player.prevPosX *= 1.2;
                    player.prevPosZ *= 1.2;

                    player.fallDistance = 0.0F;

                    player.setMotion(x, player.getMotion().y / 2.600000023841858D, z);
                }
                if (player.world.isRemote) {
                    player.limbSwing = 0;
                    player.limbSwingAmount = 0;
                }

    }
}
