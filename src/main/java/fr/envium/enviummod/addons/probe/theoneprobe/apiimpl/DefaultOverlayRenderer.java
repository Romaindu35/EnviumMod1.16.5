package fr.envium.enviummod.addons.probe.theoneprobe.apiimpl;

import fr.envium.enviummod.addons.probe.theoneprobe.TheOneProbe;
import fr.envium.enviummod.addons.probe.theoneprobe.api.IOverlayRenderer;
import fr.envium.enviummod.addons.probe.theoneprobe.api.IOverlayStyle;
import fr.envium.enviummod.addons.probe.theoneprobe.api.IProbeInfo;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.styles.DefaultOverlayStyle;
import fr.envium.enviummod.addons.probe.theoneprobe.config.Config;
import fr.envium.enviummod.addons.probe.theoneprobe.rendering.OverlayRenderer;

public class DefaultOverlayRenderer implements IOverlayRenderer {

    @Override
    public IOverlayStyle createDefaultStyle() {
        return ((DefaultOverlayStyle)Config.getDefaultOverlayStyle()).copy();
    }

    @Override
    public IProbeInfo createProbeInfo() {
        return TheOneProbe.theOneProbeImp.create();
    }

    @Override
    public void render(IOverlayStyle style, IProbeInfo probeInfo) {
        OverlayRenderer.renderOverlay(style, probeInfo);
    }
}
