package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import org.astemir.smpl.SMPLItems;
import org.astemir.smpl.event.IEntityDamageEntityEventListener;
import org.astemir.smpl.event.IHurtByEntityEventListener;
import org.astemir.smpl.event.IShotEventListener;
import org.astemir.smpl.graphics.ItemModel;
import org.astemir.smpl.utils.EntityHandler;
import org.astemir.smpl.utils.EntityUtils;
import org.astemir.smpl.utils.ItemUtils;
import org.astemir.smpl.utils.RandomUtils;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public class Firestorm extends Item implements IShotEventListener, IEntityDamageEntityEventListener {

    public Firestorm(ItemModel texture, String nameKey) {
        super(texture, nameKey);
    }

    @Override
    public ChatColor getNameColor() {
        return ChatColor.GOLD;
    }


    @Override
    public void onShoot(EntityShootBowEvent e) {
        Entity projectile = e.getProjectile();
        ItemUtils.damageItem((Player)e.getEntity(),e.getBow(),1);
        e.getEntity().getWorld().playSound(e.getEntity().getLocation(),Sound.ITEM_CROSSBOW_SHOOT,1,1.5f);
        e.getEntity().getWorld().playSound(e.getEntity().getLocation(),Sound.ITEM_CROSSBOW_SHOOT,1,0.5f);
        if (projectile instanceof AbstractArrow){
            AbstractArrow arrow = EntityUtils.shootArrow(e.getEntity(),e.getArrowItem(),1,e.getForce(),0,true);
            arrow.setPickupStatus(AbstractArrow.PickupStatus.CREATIVE_ONLY);
            arrow.setMetadata("firestorm_arrow",new FixedMetadataValue(SMPLItems.getPlugin(),true));
            EntityHandler.getInstance().watchEntity(new EntityHandler.EntityRunnable(arrow){
                @Override
                public void run(Entity entity, long ticks) {
                    entity.getWorld().spawnParticle(Particle.FLAME, entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ(), 0, 0, 0, 0);
                    if (arrow.isInBlock()){
                        FireworkEffect effect = FireworkEffect.builder().withColor(Color.ORANGE,Color.YELLOW,Color.RED).withFade(Color.ORANGE,Color.YELLOW,Color.RED).with(FireworkEffect.Type.BALL_LARGE).build();
                        FireworkEffect effect1 = FireworkEffect.builder().withColor(Color.ORANGE,Color.YELLOW,Color.RED).withFade(Color.ORANGE,Color.YELLOW,Color.RED).with(FireworkEffect.Type.BURST).build();
                        EntityUtils.explodeFirework(arrow.getLocation(), 20,effect,effect,effect,effect1,effect1);
                        entity.getWorld().playSound(arrow.getLocation(),Sound.BLOCK_RESPAWN_ANCHOR_CHARGE,2,0.5f);
                        entity.getWorld().playSound(arrow.getLocation(),Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE,2,0.5f);
                        arrow.remove();
                    }
                }

            });
            e.setCancelled(true);
        }
    }


    @Override
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof AbstractArrow){
            if (e.getDamager().hasMetadata("firestorm_arrow")){
                FireworkEffect effect = FireworkEffect.builder().withColor(Color.ORANGE,Color.YELLOW,Color.RED).withFade(Color.ORANGE,Color.YELLOW,Color.RED).with(FireworkEffect.Type.BALL_LARGE).build();
                FireworkEffect effect1 = FireworkEffect.builder().withColor(Color.ORANGE,Color.YELLOW,Color.RED).withFade(Color.ORANGE,Color.YELLOW,Color.RED).with(FireworkEffect.Type.BURST).build();
                e.getEntity().getWorld().playSound(e.getEntity().getLocation(),Sound.BLOCK_RESPAWN_ANCHOR_CHARGE,2,0.5f);
                e.getEntity().getWorld().playSound(e.getEntity().getLocation(),Sound.BLOCK_RESPAWN_ANCHOR_DEPLETE,2,0.5f);
                EntityUtils.explodeFirework(e.getEntity().getLocation(), 20,effect,effect,effect,effect1,effect1);
            }
        }
    }
}
