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

import net.minecraft.item.Item.Properties;

public class HangGlider extends Item {

    public HangGlider() {
        super(new Properties().tab(EnviumTab.TAB));
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {

        updatePosition(playerIn);

        return ActionResult.fail(playerIn.getItemInHand(handIn));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    public static void updatePosition(PlayerEntity player){

        System.out.println(player.verticalCollision);
                if (!player.verticalCollision) { //if falling (flying)
                    System.out.println("x");
                    // Init variables
                    final double horizontalSpeed;
                    final double verticalSpeed;

                    horizontalSpeed = 1;
                    verticalSpeed = 0.0256;

                    double x = Math.cos(Math.toRadians(player.yRot + 90)) * horizontalSpeed;
                    double z = Math.sin(Math.toRadians(player.yRot + 90)) * horizontalSpeed;
                    player.xo += x;
                    player.zo += z; //ToDo: Wrong, need multiplication to slow down


                    player.xo *= 1.2;
                    player.zo *= 1.2;

                    player.fallDistance = 0.0F;

                    player.setDeltaMovement(x, player.getDeltaMovement().y / 2.600000023841858D, z);
                }
                if (player.level.isClientSide) {
                    player.animationPosition = 0;
                    player.animationSpeed = 0;
                }

    }
}
