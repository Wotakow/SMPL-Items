package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import org.astemir.smpl.event.IShotEventListener;
import org.astemir.smpl.event.ITickEventListener;
import org.astemir.smpl.event.PlayerTickEvent;
import org.astemir.smpl.graphics.ItemModel;
import org.astemir.smpl.utils.*;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.util.Vector;

public class SentrysWrath extends Item implements IShotEventListener, ITickEventListener {

    public SentrysWrath(ItemModel texture, String nameKey) {
        super(texture, nameKey);
    }

    @Override
    public ChatColor getNameColor() {
        return ChatColor.LIGHT_PURPLE;
    }


    @Override
    public void onShoot(EntityShootBowEvent e) {
        Entity projectile = e.getProjectile();
        if (projectile instanceof Arrow) {
            Player player = (Player)e.getEntity();
            ItemUtils.damageItem((Player) e.getEntity(),e.getBow(),1);
            e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_CHARGE, 1, 1.5f);
            e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ITEM_CROSSBOW_SHOOT, 1, 0.5f);
            AbstractArrow arrow = EntityUtils.shootArrow(e.getEntity(), e.getArrowItem(), 1, e.getForce(), 0, true);
            arrow.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
            arrow.setGravity(false);
            arrow.setPierceLevel(120);
            arrow.setVelocity(arrow.getVelocity().clone().multiply(0.1f));
            EntityUtils.hideEntity(arrow);
            EntityHandler.getInstance().watchEntity(new EntityHandler.EntityRunnable(arrow) {
                @Override
                public void runOut(Entity entity, long ticks) {
                    for (int j = 0;j<3;j++) {
                        arrow.getWorld().playSound(arrow.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE, 2, 2);
                        Location randomLoc = arrow.getLocation().clone().add(new Vector(RandomUtils.randomFloat(-3, 3), RandomUtils.randomFloat(2, 4), RandomUtils.randomFloat(-3, 3)));
                        Vector dir = arrow.getLocation().toVector().add(randomLoc.toVector().multiply(-1)).normalize();
                        for (int i = 0; i < 5; i++) {
                            Location loc = BeamUtils.sendParticleLightning(Particle.SOUL_FIRE_FLAME, randomLoc, dir, 2, (ent) -> {
                                if (!ent.getUniqueId().equals(player.getUniqueId())) {
                                    ent.damage(10, player);
                                    if (!PlayerUtils.hasCooldown(player, "sentrys_wrath")) {
                                        PlayerUtils.cooldown(player, "sentrys_wrath", 40);
                                        EntityUtils.explodeFirework(ent.getLocation(), 2, FireworkEffect.builder().withFlicker().withColor(Color.AQUA).build());
                                    }
                                    return true;
                                } else {
                                    return false;
                                }
                            }, 6, 0.5f, false).getLoc();
                            arrow.getLocation().getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, arrow.getLocation().clone().add(RandomUtils.randomFloat(-2f, 2f), RandomUtils.randomFloat(-2f, 2f), RandomUtils.randomFloat(-2f, 2f)), 0, 0, 0, 0);
                            arrow.getLocation().getWorld().spawnParticle(Particle.REDSTONE, arrow.getLocation().clone().add(RandomUtils.randomFloat(-0.5f, 0.5f), RandomUtils.randomFloat(-0.5f, 0.5f), RandomUtils.randomFloat(-0.5f, 0.5f)), 10, 0, 0, 0, new Particle.DustOptions(Color.AQUA, 2f));
                        }
                    }
                    arrow.getWorld().playSound(arrow.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE, 2, 0.5f);
                    arrow.getWorld().playSound(arrow.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 2, 1f);
                    EntityUtils.explodeFirework(entity.getLocation(), 5, FireworkEffect.builder().withFlicker().withColor(Color.AQUA).withColor(Color.BLUE).build());
                }

                @Override
                public void run(Entity entity, long ticks) {
                    if (ticks % 5 == 0 && RandomUtils.doWithChance(50)) {
                        if (arrow.isInBlock()){
                            cancel();
                            arrow.remove();
                        }else{
                            arrow.getWorld().playSound(arrow.getLocation(), Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE, 2, 2);
                            Location randomLoc = arrow.getLocation().clone().add(new Vector(RandomUtils.randomFloat(-3,3), RandomUtils.randomFloat(2, 4), RandomUtils.randomFloat(-3, 3)));
                            Vector dir = arrow.getLocation().toVector().add(randomLoc.toVector().multiply(-1)).normalize();
                            for (int i = 0;i<5;i++) {
                                Location loc = BeamUtils.sendParticleLightning(Particle.SOUL_FIRE_FLAME,randomLoc , dir, 2, (ent) -> {
                                    if (!ent.getUniqueId().equals(player.getUniqueId())) {
                                        ent.damage(10, player);
                                        if (!PlayerUtils.hasCooldown(player,"sentrys_wrath")) {
                                            PlayerUtils.cooldown(player,"sentrys_wrath",40);
                                            EntityUtils.explodeFirework(ent.getLocation(), 2, FireworkEffect.builder().withFlicker().withColor(Color.AQUA).build());
                                        }
                                        return true;
                                    } else {
                                        return false;
                                    }
                                }, 6, 0.5f, false).getLoc();
                                arrow.getLocation().getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, arrow.getLocation().clone().add(RandomUtils.randomFloat(-2f, 2f), RandomUtils.randomFloat(-2f, 2f), RandomUtils.randomFloat(-2f, 2f)), 0, 0, 0, 0);
                                arrow.getLocation().getWorld().spawnParticle(Particle.REDSTONE, arrow.getLocation().clone().add(RandomUtils.randomFloat(-0.5f, 0.5f), RandomUtils.randomFloat(-0.5f,0.5f), RandomUtils.randomFloat(-0.5f, 0.5f)), 10, 0, 0, 0, new Particle.DustOptions(Color.AQUA,2f));
                            }
                        }
                    }


                }
            }).lifespan(100);
            e.setCancelled(true);
        }
    }


    @Override
    public void onTick(PlayerTickEvent e) {
        if (e.getTick() % 10 == 0){
            Location loc = e.getPlayer().getLocation();
            for (int i = 0;i<10;i++) {
                loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(RandomUtils.randomFloat(-1f, 1f), 1.75f+RandomUtils.randomFloat(-0.5f,0.1f), RandomUtils.randomFloat(-1f, 1f)), 0, 0, 0, 0, new Particle.DustOptions(Color.AQUA,1f));
            }
        }
    }
}
