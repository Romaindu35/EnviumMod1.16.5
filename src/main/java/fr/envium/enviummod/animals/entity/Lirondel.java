package fr.envium.enviummod.animals.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import fr.envium.enviummod.api.init.RegisterEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.ShoulderRidingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

public class Lirondel extends ShoulderRidingEntity implements IFlyingAnimal {

    private static final DataParameter<Integer> VARIANT = EntityDataManager.defineId(Lirondel.class, DataSerializers.INT);
    private static final Predicate<MobEntity> CAN_MIMIC = new Predicate<MobEntity>() {
        public boolean test(@Nullable MobEntity p_test_1_) {
            return p_test_1_ != null && Lirondel.IMITATION_SOUND_EVENTS.containsKey(p_test_1_.getType());
        }
    };
    private static final Item DEADLY_ITEM = Items.COOKIE;
    private static final Set<Item> TAME_ITEMS = Sets.newHashSet(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);
    private static final Map<EntityType<?>, SoundEvent> IMITATION_SOUND_EVENTS = Util.make(Maps.newHashMap(), (p_200609_0_) -> {
        p_200609_0_.put(EntityType.BLAZE, SoundEvents.PARROT_IMITATE_BLAZE);
        p_200609_0_.put(EntityType.CAVE_SPIDER, SoundEvents.PARROT_IMITATE_SPIDER);
        p_200609_0_.put(EntityType.CREEPER, SoundEvents.PARROT_IMITATE_CREEPER);
        p_200609_0_.put(EntityType.DROWNED, SoundEvents.PARROT_IMITATE_DROWNED);
        p_200609_0_.put(EntityType.ELDER_GUARDIAN, SoundEvents.PARROT_IMITATE_ELDER_GUARDIAN);
        p_200609_0_.put(EntityType.ENDER_DRAGON, SoundEvents.PARROT_IMITATE_ENDER_DRAGON);
        p_200609_0_.put(EntityType.ENDERMITE, SoundEvents.PARROT_IMITATE_ENDERMITE);
        p_200609_0_.put(EntityType.EVOKER, SoundEvents.PARROT_IMITATE_EVOKER);
        p_200609_0_.put(EntityType.GHAST, SoundEvents.PARROT_IMITATE_GHAST);
        p_200609_0_.put(EntityType.GUARDIAN, SoundEvents.PARROT_IMITATE_GUARDIAN);
        p_200609_0_.put(EntityType.HUSK, SoundEvents.PARROT_IMITATE_HUSK);
        p_200609_0_.put(EntityType.ILLUSIONER, SoundEvents.PARROT_IMITATE_ILLUSIONER);
        p_200609_0_.put(EntityType.MAGMA_CUBE, SoundEvents.PARROT_IMITATE_MAGMA_CUBE);
        p_200609_0_.put(EntityType.PHANTOM, SoundEvents.PARROT_IMITATE_PHANTOM);
        p_200609_0_.put(EntityType.PILLAGER, SoundEvents.PARROT_IMITATE_PILLAGER);
        p_200609_0_.put(EntityType.RAVAGER, SoundEvents.PARROT_IMITATE_RAVAGER);
        p_200609_0_.put(EntityType.SHULKER, SoundEvents.PARROT_IMITATE_SHULKER);
        p_200609_0_.put(EntityType.SILVERFISH, SoundEvents.PARROT_IMITATE_SILVERFISH);
        p_200609_0_.put(EntityType.SKELETON, SoundEvents.PARROT_IMITATE_SKELETON);
        p_200609_0_.put(EntityType.SLIME, SoundEvents.PARROT_IMITATE_SLIME);
        p_200609_0_.put(EntityType.SPIDER, SoundEvents.PARROT_IMITATE_SPIDER);
        p_200609_0_.put(EntityType.STRAY, SoundEvents.PARROT_IMITATE_STRAY);
        p_200609_0_.put(EntityType.VEX, SoundEvents.PARROT_IMITATE_VEX);
        p_200609_0_.put(EntityType.VINDICATOR, SoundEvents.PARROT_IMITATE_VINDICATOR);
        p_200609_0_.put(EntityType.WITCH, SoundEvents.PARROT_IMITATE_WITCH);
        p_200609_0_.put(EntityType.WITHER, SoundEvents.PARROT_IMITATE_WITHER);
        p_200609_0_.put(EntityType.WITHER_SKELETON, SoundEvents.PARROT_IMITATE_WITHER_SKELETON);
        p_200609_0_.put(EntityType.ZOMBIE, SoundEvents.PARROT_IMITATE_ZOMBIE);
        p_200609_0_.put(EntityType.ZOMBIE_VILLAGER, SoundEvents.PARROT_IMITATE_ZOMBIE_VILLAGER);
    });
    public float flap;
    public float flapSpeed;
    public float oFlapSpeed;
    public float oFlap;
    private float flapping = 1.0F;
    private boolean partyParrot;
    private BlockPos jukeboxPosition;

