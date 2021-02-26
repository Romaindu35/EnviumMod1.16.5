package fr.envium.enviummod.core.jobs;

import fr.envium.enviummod.api.init.RegisterEntity;
import fr.envium.enviummod.api.init.RegisterBlock;
import fr.envium.enviummod.api.packets.NetworkRegistryHandler;
import fr.envium.enviummod.core.World.SavedData;
import fr.envium.enviummod.core.client.gui.toast.JobsToast;
import fr.envium.enviummod.core.client.gui.toast.JobsToastGui;
import fr.envium.enviummod.core.packets.ReponseClientPacket;
import fr.envium.enviummod.core.server.BDD;
import fr.envium.enviummod.core.server.enums.ActionResponseClient;
import fr.envium.enviummod.core.server.enums.Levels;
import net.mcreator.boss_tools.block.CheeseBlockBlock;
import net.mcreator.boss_tools.block.MarssiliconoreBlock;
import net.mcreator.boss_tools.block.MooncopperOreBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Jobs {

    protected JobsEnum jobsEnum;
    protected Map<Block, List> blockXPMap = new HashMap<>();
    protected Map<EntityType, List> entityXPMap = new HashMap<>();
    protected static Jobs instance;
    protected static BDD bdd;

    public Jobs(JobsEnum jobsEnum) {
        System.out.println("Jobs - Abstract");
        this.jobsEnum = jobsEnum;
        init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    public Map<Block, List> getBlockXPMap() {
        return blockXPMap;
    }
    public Map<EntityType, List> getEntityXPMap() {
        return entityXPMap;
    }
    public void addBlockXP(Block block, Integer XP, Levels min, Levels max, JobsEnum jobsEnum) {
        List list = new ArrayList();
        list.add(XP);
        list.add(min);
        list.add(max);
        list.add(jobsEnum);
        blockXPMap.put(block, list);
    }

    public void addEntityXP(EntityType entity, Integer XP, Levels min, Levels max, JobsEnum jobsEnum) {
        List list = new ArrayList();
        list.add(XP);
        list.add(min);
        list.add(max);
        list.add(jobsEnum);
        entityXPMap.put(entity, list);
    }

    public List getJobsList(Block block) {
        return getBlockXPMap().get(block);
    }

    public Integer getXP(Block block) {
        return (Integer) getJobsList(block).get(0);
    }

    public Levels getLevelsMin(Block block) {
        return (Levels) getJobsList(block).get(1);
    }

    public Levels getLevelsMax(Block block) {
        return (Levels) getJobsList(block).get(2);
    }

    public JobsEnum getJobs(Block block) {
        return (JobsEnum) getJobsList(block).get(3);
    }



    public List getJobsListEntity(EntityType entityType) {
        return getEntityXPMap().get(entityType);
    }

    public Integer getXP(EntityType entityType) {
        return (Integer) getJobsListEntity(entityType).get(0);
    }

    public Levels getLevelsMin(EntityType entityType) {
        return (Levels) getJobsListEntity(entityType).get(1);
    }

    public Levels getLevelsMax(EntityType entityType) {
        return (Levels) getJobsListEntity(entityType).get(2);
    }

    public JobsEnum getJobs(EntityType entityType) {
        return (JobsEnum) getJobsListEntity(entityType).get(3);
    }

    public void init() {
        /* Miner */
        /*addBlockXP(Blocks.STONE, 10, Levels.LVL1, Levels.LVL2, Jobs.MINER);
        addBlockXP(Blocks.SAND, 20, Levels.LVL1, Levels.LVL2, Jobs.MINER);*/

        addBlockXP(Blocks.COAL_ORE, 2, Levels.LVL1, Levels.LVL3, JobsEnum.MINER);
        addBlockXP(Blocks.IRON_ORE, 3, Levels.LVL1, Levels.LVL3, JobsEnum.MINER);
        addBlockXP(Blocks.LAPIS_ORE, 3, Levels.LVL1, Levels.LVL3, JobsEnum.MINER);
        addBlockXP(Blocks.REDSTONE_ORE, 3, Levels.LVL1, Levels.LVL3, JobsEnum.MINER);
        addBlockXP(Blocks.EMERALD_ORE, 10, Levels.LVL1, Levels.LVL3, JobsEnum.MINER);
        addBlockXP(Blocks.DIAMOND_ORE, 8, Levels.LVL1, Levels.LVL3, JobsEnum.MINER);
        addBlockXP(RegisterBlock.envium_ore, 15, Levels.LVL1, Levels.LVL3, JobsEnum.MINER);
        addBlockXP(MooncopperOreBlock.block, 20, Levels.LVL1, Levels.LVL3, JobsEnum.MINER);
        addBlockXP(CheeseBlockBlock.block, 25, Levels.LVL1, Levels.LVL3, JobsEnum.MINER);
        addBlockXP(MarssiliconoreBlock.block, 40, Levels.LVL1, Levels.LVL3, JobsEnum.MINER);

        /* Lumberjack */
        addBlockXP(Blocks.ACACIA_WOOD, 10, Levels.LVL1, Levels.LVL3, JobsEnum.LUMBERJACK);
        addBlockXP(Blocks.ACACIA_PLANKS, 10, Levels.LVL1, Levels.LVL3, JobsEnum.LUMBERJACK);

        /* Chasseur */
        addEntityXP(EntityType.DOLPHIN, 20, Levels.LVL1, Levels.LVL3, JobsEnum.CHASSEUR);
        addEntityXP(RegisterEntity.LIRONDEL_ENTITY.get(), 20, Levels.LVL1, Levels.LVL3, JobsEnum.CHASSEUR);
    }

    public static BDD getInstanceBDD() {
        if (bdd == null) {
            bdd = BDD.getInstance();
        }
        return bdd;
    }
    public static Jobs getInstance() {
        if (instance == null) {
            instance = new Jobs(JobsEnum.NULL);
        }
        return instance;
    }

    @SubscribeEvent
    public void onBlockPickup(BlockEvent.BreakEvent event) {
        BDD bdd = BDD.getInstance();
        if (event.getPlayer() == null || event.getState() == null)
            return;
        if (SavedData.blockPosSet.contains(event.getPos())) {
            SavedData.blockPosSet.remove(event.getPos());
            return;
        }
        if (event.getPlayer().isCreative() || event.getPlayer().isSpectator())
            return;
        if (getBlockXPMap().containsKey(event.getState().getBlock())) {
            Map<JobsEnum, Integer> jobsIntegerMap = new HashMap<>();
            jobsIntegerMap.put(getJobs(event.getState().getBlock()), getXP(event.getState().getBlock()));
            bdd.addJobs(event.getPlayer(), jobsIntegerMap);
            showToast(getXP(event.getState().getBlock()), (ServerPlayerEntity) event.getPlayer());
        }
    }


    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event) {
        BDD bdd = BDD.getInstance();
        if (event.getEntity() == null || event.getSource() == null)
            return;
        if ((event.getSource().getImmediateSource() instanceof PlayerEntity)) {
            PlayerEntity playerEntity = (PlayerEntity) event.getSource().getImmediateSource();
            if (playerEntity.isCreative() || playerEntity.isSpectator())
                return;
            if (getEntityXPMap().containsKey(event.getEntity().getType())) {
                Map<JobsEnum, Integer> jobsIntegerMap = new HashMap<>();
                jobsIntegerMap.put(getJobs(event.getEntity().getType()), getXP(event.getEntity().getType()));
                bdd.addJobs(playerEntity, jobsIntegerMap);
            }
        }
    }

    @SubscribeEvent
    public void playerPlaceBlockEvent(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) event.getEntity();
            if (!playerEntity.isCreative() && !playerEntity.isSpectator()) {
                SavedData.blockPosSet.add(event.getBlockSnapshot().getPos());
            }
        }
    }
    public void showToast(int xp, ServerPlayerEntity playerEntity) {
        NetworkRegistryHandler.network.send(PacketDistributor.PLAYER.with(
                () -> playerEntity),
                new ReponseClientPacket(ActionResponseClient.SHOW_TOAST, String.valueOf(xp)));
    }
}
