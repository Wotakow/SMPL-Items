package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import org.astemir.smpl.event.IShotEventListener;
import org.astemir.smpl.graphics.ItemModel;
import org.astemir.smpl.utils.EntityHandler;
import org.astemir.smpl.utils.EntityUtils;
import org.astemir.smpl.utils.ItemUtils;
import org.astemir.smpl.utils.RandomUtils;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class MultiplexCrossbow extends Item implements IShotEventListener {

    public MultiplexCrossbow(ItemModel texture, String nameKey) {
        super(texture, nameKey);
    }

    @Override
    public ChatColor getNameColor() {
        return ChatColor.BLUE;
    }


    @Override
    public void onShoot(EntityShootBowEvent e) {
        Entity projectile = e.getProjectile();
        ItemUtils.damageItem((Player)e.getEntity(),e.getBow(),1);
        e.getEntity().getWorld().playSound(e.getEntity().getLocation(),Sound.ITEM_CROSSBOW_SHOOT,1,1.5f);
        e.getEntity().getWorld().playSound(e.getEntity().getLocation(),Sound.ITEM_CROSSBOW_SHOOT,1,0.5f);
        for (int i = 0;i<4;i++) {
            e.getEntity().getWorld().spawnParticle(Particle.LAVA, e.getEntity().getLocation().getX()+ RandomUtils.randomFloat(-0.5f,0.5f), e.getEntity().getLocation().getY()+ 1f+RandomUtils.randomFloat(-0.5f,0.75f), e.getEntity().getLocation().getZ()+ RandomUtils.randomFloat(-0.5f,0.5f), 0, 0, 0, 0);
        }
        for (int i = 0;i<3;i++) {
            Vector randomVec = new Vector(RandomUtils.randomFloat(-0.5f, 0.5f), RandomUtils.randomFloat(-0.5f, 0.5f), RandomUtils.randomFloat(-0.5f, 0.5f));
            if (projectile instanceof AbstractArrow){
                AbstractArrow arrow = EntityUtils.shootArrow(e.getEntity(),e.getArrowItem(),1,e.getForce(),0,true);
                if (i != 0){
                    arrow.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
                }
                EntityUtils.setArrowLife(arrow,1000);
                arrow.setVelocity(arrow.getVelocity().clone().add(randomVec));
                EntityHandler.getInstance().watchEntity(new EntityHandler.EntityRunnable(arrow){
                    @Override
                    public void run(Entity entity, long ticks) {
                        if (ticks % 20 == 0) {
                            entity.getWorld().spawnParticle(Particle.LAVA, entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ(), 0, 0, 0, 0);
                        }
                    }
                });
            }else
            if (projectile instanceof Firework){
                Firework firework = EntityUtils.shootFirework(e.getEntity(),e.getArrowItem(),e.getForce(),1);
                firework.setVelocity(firework.getVelocity().clone().add(randomVec));
            }
        }
        e.setCancelled(true);
    }



}
