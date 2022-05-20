package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import org.astemir.smpl.graphics.ItemModel;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.astemir.smpl.utils.RandomUtils;

public class ChillingBlade extends ItemCustomSword{

    public ChillingBlade(ItemModel texture, String nameKey) {
        super(texture, nameKey);
    }


    @Override
    public boolean onAttackEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof LivingEntity){
            World world = e.getEntity().getWorld();
            for (int i = 0;i<10;i++) {
                world.spawnParticle(Particle.SNOWBALL, e.getEntity().getLocation().getX()+ RandomUtils.randomFloat(-0.5f,0.5f), e.getEntity().getLocation().getY()+ 1f+RandomUtils.randomFloat(-0.5f,0.5f), e.getEntity().getLocation().getZ()+ RandomUtils.randomFloat(-0.5f,0.5f), 0, 0, 0, 0);
            }
            ((LivingEntity)e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.SLOW,100,0));
        }
        return super.onAttackEntity(e);
    }

    @Override
    public ChatColor getNameColor() {
        return ChatColor.AQUA;
    }

    @Override
    public double getDamage(){
        return 6;
    }

}
