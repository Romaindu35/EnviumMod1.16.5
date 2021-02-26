package fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.providers;

import fr.envium.enviummod.addons.probe.theoneprobe.TheOneProbe;
import fr.envium.enviummod.addons.probe.theoneprobe.api.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class EntityProbeInfoEntityProvider implements IProbeInfoEntityProvider {

    @Override
    public String getID() {
        return TheOneProbe.MODID + ":entity.entity";
    }

    @Override
    public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, Entity entity, IProbeHitEntityData data) {
        if (entity instanceof IProbeInfoEntityAccessor) {
            IProbeInfoEntityAccessor accessor = (IProbeInfoEntityAccessor) entity;
            accessor.addProbeInfo(mode, probeInfo, player, world, entity, data);
        }
    }
}
