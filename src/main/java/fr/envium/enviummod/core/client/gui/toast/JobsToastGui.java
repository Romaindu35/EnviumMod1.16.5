package fr.envium.enviummod.core.client.gui.toast;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.ToastGui;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Deque;

@OnlyIn(Dist.CLIENT)
public class JobsToastGui extends ToastGui {
    private final Minecraft mc;
    private final ToastInstance<?>[] visible = new ToastInstance[3];
    private final Deque<IToast> toastsQueue = Queues.newArrayDeque();

    public JobsToastGui() {
        super(Minecraft.getInstance());
        this.mc = Minecraft.getInstance();
    }

    public void render(MatrixStack matrixStack) {
        if (!this.mc.options.hideGui) {
            for(int i = 0; i < this.visible.length; ++i) {
                ToastInstance<?> toastinstance = this.visible[i];
                if (toastinstance != null && toastinstance.getToast() instanceof JobsToast) {
                    JobsToast jobsToast = (JobsToast) toastinstance.getToast();
                    if (jobsToast.getReload()) {
                        if (toastinstance != null && toastinstance.render(matrixStack, this.mc.getWindow().getGuiScaledWidth(), i)) {
                            this.visible[i] = null;
                        }
                        return;
                    }
                }
                if (toastinstance != null && toastinstance.renderWithAnimation(matrixStack, this.mc.getWindow().getGuiScaledWidth(), i)) {
                    this.visible[i] = null;
                }

                if (this.visible[i] == null && !this.toastsQueue.isEmpty()) {
                    this.visible[i] = new ToastInstance(this.toastsQueue.removeFirst());
                }
            }

        }
    }

    @Nullable
    public <T extends IToast> T getToast(Class<? extends T> p_192990_1_, Object p_192990_2_) {
        for(ToastInstance<?> toastinstance : this.visible) {
            if (toastinstance != null && p_192990_1_.isAssignableFrom(toastinstance.getToast().getClass()) && toastinstance.getToast().getToken().equals(p_192990_2_)) {
                return (T)toastinstance.getToast();
            }
        }

        for(IToast itoast : this.toastsQueue) {
            if (p_192990_1_.isAssignableFrom(itoast.getClass()) && itoast.getToken().equals(p_192990_2_)) {
                return (T)itoast;
            }
        }

        return (T)null;
    }

    @Nullable
    public <T extends IToast> T getToastByName(Class<? extends T> toast) {
        for(ToastInstance<?> toastinstance : this.visible) {
            if(toastinstance == null)
                return null;
            if (toastinstance != null && toastinstance.getToast().getClass().getName().equals(toast.getName())) {
                return (T)toastinstance.getToast();
            }
        }

        for(IToast itoast : this.toastsQueue) {
            if (itoast.getClass().getName().equals(toast.getName())) {
                return (T) itoast;
            }
        }

        return (T)null;
    }

    public void clear() {
        Arrays.fill(this.visible, (Object)null);
        this.toastsQueue.clear();
    }

    public void addToast(IToast toastIn) {
        this.toastsQueue.add(toastIn);
    }

    public Minecraft getMinecraft() {
        return this.mc;
    }

    public void updateJobsToast(JobsToast toast) {
        if (getToastByName(toast.getClass()) != null) {
            if (isVisible(toast)) {
                JobsToast jobsToast = getToastByName(toast.getClass());
                for(int i = 0; i < this.visible.length; ++i) {
                    ToastInstance<?> toastinstance = this.visible[i];
                    if (toastinstance != null && toastinstance.getToast().getClass().getName().equals(toast.getClass().getName())) {
                        toast.setReload(true);
                        toast.setXp(toast.getXp() + jobsToast.getXp());
                        this.visible[i] = new ToastInstance(toast);
                    }
                }
            }
        } else {
            toast.setReload(false);
            addToast(toast);
        }
    }

    public boolean isVisible(JobsToast toast) {
        for(ToastInstance<?> toastinstance : this.visible) {
            if (toastinstance != null && toast.getClass().getName().equals(toast.getClass().getName())) {
                return true;
            }
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    class ToastInstance<T extends IToast> {
        private final T toast;
        private long animationTime = -1L;
        private long visibleTime = -1L;
        private IToast.Visibility visibility = IToast.Visibility.SHOW;

        private ToastInstance(T toastIn) {
            this.toast = toastIn;
        }

        public T getToast() {
            return this.toast;
        }

        private float getVisibility(long p_193686_1_) {
            float f = MathHelper.clamp((float)(p_193686_1_ - this.animationTime) / 600.0F, 0.0F, 1.0F);
            f = f * f;
            return this.visibility == IToast.Visibility.HIDE ? 1.0F - f : f;
        }

        public boolean renderWithAnimation(MatrixStack matrixStack, int width, int height) {
            long i = Util.getMillis();
            if (this.animationTime == -1L) {
                this.animationTime = i;
                this.visibility.playSound(JobsToastGui.this.mc.getSoundManager());
            }

            if (this.visibility == IToast.Visibility.SHOW && i - this.animationTime <= 600L) {
                this.visibleTime = i;
            }

            RenderSystem.pushMatrix();
            RenderSystem.translatef((float)width - 160.0F * this.getVisibility(i), (float)(height * 32), (float)(800 + height));
            IToast.Visibility itoast$visibility = this.toast.render(matrixStack, JobsToastGui.this, i - this.visibleTime);
            RenderSystem.popMatrix();
            if (itoast$visibility != this.visibility) {
                this.animationTime = i - (long)((int)((1.0F - this.getVisibility(i)) * 600.0F));
                this.visibility = itoast$visibility;
                this.visibility.playSound(JobsToastGui.this.mc.getSoundManager());
            }

            return this.visibility == IToast.Visibility.HIDE && i - this.animationTime > 600L;
        }

        public boolean render(MatrixStack matrixStack, int width, int height) {
            long i = Util.getMillis();
            if (this.animationTime == -1L) {
                this.animationTime = i;
                this.visibility.playSound(JobsToastGui.this.mc.getSoundManager());
            }

            if (this.visibility == IToast.Visibility.SHOW && i - this.animationTime <= 600L) {
                this.visibleTime = i;
            }

            RenderSystem.pushMatrix();
            RenderSystem.translatef((float)width - 160.0F, (float)(height * 32), (float)(800 + height));
            IToast.Visibility itoast$visibility = this.toast.render(matrixStack, JobsToastGui.this, i - this.visibleTime);
            RenderSystem.popMatrix();
            if (itoast$visibility != this.visibility) {
                this.animationTime = i - (long)((int)((1.0F - this.getVisibility(i)) * 600.0F));
                this.visibility = itoast$visibility;
                this.visibility.playSound(JobsToastGui.this.mc.getSoundManager());
            }

            return this.visibility == IToast.Visibility.HIDE && i - this.animationTime > 600L;
        }
    }
}
