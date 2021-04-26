package fr.envium.enviummod.addons.probe.theoneprobe.apiimpl;

import fr.envium.enviummod.addons.probe.theoneprobe.TheOneProbe;
import fr.envium.enviummod.addons.probe.theoneprobe.api.*;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.elements.ElementVertical;
import net.minecraft.network.PacketBuffer;

import java.util.ArrayList;
import java.util.List;

public class ProbeInfo extends ElementVertical {

    public List<IElement> getElements() {
        return children;
    }

    public void fromBytes(PacketBuffer buf) {
        children = createElements(buf);
    }

    public ProbeInfo() {
        super(null, 2, ElementAlignment.ALIGN_TOPLEFT);
    }

    public static List<IElement> createElements(PacketBuffer buf) {
        int size = buf.readVarInt();
        List<IElement> elements = new ArrayList<>(size);
        for (int i = 0 ; i < size ; i++) {
            int id = buf.readVarInt();
            IElementFactory factory = TheOneProbe.theOneProbeImp.getElementFactory(id);
            elements.add(factory.createElement(buf));
        }
        return elements;
    }

    public static void writeElements(List<IElement> elements, PacketBuffer buf) {
        buf.writeVarInt(elements.size());
        for (IElement element : elements) {
            buf.writeVarInt(element.getID());
            element.toBytes(buf);
        }
    }

    public void removeElement(IElement element) {
        this.getElements().remove(element);
    }
}
