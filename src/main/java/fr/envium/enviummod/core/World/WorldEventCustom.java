package fr.envium.enviummod.core.World;

import fr.envium.enviummod.EnviumMod;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.WorldEvent;

public class WorldEventCustom {

    public static void onWorldLoaded(WorldEvent.Load event)
    {
        if (!event.getWorld().isClientSide() && event.getWorld() instanceof ServerWorld)
        {
            //SavedData saver = SavedData.forWorld((ServerWorld) event.getWorld());
        }
    }

    public static void onWorldSaved(WorldEvent.Save event)
    {
        if (!event.getWorld().isClientSide() && event.getWorld() instanceof ServerWorld)
        {
            SavedData saver = SavedData.forWorld((ServerWorld) event.getWorld());
            saver.setDirty();
            EnviumMod.LOGGER.debug("Put my data in!");
        }
    }

}
