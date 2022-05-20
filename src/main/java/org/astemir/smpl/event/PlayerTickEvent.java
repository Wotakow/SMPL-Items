package org.astemir.smpl.event;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class PlayerTickEvent{


    private Player player;
    private ItemStack item;
    private long tick = 0;

    public PlayerTickEvent(Player player, ItemStack item,long ticks) {
        this.player = player;
        this.item = item;
        this.tick = ticks;
    }

    public long getTick() {
        return tick;
    }

    public ItemStack getItemStack() {
        return item;
    }

    public Player getPlayer() {
        return player;
    }
}
