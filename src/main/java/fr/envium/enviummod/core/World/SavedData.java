package fr.envium.enviummod.core.World;

import fr.envium.enviummod.References;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

import java.util.*;
import java.util.function.Supplier;

public class SavedData extends WorldSavedData implements Supplier {

    public CompoundNBT data = new CompoundNBT();
    public static Map<String, BlockPos> posMap = new HashMap<>();
    public List<Integer> pos = new ArrayList<>();
    public static Set<BlockPos> blockPosSet = new HashSet<>();
    public int x = 100;
    public int y = 6200;
    public int z = 3515;

    public SavedData()
    {
        super(References.MODID);
        posMap.put("sand", new BlockPos(200,5,3654));
        posMap.put("stone", new BlockPos(200,5,3654));
    }

    public SavedData(String name)
    {
        super(name);
    }

    @Override
    public void load(CompoundNBT nbt)
    {
        int size = nbt.getInt("size");
        Set<BlockPos> set = new HashSet<>(size);
        int index = 0;
        for (int i = size; i > 0; i--)
        {

            BlockPos blockPos = new BlockPos(nbt.getInt("x_"+index), nbt.getInt("y_"+index), nbt.getInt("z_"+index));
            set.add(blockPos);
            index++;
        }
        System.out.println("SavedData - Read - OK");
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt)
    {
        int index =0;
        nbt.putInt("size", blockPosSet.size());
        for (Iterator<BlockPos> it = blockPosSet.iterator(); it.hasNext(); ) {
            BlockPos x = it.next();
            nbt.putInt("x_"+index, x.getX());
            nbt.putInt("y_"+index, x.getY());
            nbt.putInt("z_"+index, x.getZ());
            index++;
        }
        System.out.println("SavedData - Write - OK");
        return nbt;
    }

    public static SavedData forWorld(ServerWorld world)
    {
        DimensionSavedDataManager storage = world.getDataStorage();
        Supplier<SavedData> sup = new SavedData();
        SavedData saver = storage.computeIfAbsent(sup, References.MODID);

        if (saver == null)
        {
            saver = new SavedData();
            storage.set(saver);
        }
        return saver;
    }

    @Override
    public Object get()
    {
        return this;
    }

}
