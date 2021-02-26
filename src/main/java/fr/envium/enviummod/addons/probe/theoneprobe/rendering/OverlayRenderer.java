package fr.envium.enviummod.addons.probe.theoneprobe.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import fr.envium.enviummod.addons.probe.theoneprobe.TheOneProbe;
import fr.envium.enviummod.addons.probe.theoneprobe.api.*;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.ProbeHitData;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.ProbeHitEntityData;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.ProbeInfo;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.elements.ElementProgress;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.elements.ElementText;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.providers.DefaultProbeInfoEntityProvider;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.providers.DefaultProbeInfoProvider;
import fr.envium.enviummod.addons.probe.theoneprobe.apiimpl.styles.ProgressStyle;
import fr.envium.enviummod.addons.probe.theoneprobe.config.Config;
import fr.envium.enviummod.addons.probe.theoneprobe.network.PacketGetEntityInfo;
import fr.envium.enviummod.addons.probe.theoneprobe.network.PacketGetInfo;
import fr.envium.enviummod.addons.probe.theoneprobe.network.PacketHandler;
import fr.envium.enviummod.addons.probe.theoneprobe.network.ThrowableIdentity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static fr.envium.enviummod.addons.probe.theoneprobe.api.TextStyleClass.ERROR;

public class OverlayRenderer {

    private static Map<Pair<DimensionType,BlockPos>, Pair<Long, ProbeInfo>> cachedInfo = new HashMap<>();
    private static Map<UUID, Pair<Long, ProbeInfo>> cachedEntityInfo = new HashMap<>();
    private static long lastCleanupTime = 0;

    // For a short while we keep displaying the last pair if we have no new information
    // to prevent flickering
    private static Pair<Long, ProbeInfo> lastPair;
    private static long lastPairTime = 0;

    // When the server delays too long we also show some preliminary information already
    private static long lastRenderedTime = -1;

    public static void registerProbeInfo(DimensionType dim, BlockPos pos, ProbeInfo probeInfo) {
        if (probeInfo == null) {
            return;
        }
        long time = System.currentTimeMillis();
        cachedInfo.put(Pair.of(dim, pos), Pair.of(time, probeInfo));
    }

    public static void registerProbeInfo(UUID uuid, ProbeInfo probeInfo) {
        if (probeInfo == null) {
            return;
        }
        long time = System.currentTimeMillis();
        cachedEntityInfo.put(uuid, Pair.of(time, probeInfo));
    }

    public static void renderHUD(ProbeMode mode, float partialTicks) {


        double dist = Config.probeDistance.get();

        RayTraceResult mouseOver = Minecraft.getInstance().objectMouseOver;
        if (mouseOver != null) {
            if (mouseOver.getType() == RayTraceResult.Type.ENTITY) {
                RenderSystem.pushMatrix();
                MatrixStack matrixStack = new MatrixStack();

                double scale = Config.tooltipScale.get();

                double sw = Minecraft.getInstance().getMainWindow().getScaledWidth();
                double sh = Minecraft.getInstance().getMainWindow().getScaledHeight();

                setupOverlayRendering(matrixStack, sw * scale, sh * scale);
                renderHUDEntity(matrixStack, mode, mouseOver, sw * scale, sh * scale);
                setupOverlayRendering(matrixStack, sw, sh);
                RenderSystem.popMatrix();

                checkCleanup();
                return;
            }
        }

        PlayerEntity entity = Minecraft.getInstance().player;
        Vec3d start  = entity.getEyePosition(partialTicks);
        Vec3d vec31 = entity.getLook(partialTicks);
        Vec3d end = start.add(vec31.x * dist, vec31.y * dist, vec31.z * dist);

        RayTraceContext context = new RayTraceContext(start, end, RayTraceContext.BlockMode.OUTLINE, Config.showLiquids.get() ? RayTraceContext.FluidMode.ANY : RayTraceContext.FluidMode.NONE, entity);
        mouseOver = entity.getEntityWorld().rayTraceBlocks(context);
        if (mouseOver == null) {
            return;
        }

        if (mouseOver.getType() == RayTraceResult.Type.BLOCK) {
            RenderSystem.pushMatrix();
            MatrixStack matrixStack = new MatrixStack();

            double scale = Config.tooltipScale.get();

            double sw = Minecraft.getInstance().getMainWindow().getScaledWidth();
            double sh = Minecraft.getInstance().getMainWindow().getScaledHeight();

            setupOverlayRendering(matrixStack, sw * scale, sh * scale);
            renderHUDBlock(matrixStack, mode, mouseOver, sw * scale, sh * scale);
            setupOverlayRendering(matrixStack, sw, sh);

            RenderSystem.popMatrix();
        }

        checkCleanup();
    }

