package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.world.item.BowItem;
import org.astemir.smpl.event.IShotEventListener;
import org.astemir.smpl.graphics.ItemModel;
import org.astemir.smpl.utils.*;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.util.Vector;

public class Ragnarok extends Item implements IShotEventListener {

    public Ragnarok(ItemModel texture, String nameKey) {
        super(texture, nameKey);
    }

    @Override
    public ChatColor getNameColor() {
        return ChatColor.AQUA;
    }


    @Override
    public void onShoot(EntityShootBowEvent e) {
        for (int i = 0;i<4;i++) {
            e.getEntity().getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, e.getEntity().getLocation().getX()+ RandomUtils.randomFloat(-0.5f,0.5f), e.getEntity().getLocation().getY()+ 1f+RandomUtils.randomFloat(-0.5f,0.75f), e.getEntity().getLocation().getZ()+ RandomUtils.randomFloat(-0.5f,0.5f), 10, 0, 0, 0);
        }
        e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_BLAZE_SHOOT,0.5f,1.5f);
        e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_SKELETON_SHOOT,1,1.5f*e.getForce());
        e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ITEM_CROSSBOW_SHOOT,1,1.5f*e.getForce());
        Player player = (Player)e.getEntity();
        int segments = (int) (10*e.getForce());
        ItemUtils.damageItem(player,e.getBow(),1);
        for (int i = 0;i<segments/3;i++) {
            BeamUtils.sendParticleLightning(Particle.END_ROD, PlayerUtils.getPlayerEyeLocation(player), PlayerUtils.getPlayerDirection(player), 2, (ent) -> {
                if (!ent.getUniqueId().equals(player.getUniqueId())) {
                    ent.damage(5, player);
                    if (RandomUtils.doWithChance(50) && !PlayerUtils.hasCooldown(player,"ragnarok")) {
                        ent.getWorld().strikeLightning(ent.getLocation());
                        PlayerUtils.cooldown(player,"ragnarok",200);
                    }
                    return true;
                } else {
                    return false;
                }
            }, segments, 0.2f,false);
        }
        e.setCancelled(true);
    }



}
