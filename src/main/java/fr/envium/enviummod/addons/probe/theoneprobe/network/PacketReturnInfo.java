package fr.envium.enviummod.addons.probe.theoneprobe.network;

import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.ProbeInfo;
import fr.envium.enviummod.addons.probe.theoneprobe.rendering.OverlayRenderer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketReturnInfo {

    private DimensionType dim;
    private BlockPos pos;
    private ProbeInfo probeInfo;

    public PacketReturnInfo(PacketBuffer buf) {
        dim = DimensionType.byName(buf.readResourceLocation());
        pos = buf.readBlockPos();
        if (buf.readBoolean()) {
            probeInfo = new ProbeInfo();
            probeInfo.fromBytes(buf);
        } else {
            probeInfo = null;
        }
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeResourceLocation(dim.getRegistryName());
        buf.writeBlockPos(pos);
        if (probeInfo != null) {
            buf.writeBoolean(true);
            probeInfo.toBytes(buf);
        } else {
            buf.writeBoolean(false);
        }
    }

    public PacketReturnInfo() {
    }

    public PacketReturnInfo(DimensionType dim, BlockPos pos, ProbeInfo probeInfo) {
        this.dim = dim;
        this.pos = pos;
        this.probeInfo = probeInfo;
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            OverlayRenderer.registerProbeInfo(dim, pos, probeInfo);
        });
        ctx.get().setPacketHandled(true);
    }
}