    private static void setupOverlayRendering(MatrixStack matrixStack, double sw, double sh) {
        RenderSystem.clear(256, true);
        RenderSystem.matrixMode(GL11.GL_PROJECTION);
        RenderSystem.loadIdentity();
        RenderSystem.ortho(0.0D, sw, sh, 0.0D, 1000.0D, 3000.0D);
        RenderSystem.matrixMode(GL11.GL_MODELVIEW);
        RenderSystem.loadIdentity();
        RenderSystem.translatef(0.0F, 0.0F, -2000.0F);
//        matrixStack.translate(0, 0, -2000);
    }

    private static void checkCleanup() {
        long time = System.currentTimeMillis();
        if (time > lastCleanupTime + 5000) {
            cleanupCachedBlocks(time);
            cleanupCachedEntities(time);
            lastCleanupTime = time;
        }
    }

    private static void renderHUDEntity(MatrixStack matrixStack, ProbeMode mode, RayTraceResult mouseOver, double sw, double sh) {
        if (!(mouseOver instanceof EntityRayTraceResult)) {
            return;
        }
        Entity entity = ((EntityRayTraceResult) mouseOver).getEntity();
//@todo
//        if (entity instanceof EntityDragonPart) {
//            EntityDragonPart part = (EntityDragonPart) entity;
//            if (part.entityDragonObj instanceof Entity) {
//                entity = (Entity) part.entityDragonObj;
//            }
//        }

//        String entityString = EntityList.getEntityString(entity);
        String entityString = entity.getEntityString();
        if (entityString == null && !(entity instanceof PlayerEntity)) {
            // We can't show info for this entity
            return;
        }

        UUID uuid = entity.getUniqueID();

        PlayerEntity player = Minecraft.getInstance().player;
        long time = System.currentTimeMillis();

        Pair<Long, ProbeInfo> cacheEntry = cachedEntityInfo.get(uuid);
        if (cacheEntry == null || cacheEntry.getValue() == null) {

            // To make sure we don't ask it too many times before the server got a chance to send the answer
            // we insert a dummy entry here for a while
            if (cacheEntry == null || time >= cacheEntry.getLeft()) {
                cachedEntityInfo.put(uuid, Pair.of(time + 500, null));
                requestEntityInfo(mode, mouseOver, entity, player);
            }

            if (lastPair != null && time < lastPairTime + Config.timeout.get()) {
                renderElements(matrixStack, lastPair.getRight(), Config.getDefaultOverlayStyle(), sw, sh, null);
                lastRenderedTime = time;
            } else if (Config.waitingForServerTimeout.get() > 0 && lastRenderedTime != -1 && time > lastRenderedTime + Config.waitingForServerTimeout.get()) {
                // It has been a while. Show some info on client that we are
                // waiting for server information
                ProbeInfo info = getWaitingEntityInfo(mode, mouseOver, entity, player);
                registerProbeInfo(uuid, info);
                lastPair = Pair.of(time, info);
                lastPairTime = time;
                renderElements(matrixStack, lastPair.getRight(), Config.getDefaultOverlayStyle(), sw, sh, null);
                lastRenderedTime = time;
            }
        } else {
            if (time > cacheEntry.getLeft() + Config.timeout.get()) {
                // This info is slightly old. Update it

                // To make sure we don't ask it too many times before the server got a chance to send the answer
                // we increase the time a bit here
                cachedEntityInfo.put(uuid, Pair.of(time + 500, cacheEntry.getRight()));
                requestEntityInfo(mode, mouseOver, entity, player);
            }
            renderElements(matrixStack, cacheEntry.getRight(), Config.getDefaultOverlayStyle(), sw, sh, null);
            lastRenderedTime = time;
            lastPair = cacheEntry;
            lastPairTime = time;
        }
    }

    private static void requestEntityInfo(ProbeMode mode, RayTraceResult mouseOver, Entity entity, PlayerEntity player) {
        PacketHandler.INSTANCE.sendToServer(new PacketGetEntityInfo(player.getEntityWorld().getDimension().getType(), mode, mouseOver, entity));
    }

