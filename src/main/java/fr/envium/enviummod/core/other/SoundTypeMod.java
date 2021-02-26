package fr.envium.enviummod.core.other;

import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

//Pour les sons
public class SoundTypeMod {

    public static final SoundType PILLOW = new SoundType(1.0F, 1.0F,
            new SoundEvent(new ResourceLocation("enviummod:sounds/collapse")),
            new SoundEvent(new ResourceLocation("enviummod:sounds/collapse")),
            new SoundEvent(new ResourceLocation("enviummod:sounds/collapse")),
            new SoundEvent(new ResourceLocation("enviummod:sounds/collapse")),
            new SoundEvent(new ResourceLocation("enviummod:sounds/collapse")));
}
