package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import org.astemir.smpl.graphics.ItemModel;
import org.astemir.smpl.utils.RandomUtils;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SacrificeSword extends ItemCustomSword {

    public SacrificeSword(ItemModel texture, String nameKey) {
        super(texture, nameKey);
    }


    @Override
    public boolean onAttackEntity(EntityDamageByEntityEvent e) {
        World world = e.getEntity().getWorld();
        if (e.getEntity() instanceof LivingEntity){
            for (int i = 0; i < 5; i++) {
                world.spawnParticle(Particle.SOUL, e.getEntity().getLocation().getX() + RandomUtils.randomFloat(-0.25f, 0.25f), e.getEntity().getLocation().getY() + 0.5f + RandomUtils.randomFloat(-0.25f, 0.25f), e.getEntity().getLocation().getZ() + RandomUtils.randomFloat(-0.25f, 0.25f), 10, 0,0,0);
            }
            for (int i = 0; i < 2; i++) {
                world.playSound(e.getEntity().getLocation(), Sound.ENTITY_HUSK_CONVERTED_TO_ZOMBIE, 1, 1.5f);
                world.playSound(e.getEntity().getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1, 2);
            }
            ((LivingEntity)e.getDamager()).addPotionEffect(new PotionEffect(PotionEffectType.HARM,1,1,false,false));
        }
        return super.onAttackEntity(e);
    }


    @Override
    public ChatColor getNameColor() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public double getDamage(){
        return 16;
    }

    @Override
    public double getAttackSpeed() {
        return -3.4;
    }
}