    public Lirondel(EntityType<? extends ShoulderRidingEntity> type, World worldIn) {
        super(type, worldIn);
        this.moveControl = new FlyingMovementController(this, 10, false);
        this.setPathfindingMalus(PathNodeType.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(PathNodeType.DAMAGE_FIRE, -1.0F);
        this.setPathfindingMalus(PathNodeType.COCOA, -1.0F);
    }

    @Nullable
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setVariant(this.random.nextInt(5));
        if (spawnDataIn == null) {
            spawnDataIn = new AgeableEntity.AgeableData(false);
        }

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(2, new SitGoal(this));
        this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.0D, 5.0F, 1.0F, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new LandOnOwnersShoulderGoal(this));
        this.goalSelector.addGoal(3, new FollowMobGoal(this, 1.0D, 3.0F, 7.0F));
    }


    /**
     * Returns new PathNavigateGround instance
     */
    protected PathNavigator createNavigation(World worldIn) {
        FlyingPathNavigator flyingpathnavigator = new FlyingPathNavigator(this, worldIn);
        flyingpathnavigator.setCanOpenDoors(false);
        flyingpathnavigator.setCanFloat(true);
        flyingpathnavigator.setCanPassDoors(true);
        return flyingpathnavigator;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return sizeIn.height * 0.6F;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void aiStep() {
        playMimicSound(this.level, this);
        if (this.jukeboxPosition == null || !this.jukeboxPosition.closerThan(this.position(), 3.46D) || this.level.getBlockState(this.jukeboxPosition).getBlock() != Blocks.JUKEBOX) {
            this.partyParrot = false;
            this.jukeboxPosition = null;
        }

        super.aiStep();
        this.calculateFlapping();
    }

    /**
     * Called when a record starts or stops playing. Used to make parrots start or stop partying.
     */
    @OnlyIn(Dist.CLIENT)
    public void setRecordPlayingNearby(BlockPos pos, boolean isPartying) {
        this.jukeboxPosition = pos;
        this.partyParrot = isPartying;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isPartying() {
        return this.partyParrot;
    }

    private void calculateFlapping() {
        this.oFlap = this.flap;
        this.oFlapSpeed = this.flapSpeed;
        this.flapSpeed = (float)((double)this.flapSpeed + (double)(!this.onGround && !this.isPassenger() ? 4 : -1) * 0.3D);
        this.flapSpeed = MathHelper.clamp(this.flapSpeed, 0.0F, 1.0F);
        if (!this.onGround && this.flapping < 1.0F) {
            this.flapping = 1.0F;
        }

        this.flapping = (float)((double)this.flapping * 0.9D);
        Vector3d vec3d = this.getDeltaMovement();
        if (!this.onGround && vec3d.y < 0.0D) {
            this.setDeltaMovement(vec3d.multiply(1.0D, 0.6D, 1.0D));
        }

        this.flap += this.flapping * 2.0F;
    }

    private static boolean playMimicSound(World worldIn, Entity parrotIn) {
        if (parrotIn.isAlive() && !parrotIn.isSilent() && worldIn.random.nextInt(50) == 0) {
            List<MobEntity> list = worldIn.getEntitiesOfClass(MobEntity.class, parrotIn.getBoundingBox().inflate(20.0D), CAN_MIMIC);
            if (!list.isEmpty()) {
                MobEntity mobentity = list.get(worldIn.random.nextInt(list.size()));
                if (!mobentity.isSilent()) {
                    SoundEvent soundevent = getMimicSound(mobentity.getType());
                    worldIn.playSound((PlayerEntity)null, parrotIn.getX(), parrotIn.getY(), parrotIn.getZ(), soundevent, parrotIn.getSoundSource(), 0.7F, getPitch(worldIn.random));
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isFood(ItemStack stack) {
        return false;
    }

    public static boolean checkParrotSpawnRules(EntityType<ParrotEntity> parrotIn, IWorld worldIn, SpawnReason reason, BlockPos p_223317_3_, Random random) {
        BlockState blockstate = worldIn.getBlockState(p_223317_3_.below());
        return (blockstate.is(BlockTags.LEAVES) /*|| blockstate.isIn(Blocks.GRASS_BLOCK)*/ || blockstate.is(BlockTags.LOGS) /*|| blockstate.isIn(Blocks.AIR)*/) && worldIn.getRawBrightness(p_223317_3_, 0) > 8;
    }

    public boolean causeFallDamage(float distance, float damageMultiplier) {
        return false;
    }

    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    public boolean canMate(AnimalEntity otherAnimal) {
        return false;
    }

    public static void playAmbientSound(World worldIn, Entity parrotIn) {
        if (!parrotIn.isSilent() && !playMimicSound(worldIn, parrotIn) && worldIn.random.nextInt(200) == 0) {
            worldIn.playSound((PlayerEntity)null, parrotIn.getX(), parrotIn.getY(), parrotIn.getZ(), getAmbientSound(worldIn.random), parrotIn.getSoundSource(), 1.0F, getPitch(worldIn.random));
        }

    }

    public boolean doHurtTarget(Entity entityIn) {
        return entityIn.hurt(DamageSource.mobAttack(this), 3.0F);
    }

    @Nullable
    public SoundEvent getAmbientSound() {
        return getAmbientSound(this.random);
    }

    private static SoundEvent getAmbientSound(Random random) {
        if (random.nextInt(1000) == 0) {
            List<EntityType<?>> list = Lists.newArrayList(IMITATION_SOUND_EVENTS.keySet());
            return getMimicSound(list.get(random.nextInt(list.size())));
        } else {
            return SoundEvents.PARROT_AMBIENT;
        }
    }

    private static SoundEvent getMimicSound(EntityType<?> type) {
        return IMITATION_SOUND_EVENTS.getOrDefault(type, SoundEvents.PARROT_AMBIENT);
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.PARROT_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.PARROT_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.PARROT_STEP, 0.15F, 1.0F);
    }

    protected float playFlySound(float volume) {
        this.playSound(SoundEvents.PARROT_FLY, 0.15F, 1.0F);
        return volume + this.flapSpeed / 2.0F;
    }

    protected boolean makeFlySound() {
        return true;
    }

    /**
     * Gets the pitch of living sounds in living entities.
     */
    protected float getVoicePitch() {
        return getPitch(this.random);
    }

    private static float getPitch(Random random) {
        return (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F;
    }

    public SoundCategory getSoundSource() {
        return SoundCategory.NEUTRAL;
    }

    /**
     * Returns true if this entity should push and be pushed by other entities when colliding.
     */
    public boolean isPushable() {
        return true;
    }

    protected void doPush(Entity entityIn) {
        if (!(entityIn instanceof PlayerEntity)) {
            super.doPush(entityIn);
        }
    }

    /**
     * Called when the entity is attacked.
     */
    /*public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else {
            if (this.sitGoal != null) {
                this.sitGoal.setSitting(false);
            }

            return super.attackEntityFrom(source, amount);
        }
    }*/

    public int getVariant() {
        return MathHelper.clamp(this.entityData.get(VARIANT), 0, 4);
    }

    public void setVariant(int variantIn) {
        this.entityData.set(VARIANT, variantIn);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity mate) {
        return null;
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
    }

    public void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getVariant());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(compound.getInt("Variant"));
    }

    public boolean isFlying() {
        return !this.onGround;
    }

}
