package fr.envium.enviummod.addons.probe.theoneprobe.playerdata;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PropertiesDispatcher implements ICapabilityProvider, INBTSerializable<CompoundNBT> {

    private PlayerGotNote playerGotNote = new PlayerGotNote();

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == PlayerProperties.PLAYER_GOT_NOTE) {
            return LazyOptional.of(() -> (T) playerGotNote);
        }
        return LazyOptional.empty();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        playerGotNote.saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        playerGotNote.loadNBTData(nbt);
    }
}
