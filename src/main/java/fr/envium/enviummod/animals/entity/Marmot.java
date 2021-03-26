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
    private static final DataParameter<Boolean> SADDLED = EntityDataManager.createKey(Marmot.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> BOOST_TIME = EntityDataManager.createKey(Marmot.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> STAND = EntityDataManager.createKey(Marmot.class, DataSerializers.BOOLEAN);
    private static final Ingredient TEMPTATION_ITEMS = Ingredient.fromItems(Items.CARROT, Items.POTATO, Items.BEETROOT);
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
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.fromItems(Items.CARROT_ON_A_STICK), false));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, false, TEMPTATION_ITEMS));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    public static AttributeModifierMap.MutableAttribute func_234188_eI_() {
        return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, (double)0.25F);
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
    public boolean canBeSteered() {
        Entity entity = this.getControllingPassenger();
        if (!(entity instanceof PlayerEntity)) {
            return false;
        } else {
            PlayerEntity playerentity = (PlayerEntity)entity;
            return playerentity.getHeldItemMainhand().getItem() == Items.CARROT_ON_A_STICK || playerentity.getHeldItemOffhand().getItem() == Items.CARROT_ON_A_STICK;
        }
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        if (BOOST_TIME.equals(key) && this.world.isRemote) {
            this.boosting = true;
            this.boostTime = 0;
            this.totalBoostTime = this.dataManager.get(BOOST_TIME);
        }

        super.notifyDataManagerChange(key);
    }

    protected void registerData() {
        super.registerData();
        this.dataManager.register(SADDLED, false);
        this.dataManager.register(BOOST_TIME, 0);
        this.dataManager.register(STAND, false);
    }

    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Saddle", this.getSaddled());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setSaddled(compound.getBoolean("Saddle"));
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PIG_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_PIG_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PIG_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_PIG_STEP, 0.15F, 1.0F);
    }

    protected void dropInventory() {
        super.dropInventory();
        if (this.getSaddled()) {
            this.entityDropItem(Items.SADDLE);
        }

    }

    /**
     * Returns true if the pig is saddled.
     */
    public boolean getSaddled() {
        return this.dataManager.get(SADDLED);
    }

    /**
     * Set or remove the saddle of the pig.
     */
    public void setSaddled(boolean saddled) {
        if (saddled) {
            this.dataManager.set(SADDLED, true);
        } else {
            this.dataManager.set(SADDLED, false);
        }

    }

    public void setStand(boolean stand) {
        if (stand) {
            this.dataManager.set(STAND, true);
        } else {
            this.dataManager.set(STAND, false);
        }

    }

    public void travel(Vector3d p_213352_1_) {
        if (this.isAlive()) {
            Entity entity = this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
            if (this.isBeingRidden() && this.canBeSteered()) {
                this.rotationYaw = entity.rotationYaw;
                this.prevRotationYaw = this.rotationYaw;
                this.rotationPitch = entity.rotationPitch * 0.5F;
                this.setRotation(this.rotationYaw, this.rotationPitch);
                this.renderYawOffset = this.rotationYaw;
                this.rotationYawHead = this.rotationYaw;
                this.stepHeight = 1.0F;
                this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;
                if (this.boosting && this.boostTime++ > this.totalBoostTime) {
                    this.boosting = false;
                }

                if (this.canPassengerSteer()) {
                    float f = (float)this.getAttribute(Attributes.MOVEMENT_SPEED).getValue() * 0.225F;
                    if (this.boosting) {
                        f += f * 1.15F * MathHelper.sin((float)this.boostTime / (float)this.totalBoostTime * (float)Math.PI);
                    }

                    this.setAIMoveSpeed(f);
                    super.travel(new Vector3d(0.0D, 0.0D, 1.0D));
                    this.newPosRotationIncrements = 0;
                } else {
                    this.setMotion(Vector3d.ZERO);
                }

                this.prevLimbSwingAmount = this.limbSwingAmount;
                double d1 = this.getPosX() - this.prevPosX;
                double d0 = this.getPosZ() - this.prevPosZ;
                float f1 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
                if (f1 > 1.0F) {
                    f1 = 1.0F;
                }

                this.limbSwingAmount += (f1 - this.limbSwingAmount) * 0.4F;
                this.limbSwing += this.limbSwingAmount;
            } else {
                this.stepHeight = 0.5F;
                this.jumpMovementFactor = 0.02F;
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
            this.totalBoostTime = this.getRNG().nextInt(841) + 140;
            this.getDataManager().set(BOOST_TIME, this.totalBoostTime);
            return true;
        }
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity p_241840_2_) {
        Marmot marmot = new Marmot(RegisterEntity.MARMOT_ENTITY.get(), serverWorld);
        marmot.onInitialSpawn(serverWorld, serverWorld.getDifficultyForLocation(marmot.getPosition()), SpawnReason.BREEDING, null, null);

        return marmot;
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isBreedingItem(ItemStack stack) {
        return TEMPTATION_ITEMS.test(stack);
    }
}
