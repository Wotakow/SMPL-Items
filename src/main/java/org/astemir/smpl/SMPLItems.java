package org.astemir.smpl;

import org.astemir.smpl.commands.ItemGiveCommand;
import org.astemir.smpl.event.EventListener;
import org.astemir.smpl.event.ITickEventListener;
import org.astemir.smpl.event.PlayerTickEvent;
import org.astemir.smpl.item.Item;
import org.astemir.smpl.item.Items;
import org.astemir.smpl.utils.EntityHandler;
import org.astemir.smpl.utils.PlayerCooldownManager;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class SMPLItems extends JavaPlugin {

    private PlayerCooldownManager cooldownManager;
    private EntityHandler entityHandler;
    private static SMPLItems plugin;
    private long ticks = 0;

    @Override
    public void onEnable() {
        plugin = this;
        cooldownManager = new PlayerCooldownManager();
        entityHandler = new EntityHandler();
        Bukkit.getPluginCommand("itemgive").setExecutor(new ItemGiveCommand());
        Bukkit.getPluginManager().registerEvents(new EventListener(),this);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this,()->{
            cooldownManager.update();
            entityHandler.update(ticks);
            ticks++;
            Bukkit.getOnlinePlayers().forEach((player)->{
                ItemStack mainHand = player.getInventory().getItemInMainHand();
                ItemStack offHand = player.getInventory().getItemInOffHand();
                if (Items.getItem(mainHand) != null) {
                    Item item = Items.getItem(mainHand);
                    if (item instanceof ITickEventListener) {
                        ((ITickEventListener) item).onTick(new PlayerTickEvent(player, mainHand, ticks));
                    }
                }else if (Items.getItem(offHand) != null) {
                    Item item = Items.getItem(offHand);
                    if (item instanceof ITickEventListener) {
                        ((ITickEventListener)item).onTick(new PlayerTickEvent(player, offHand, ticks));
                    }
                }
            });
        },0,0);
    }

    public PlayerCooldownManager getCooldownManager() {
        return cooldownManager;
    }

    public EntityHandler getEntityHandler() {
        return entityHandler;
    }

    public static SMPLItems getPlugin() {
        return plugin;
    }

    @Override
    public void onDisable() {
    }
}
