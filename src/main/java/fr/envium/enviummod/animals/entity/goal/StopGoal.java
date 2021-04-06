package fr.envium.enviummod.animals.entity.goal;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;

public class StopGoal extends Goal {

    protected final CreatureEntity creature;

    public StopGoal(CreatureEntity creature) {
        this.creature = creature;
    }

    @Override
    public boolean canUse() {
        return true;
    }

}
