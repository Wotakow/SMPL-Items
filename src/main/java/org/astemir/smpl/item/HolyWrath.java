package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import org.astemir.smpl.event.IShotEventListener;
import org.astemir.smpl.graphics.ItemModel;
import org.astemir.smpl.utils.EntityHandler;
import org.astemir.smpl.utils.EntityUtils;
import org.astemir.smpl.utils.ItemUtils;
import org.astemir.smpl.utils.RandomUtils;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.util.Vector;

public class HolyWrath extends Item implements IShotEventListener {

    public HolyWrath(ItemModel texture, String nameKey) {
        super(texture, nameKey);
    }

    @Override
    public ChatColor getNameColor() {
        return ChatColor.LIGHT_PURPLE;
    }


    private void spreadArrows(AbstractArrow arrow,float power,boolean spectral){
        for (int i = 0; i < 360; i+=30) {
            AbstractArrow smallFire = arrow.getWorld().spawnArrow(arrow.getLocation(), new Vector(Math.cos(i), 1, Math.sin(i)), power, 0);
            EntityUtils.setArrowLife(smallFire, 1150);
            smallFire.setFireTicks(99999);
            EntityHandler.getInstance().watchEntity(new EntityHandler.EntityRunnable(smallFire) {
                @Override
                public void run(Entity entity, long ticks) {
                    if (smallFire.isInBlock()) {
                        Location smallLoc = smallFire.getLocation().clone().add(new Vector(0, 0, 0));
                        placeFire(smallLoc,spectral);
                        entity.getWorld().spawnParticle(Particle.END_ROD, entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ(), 0, 0, 0, 0);
                        cancel();
                        smallFire.remove();
                    }
                }
            });
        }
    }

    private void particleCircle(Location loc,float radius){
        for (int i = 0;i<360;i+=10){
            loc.getWorld().spawnParticle(Particle.END_ROD,loc.toCenterLocation().clone().add(new Vector(Math.cos(i)*radius,0,Math.sin(i)*radius)),0,Math.cos(i)/2,0,Math.sin(i)/2);
        }
    }

    private void placeFire(Location loc,boolean spectral){
        if (loc.getBlock().isEmpty() && spectral){
            loc.getBlock().setType(Material.FIRE);
        }
    }

    @Override
    public void onShoot(EntityShootBowEvent e) {
        Entity projectile = e.getProjectile();
        if (projectile instanceof AbstractArrow) {
            ItemUtils.damageItem((Player) e.getEntity(), e.getBow(), 1);
            e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 0.5f);
            e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ITEM_CROSSBOW_SHOOT, 1, 0.5f);
            AbstractArrow arrow = EntityUtils.shootArrow(e.getEntity(), e.getArrowItem(), 1, e.getForce(), 0, true);
            arrow.setPierceLevel(2);
            arrow.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
            EntityUtils.setArrowLife(arrow,1150);
            EntityHandler.getInstance().watchEntity(new EntityHandler.EntityRunnable(arrow) {
                int k = 0;
                @Override
                public void run(Entity entity, long ticks) {
                    arrow.getWorld().spawnParticle(Particle.END_ROD, arrow.getLocation().getX(), arrow.getLocation().getY(), arrow.getLocation().getZ(), 0, 0, 0, 0);
                    if (arrow.isInBlock()){
                        if (k == 0) {
                            particleCircle(arrow.getLocation().clone().add(0,1,0),6);
                            particleCircle(arrow.getLocation().clone().add(0,2f,0),4);
                            particleCircle(arrow.getLocation().clone().add(0,4f,0),2);
                            particleCircle(arrow.getLocation().clone().add(0,6f,0),1);
                            placeFire(arrow.getLocation().clone().add(new Vector(1,0,0)),e.getArrowItem().getType().equals(Material.SPECTRAL_ARROW));
                            placeFire(arrow.getLocation().clone().add(new Vector(0,0,1)),e.getArrowItem().getType().equals(Material.SPECTRAL_ARROW));
                            placeFire(arrow.getLocation().clone().add(new Vector(-1,0,0)),e.getArrowItem().getType().equals(Material.SPECTRAL_ARROW));
                            placeFire(arrow.getLocation().clone().add(new Vector(0,0,-1)),e.getArrowItem().getType().equals(Material.SPECTRAL_ARROW));
                            placeFire(arrow.getLocation().clone().add(new Vector(0,0,0)),e.getArrowItem().getType().equals(Material.SPECTRAL_ARROW));
                            EntityUtils.explodeFirework(arrow.getLocation(), 10, FireworkEffect.builder().withColor(Color.WHITE).withFlicker().build());
                        }
                        k++;
                        if (k == 5){
                            spreadArrows(arrow,0.65f,e.getArrowItem().getType().equals(Material.SPECTRAL_ARROW));
                        }else
                        if (k == 10){
                            spreadArrows(arrow,0.5f,e.getArrowItem().getType().equals(Material.SPECTRAL_ARROW));
                        }else
                        if (k > 40){
                            arrow.remove();
                            cancel();
                        }
                    }
                }
            });
            e.setCancelled(true);
        }
    }



}