    private static void renderHUDBlock(MatrixStack matrixStack, ProbeMode mode, RayTraceResult mouseOver, double sw, double sh) {
        if (!(mouseOver instanceof BlockRayTraceResult)) {
            return;
        }
        BlockPos blockPos = ((BlockRayTraceResult) mouseOver).getPos();
        if (blockPos == null) {
            return;
        }
        PlayerEntity player = Minecraft.getInstance().player;
        if (player.getEntityWorld().isAirBlock(blockPos)) {
            return;
        }

        long time = System.currentTimeMillis();

        IElement damageElement = null;
        if (Config.showBreakProgress.get() > 0) {
            float damage = Minecraft.getInstance().playerController.curBlockDamageMP;
            if (damage > 0) {
                if (Config.showBreakProgress.get() == 2) {
                    damageElement = new ElementText("" + TextFormatting.RED + "Progress " + (int) (damage * 100) + "%");
                } else {
                    damageElement = new ElementProgress((long) (damage * 100), 100, new ProgressStyle()
                            .prefix("Progress ")
                            .suffix("%")
                            .width(85)
                            .borderColor(0)
                            .filledColor(0)
                            .filledColor(0xff990000)
                            .alternateFilledColor(0xff550000));
                }
            }
        }

        DimensionType dimension = player.getEntityWorld().getDimension().getType();
        Pair<DimensionType, BlockPos> key = Pair.of(dimension, blockPos);
        Pair<Long, ProbeInfo> cacheEntry = cachedInfo.get(key);
        if (cacheEntry == null || cacheEntry.getValue() == null) {

            // To make sure we don't ask it too many times before the server got a chance to send the answer
            // we insert a dummy entry here for a while
            if (cacheEntry == null || time >= cacheEntry.getLeft()) {
                cachedInfo.put(key, Pair.of(time + 500, null));
                requestBlockInfo(mode, mouseOver, blockPos, player);
            }

            if (lastPair != null && time < lastPairTime + Config.timeout.get()) {
                renderElements(matrixStack, lastPair.getRight(), Config.getDefaultOverlayStyle(), sw, sh, damageElement);
                lastRenderedTime = time;
            } else if (Config.waitingForServerTimeout.get() > 0 && lastRenderedTime != -1 && time > lastRenderedTime + Config.waitingForServerTimeout.get()) {
                // It has been a while. Show some info on client that we are
                // waiting for server information
                ProbeInfo info = getWaitingInfo(mode, mouseOver, blockPos, player);
                registerProbeInfo(dimension, blockPos, info);
                lastPair = Pair.of(time, info);
                lastPairTime = time;
                renderElements(matrixStack, lastPair.getRight(), Config.getDefaultOverlayStyle(), sw, sh, damageElement);
                lastRenderedTime = time;
            }
        } else {
            if (time > cacheEntry.getLeft() + Config.timeout.get()) {
                // This info is slightly old. Update it

                // To make sure we don't ask it too many times before the server got a chance to send the answer
                // we increase the time a bit here
                cachedInfo.put(key, Pair.of(time + 500, cacheEntry.getRight()));
                requestBlockInfo(mode, mouseOver, blockPos, player);
            }
            renderElements(matrixStack, cacheEntry.getRight(), Config.getDefaultOverlayStyle(), sw, sh, damageElement);
            lastRenderedTime = time;
            lastPair = cacheEntry;
            lastPairTime = time;
        }
    }

    // Information for when the server is laggy
    private static ProbeInfo getWaitingInfo(ProbeMode mode, RayTraceResult mouseOver, BlockPos blockPos, PlayerEntity player) {
        ProbeInfo probeInfo = TheOneProbe.theOneProbeImp.create();

        World world = player.getEntityWorld();
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        ItemStack pickBlock = block.getPickBlock(blockState, mouseOver, world, blockPos, player);
        IProbeHitData data = new ProbeHitData(blockPos, mouseOver.getHitVec(), ((BlockRayTraceResult)mouseOver).getFace(), pickBlock);

        IProbeConfig probeConfig = TheOneProbe.theOneProbeImp.createProbeConfig();
        try {
            DefaultProbeInfoProvider.showStandardBlockInfo(probeConfig, mode, probeInfo, blockState, block, world, blockPos, player, data);
        } catch (Exception e) {
            ThrowableIdentity.registerThrowable(e);
            probeInfo.text(CompoundText.create().style(ERROR).text("Error (see log for details)!"));
        }

        probeInfo.text(CompoundText.create().style(ERROR).text("Waiting for server..."));
        return probeInfo;
    }

    private static ProbeInfo getWaitingEntityInfo(ProbeMode mode, RayTraceResult mouseOver, Entity entity, PlayerEntity player) {
        ProbeInfo probeInfo = TheOneProbe.theOneProbeImp.create();
        IProbeHitEntityData data = new ProbeHitEntityData(mouseOver.getHitVec());

        IProbeConfig probeConfig = TheOneProbe.theOneProbeImp.createProbeConfig();
        try {
            DefaultProbeInfoEntityProvider.showStandardInfo(mode, probeInfo, entity, probeConfig);
        } catch (Exception e) {
            ThrowableIdentity.registerThrowable(e);
            probeInfo.text(CompoundText.create().style(ERROR).text("Error (see log for details)!"));
        }

        probeInfo.text(CompoundText.create().style(ERROR).text("Waiting for server..."));
        return probeInfo;
    }

