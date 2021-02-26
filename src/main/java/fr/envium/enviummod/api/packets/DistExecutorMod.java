package fr.envium.enviummod.api.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

public class DistExecutorMod {

    public static PlayerEntity getPlayerEntity() {
        return Minecraft.getInstance().player;
    }
}
