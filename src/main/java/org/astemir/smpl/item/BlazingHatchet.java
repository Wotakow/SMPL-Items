package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import org.astemir.smpl.SMPLItems;
import org.astemir.smpl.event.IBreakBlockEventListener;
import org.astemir.smpl.event.IEntityDeathEventListener;
import org.astemir.smpl.graphics.ItemModel;
import org.astemir.smpl.utils.ItemUtils;
import org.astemir.smpl.utils.PlayerUtils;
import org.astemir.smpl.utils.RandomUtils;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class BlazingHatchet extends ItemCustomSword implements IBreakBlockEventListener {

    public BlazingHatchet(ItemModel texture, String nameKey) {
        super(texture, nameKey);
    }


    @Override
    public boolean onAttackEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player player = (Player) e.getDamager();
            if (!PlayerUtils.hasCooldown(player, "blazing")) {
                PlayerUtils.cooldown(player, "blazing", 80);
                Location loc = e.getEntity().getLocation();
                loc.getWorld().playSound(loc, Sound.ENTITY_BLAZE_SHOOT, 1, 0.5f);
                for (int i = 0;i<2;i++) {
                    loc.getWorld().playSound(loc, Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 0.5f);
                    loc.getWorld().spawnParticle(Particle.FLAME, loc.clone().add(0,0.5f,0), 100, 0.5f, 0.5f, 0.5f, 0.1f);
                }
                loc.getNearbyLivingEntities(1.5f, 1, 1.5f).forEach((entity) -> {
                    if (!entity.getUniqueId().equals(player.getUniqueId())) {
                        blaze(player, entity);
                    }
                });
            }
        }
        return super.onAttackEntity(e);
    }

    private void blaze(Player player,Entity entity){
        if (!entity.getUniqueId().equals(player.getUniqueId())) {
            Vector direction = PlayerUtils.getPlayerDirection(player).clone().normalize();
            entity.setFireTicks(150);
            new BukkitRunnable(){
                @Override
                public void run() {
                    entity.setVelocity(new Vector(direction.getX()*2,1.25f,direction.getZ()*2));
                }
            }.runTaskLater(SMPLItems.getPlugin(),1);
        }
    }


    @Override
    public ChatColor getNameColor() {
        return ChatColor.GOLD;
    }

    @Override
    public double getDamage(){
        return 8;
    }


    @Override
    public double getAttackSpeed() {
        return -3;
    }

    @Override
    public void onBreakBlock(BlockBreakEvent e) {
        if (ItemUtils.isWoodLog(e.getBlock().getType())){
            e.setDropItems(false);
            e.getBlock().getWorld().spawnParticle(Particle.FLAME, e.getBlock().getLocation().toCenterLocation(), 10, 0.25f, 0.25f, 0.25f, 0.1f);
            e.getBlock().getWorld().dropItem(e.getBlock().getLocation(),new ItemStack(Material.CHARCOAL));
        }
    }
}
