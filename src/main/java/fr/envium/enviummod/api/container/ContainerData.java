package fr.envium.enviummod.api.container;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Map;

public abstract class ContainerData extends Container {

    private Map<String, Integer> clientData = new HashMap<String, Integer>();

    protected ContainerData(ContainerType<?> type, int id) {
        super(type, id);
    }

    @OnlyIn(Dist.CLIENT)
    public Map<String, Integer> getClientData()
    {
        return clientData;
    }

    @OnlyIn(Dist.CLIENT)
    public void setClientData(Map<String, Integer> map)
    {
        this.clientData = map;
    }
}
