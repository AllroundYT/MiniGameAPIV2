package de.allround.util;

import de.allround.Minigame;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Countdown {
    private final int startTime;
    private final int tickLength;
    private final BukkitRunnable bukkitRunnable;
    private int time;
    private boolean cancelNextTick;
    private boolean paused;
    private boolean started;

    public Countdown(int startTime, int tickLength) {
        this.startTime = startTime;
        this.tickLength = tickLength;
        this.bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                tick();
            }
        };
    }

    public BukkitRunnable getBukkitRunnable() {
        return bukkitRunnable;
    }

    public void start() {
        getBukkitRunnable().runTaskTimer(Minigame.getInstance(), 0, getTickLength());
        this.started = true;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean hasBeenStarted() {
        return started;
    }

    private void cancelTask() {
        if (!hasBeenStarted()) return;
        getBukkitRunnable().cancel();
    }

    public int getStartTime() {
        return startTime;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTickLength() {
        return tickLength;
    }

    public boolean isCancelNextTick() {
        return cancelNextTick;
    }

    public void setCancelNextTick(boolean cancelNextTick) {
        this.cancelNextTick = cancelNextTick;
    }

    private void tick() {
        if (isPaused()) return;
        preTick();
        if (isCancelNextTick()) return;
        postTick();
        if (getTime() <= 0) {
            stop();
        }
        time--;
    }

    public void stop(){
        onEnd();
        cancelTask();
    }

    public abstract void preTick();

    public abstract void postTick();

    public abstract void onEnd();
}