    private static void requestBlockInfo(ProbeMode mode, RayTraceResult mouseOver, BlockPos blockPos, PlayerEntity player) {
        World world = player.getEntityWorld();
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        ItemStack pickBlock = block.getPickBlock(blockState, mouseOver, world, blockPos, player);
        if (pickBlock == null) {
            // Should not be needed but you never know... (bad mods)
            pickBlock = ItemStack.EMPTY;
        }
        if (!pickBlock.isEmpty() && Config.getDontSendNBTSet().contains(pickBlock.getItem().getRegistryName())) {
            pickBlock = pickBlock.copy();
            pickBlock.setTag(null);
        }
        PacketHandler.INSTANCE.sendToServer(new PacketGetInfo(world.getDimension().getType(), blockPos, mode, mouseOver, pickBlock));
    }

    public static void renderOverlay(IOverlayStyle style, IProbeInfo probeInfo) {
        RenderSystem.pushMatrix();
        MatrixStack matrixStack = new MatrixStack();

        double scale = Config.tooltipScale.get();

        double sw = Minecraft.getInstance().getMainWindow().getScaledWidth();
        double sh = Minecraft.getInstance().getMainWindow().getScaledHeight();

        setupOverlayRendering(matrixStack, sw * scale, sh * scale);
        renderElements(matrixStack, (ProbeInfo) probeInfo, style, sw * scale, sh * scale, null);
        setupOverlayRendering(matrixStack, sw, sh);
        RenderSystem.popMatrix();
    }

    private static void cleanupCachedBlocks(long time) {
        // It has been a while. Time to clean up unused cached pairs.
        Map<Pair<DimensionType,BlockPos>, Pair<Long, ProbeInfo>> newCachedInfo = new HashMap<>();
        for (Map.Entry<Pair<DimensionType, BlockPos>, Pair<Long, ProbeInfo>> entry : cachedInfo.entrySet()) {
            long t = entry.getValue().getLeft();
            if (time < t + Config.timeout.get() + 1000) {
                newCachedInfo.put(entry.getKey(), entry.getValue());
            }
        }
        cachedInfo = newCachedInfo;
    }

    private static void cleanupCachedEntities(long time) {
        // It has been a while. Time to clean up unused cached pairs.
        Map<UUID, Pair<Long, ProbeInfo>> newCachedInfo = new HashMap<>();
        for (Map.Entry<UUID, Pair<Long, ProbeInfo>> entry : cachedEntityInfo.entrySet()) {
            long t = entry.getValue().getLeft();
            if (time < t + Config.timeout.get() + 1000) {
                newCachedInfo.put(entry.getKey(), entry.getValue());
            }
        }
        cachedEntityInfo = newCachedInfo;
    }

    public static void renderElements(MatrixStack matrixStack, ProbeInfo probeInfo, IOverlayStyle style, double sw, double sh,
                                      @Nullable IElement extra) {
        if (extra != null) {
            probeInfo.element(extra);
        }

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableLighting();

//        final ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getInstance());
//        final int scaledWidth = scaledresolution.getScaledWidth();
//        final int scaledHeight = scaledresolution.getScaledHeight();
        int scaledWidth = (int) sw;
        int scaledHeight = (int) sh;

        int w = probeInfo.getWidth();
        int h = probeInfo.getHeight();

        int offset = style.getBorderOffset();
        int thick = style.getBorderThickness();
        int margin = 0;
        if (thick > 0) {
            w += (offset + thick + 3) * 2;
            h += (offset + thick + 3) * 2;
            margin = offset + thick + 3;
        }

        int x;
        int y;
        if (style.getLeftX() != -1) {
            x = style.getLeftX();
        } else if (style.getRightX() != -1) {
            x = scaledWidth - w - style.getRightX();
        } else {
            x = (scaledWidth - w) / 2;
        }
        if (style.getTopY() != -1) {
            y = style.getTopY();
        } else if (style.getBottomY() != -1) {
            y = scaledHeight - h - style.getBottomY();
        } else {
            y = (scaledHeight - h) / 2;
        }

        if (thick > 0) {
            if (offset > 0) {
                RenderHelper.drawThickBeveledBox(x, y, x + w-1, y + h-1, thick, style.getBoxColor(), style.getBoxColor(), style.getBoxColor());
            }
            RenderHelper.drawThickBeveledBox(x+offset, y+offset, x + w-1-offset, y + h-1-offset, thick, style.getBorderColor(), style.getBorderColor(), style.getBoxColor());
        }

        if (!Minecraft.getInstance().isGamePaused()) {
            RenderHelper.rot += .5f;
        }

        probeInfo.render(x + margin, y + margin);
        if (extra != null) {
            probeInfo.removeElement(extra);
        }
    }
}
