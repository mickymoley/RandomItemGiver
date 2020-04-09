package me.mickymoley.randomitemgiver;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.List;

public class TaskGiveItems extends BukkitRunnable {

    private TaskHandler handler;
    private int ticksPerItem;

    public TaskGiveItems(TaskHandler handler, int ticksPerItem){
        this.handler = handler;
        this.ticksPerItem = ticksPerItem;
    }

    @Override
    public void run() {
        handler.giveRandomItem();
        handler.begin(ticksPerItem);
        this.cancel();
    }
}
