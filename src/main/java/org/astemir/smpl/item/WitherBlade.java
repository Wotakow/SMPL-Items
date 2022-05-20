package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import org.astemir.smpl.event.IEntityDeathEventListener;
import org.astemir.smpl.graphics.ItemModel;
import org.astemir.smpl.utils.RandomUtils;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WitherBlade extends ItemCustomSword {

    public WitherBlade(ItemModel texture, String nameKey) {
        super(texture, nameKey);
    }


    @Override
    public boolean onAttackEntity(EntityDamageByEntityEvent e) {
        World world = e.getEntity().getWorld();
        if (e.getEntity() instanceof LivingEntity){
            if (RandomUtils.doWithChance(50)) {
                for (int i = 0; i < 10; i++) {
                    world.spawnParticle(Particle.SMOKE_LARGE, e.getEntity().getLocation().getX() + RandomUtils.randomFloat(-0.25f, 0.25f), e.getEntity().getLocation().getY() + 0.5f + RandomUtils.randomFloat(-0.25f, 0.25f), e.getEntity().getLocation().getZ() + RandomUtils.randomFloat(-0.25f, 0.25f), 20, 0.05f, 0.05f, 0.05f);
                    world.spawnParticle(Particle.SOUL_FIRE_FLAME, e.getEntity().getLocation().getX() + RandomUtils.randomFloat(-0.25f, 0.25f), e.getEntity().getLocation().getY() + 0.5f + RandomUtils.randomFloat(-0.25f, 0.25f), e.getEntity().getLocation().getZ() + RandomUtils.randomFloat(-0.25f, 0.25f), 10, 0.05f, 0.05f, 0.05f);
                }
                for (int i = 0; i < 2; i++) {
                    world.playSound(e.getEntity().getLocation(), Sound.ENTITY_WITHER_AMBIENT, 1, 1.15f);
                    world.playSound(e.getEntity().getLocation(), Sound.ENTITY_WITHER_SHOOT, 1, 2);
                }
                ((LivingEntity)e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.WITHER,150,0,false,false));
            }
        }
        return super.onAttackEntity(e);
    }


    @Override
    public ChatColor getNameColor() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public double getDamage(){
        return 14;
    }

    @Override
    public double getAttackSpeed() {
        return -3.4;
    }
}

