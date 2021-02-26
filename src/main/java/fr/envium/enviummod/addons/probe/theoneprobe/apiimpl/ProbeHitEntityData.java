package fr.envium.enviummod.addons.probe.theoneprobe.apiimpl;

import fr.envium.enviummod.addons.probe.theoneprobe.api.IProbeHitEntityData;
import net.minecraft.util.math.vector.Vector3d;

public class ProbeHitEntityData implements IProbeHitEntityData {

    private final Vector3d hitVec;

    public ProbeHitEntityData(Vector3d hitVec) {
        this.hitVec = hitVec;
    }

    @Override
    public Vector3d getHitVec() {
        return hitVec;
    }
}
