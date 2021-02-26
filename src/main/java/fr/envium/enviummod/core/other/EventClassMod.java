package fr.envium.enviummod.core.other;

import fr.envium.enviummod.core.blocks.BlockPillow;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class EventClassMod {

    @SubscribeEvent
    public void LivingFallEvent(LivingFallEvent event) {
        BlockPos pos = new BlockPos(event.getEntityLiving().getPosition());

        if(event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            Block block = player.world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock();

            if(block instanceof BlockPillow) {
                event.setCanceled(true);
            }
        }
    }
}
