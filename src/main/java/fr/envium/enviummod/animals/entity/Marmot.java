package fr.envium.enviummod.animals.entity;

import fr.envium.enviummod.api.init.RegisterEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public class Marmot extends AnimalEntity {
    private static final DataParameter<Boolean> SADDLED = EntityDataManager.defineId(Marmot.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> BOOST_TIME = EntityDataManager.defineId(Marmot.class, DataSerializers.INT);
    private static final DataParameter<Boolean> STAND = EntityDataManager.defineId(Marmot.class, DataSerializers.BOOLEAN);
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.of(Items.CARROT, Items.POTATO, Items.BEETROOT);
    private boolean boosting;
    private int boostTime;
    private int totalBoostTime;

    public Marmot(EntityType<? extends Marmot> p_i50250_1_, World p_i50250_2_) {
        super(p_i50250_1_, p_i50250_2_);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(Items.CARROT_ON_A_STICK), false));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, (double)0.25F);
    }

    /**
     * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
     * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
     */
    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    /**
     * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
     * by a player and the player is holding a carrot-on-a-stick
     */
    public boolean canBeControlledByRider() {
        Entity entity = this.getControllingPassenger();
        if (!(entity instanceof PlayerEntity)) {
            return false;
        } else {
            PlayerEntity playerentity = (PlayerEntity)entity;
            return playerentity.getMainHandItem().getItem() == Items.CARROT_ON_A_STICK || playerentity.getOffhandItem().getItem() == Items.CARROT_ON_A_STICK;
        }
    }

    public void onSyncedDataUpdated(DataParameter<?> key) {
        if (BOOST_TIME.equals(key) && this.level.isClientSide) {
            this.boosting = true;
            this.boostTime = 0;
            this.totalBoostTime = this.entityData.get(BOOST_TIME);
        }

        super.onSyncedDataUpdated(key);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SADDLED, false);
        this.entityData.define(BOOST_TIME, 0);
        this.entityData.define(STAND, false);
    }

    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Saddle", this.getSaddled());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setSaddled(compound.getBoolean("Saddle"));
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.PIG_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.PIG_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.PIG_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.PIG_STEP, 0.15F, 1.0F);
    }

    protected void dropEquipment() {
        super.dropEquipment();
        if (this.getSaddled()) {
            this.spawnAtLocation(Items.SADDLE);
        }

    }

    /**
     * Returns true if the pig is saddled.
     */
    public boolean getSaddled() {
        return this.entityData.get(SADDLED);
    }

    /**
     * Set or remove the saddle of the pig.
     */
    public void setSaddled(boolean saddled) {
        if (saddled) {
            this.entityData.set(SADDLED, true);
        } else {
            this.entityData.set(SADDLED, false);
        }

    }

    public void setStand(boolean stand) {
        if (stand) {
            this.entityData.set(STAND, true);
        } else {
            this.entityData.set(STAND, false);
        }

    }

    public void travel(Vector3d p_213352_1_) {
        if (this.isAlive()) {
            Entity entity = this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
            if (this.isVehicle() && this.canBeControlledByRider()) {
                this.yRot = entity.yRot;
                this.yRotO = this.yRot;
                this.xRot = entity.xRot * 0.5F;
                this.setRot(this.yRot, this.xRot);
                this.yBodyRot = this.yRot;
                this.yHeadRot = this.yRot;
                this.maxUpStep = 1.0F;
                this.flyingSpeed = this.getSpeed() * 0.1F;
                if (this.boosting && this.boostTime++ > this.totalBoostTime) {
                    this.boosting = false;
                }

                if (this.isControlledByLocalInstance()) {
                    float f = (float)this.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * 0.225F;
                    if (this.boosting) {
                        f += f * 1.15F * MathHelper.sin((float)this.boostTime / (float)this.totalBoostTime * (float)Math.PI);
                    }

                    this.setSpeed(f);
                    super.travel(new Vector3d(0.0D, 0.0D, 1.0D));
                    this.lerpSteps = 0;
                } else {
                    this.setDeltaMovement(Vector3d.ZERO);
                }

                this.animationSpeedOld = this.animationSpeed;
                double d1 = this.getX() - this.xo;
                double d0 = this.getZ() - this.zo;
                float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
                if (f1 > 1.0F) {
                    f1 = 1.0F;
                }

                this.animationSpeed += (f1 - this.animationSpeed) * 0.4F;
                this.animationPosition += this.animationSpeed;
            } else {
                this.maxUpStep = 0.5F;
                this.flyingSpeed = 0.02F;
                super.travel(p_213352_1_);
            }
        }
    }

    public boolean boost() {
        if (this.boosting) {
            return false;
        } else {
            this.boosting = true;
            this.boostTime = 0;
            this.totalBoostTime = this.getRandom().nextInt(841) + 140;
            this.getEntityData().set(BOOST_TIME, this.totalBoostTime);
            return true;
        }
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity mate) {
        Marmot marmot = new Marmot(RegisterEntity.MARMOT_ENTITY.get(), world);
        marmot.finalizeSpawn(world, world.getCurrentDifficultyAt(marmot.blockPosition()), SpawnReason.BREEDING, null, null);

        return marmot;
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isFood(ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }
}
