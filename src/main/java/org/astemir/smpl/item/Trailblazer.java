package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import org.astemir.smpl.SMPLItems;
import org.astemir.smpl.event.IEntityDamageEntityEventListener;
import org.astemir.smpl.event.IShotEventListener;
import org.astemir.smpl.graphics.ItemModel;
import org.astemir.smpl.graphics.ParticleEffect;
import org.astemir.smpl.utils.*;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public class Trailblazer extends Item implements IShotEventListener, IEntityDamageEntityEventListener {

    public Trailblazer(ItemModel texture, String nameKey) {
        super(texture, nameKey);
    }

    @Override
    public ChatColor getNameColor() {
        return ChatColor.GOLD;
    }


    @Override
    public void onShoot(EntityShootBowEvent e) {
        ParticleEffect effect = new ParticleEffect(Particle.FLAME).size(1,1,1).speed(1,1,1).randomSpeed().renderTimes(10);
        effect.render(e.getEntity().getLocation());
        EntityHandler.getInstance().watchEntity(new EntityHandler.EntityRunnable(e.getProjectile()){
            @Override
            public void run(Entity entity, long ticks) {
                if (ticks % 2 == 0) {
                    entity.getWorld().spawnParticle(Particle.FLAME, entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ(), 0, 0, 0, 0);
                }
            }
        }).lifespan(100);
        e.getProjectile().setFireTicks(99999);
        e.getProjectile().setMetadata("trailblazer",new FixedMetadataValue(SMPLItems.getPlugin(),true));
        e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_BLAZE_SHOOT,0.5f,1.5f);
        e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_SKELETON_SHOOT,1,1.5f*e.getForce());
        e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_EVOKER_CAST_SPELL,1,1.5f*e.getForce());
    }


    @Override
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof AbstractArrow){
            if (e.getDamager().hasMetadata("trailblazer")){
                FireworkEffect effect = FireworkEffect.builder().withColor(Color.ORANGE,Color.YELLOW,Color.RED).withFade(Color.ORANGE,Color.YELLOW,Color.RED).with(FireworkEffect.Type.BALL_LARGE).build();
                e.getEntity().getWorld().playSound(e.getEntity().getLocation(),Sound.ENTITY_WITHER_SHOOT,2,0.5f);
                e.getEntity().getWorld().playSound(e.getEntity().getLocation(),Sound.ENTITY_BLAZE_SHOOT,2,0.5f);
                EntityUtils.explodeFirework(e.getEntity().getLocation(), 20,effect);
                Player player = (Player) ((AbstractArrow) e.getDamager()).getShooter();
                for (int i = 0;i<6;i++) {
                    BeamUtils.sendParticleLightning(new ParticleEffect(Particle.FLAME), e.getEntity().getLocation(), new Vector(Math.cos(RandomUtils.randomInt(0, 360)), 1, Math.sin(RandomUtils.randomInt(0, 360))), 1, (entity) -> {
                        if (!entity.getUniqueId().equals(player.getUniqueId())) {
                            entity.damage(5, player);
                            entity.setFireTicks(100);
                            return true;
                        }
                        return false;
                    }, 4, 0.5f, false);
                }
            }
        }
    }

}
