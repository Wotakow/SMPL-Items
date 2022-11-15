package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.world.level.block.Blocks;
import org.astemir.smpl.event.IEntityDeathEventListener;
import org.astemir.smpl.event.ITickEventListener;
import org.astemir.smpl.event.PlayerTickEvent;
import org.astemir.smpl.graphics.ItemModel;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.astemir.smpl.utils.RandomUtils;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ScarletDagger extends ItemCustomSword implements ITickEventListener {

    public ScarletDagger(ItemModel texture, String nameKey) {
        super(texture, nameKey);
    }


    @Override
    public boolean onAttackEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof LivingEntity){
            if (RandomUtils.doWithChance(50)){
                scarletEffect(e.getEntity().getLocation());
                ((LivingEntity)e.getDamager()).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,100,1,false,false));
            }
        }
        return super.onAttackEntity(e);
    }

    @Override
    public void onTick(PlayerTickEvent e) {
        if (e.getTick() % 10 == 0){
            Location loc = e.getPlayer().getLocation();
            for (int i = 0;i<3;i++) {
                loc.getWorld().spawnParticle(Particle.FALLING_DUST, loc.clone().add(RandomUtils.randomFloat(-0.5f, 0.5f), 1.75f, RandomUtils.randomFloat(-0.5f, 0.5f)), 0, 0, 0, 0, Material.REDSTONE_BLOCK.createBlockData());
            }
        }
    }


    private void scarletEffect(Location loc){
        loc.getWorld().playSound(loc,Sound.ENTITY_SQUID_DEATH,0.75f,0.5f);
        loc.getWorld().playSound(loc,Sound.ENTITY_SQUID_DEATH,1,1f);
        loc.getWorld().playSound(loc,Sound.ENTITY_BLAZE_SHOOT,1,0.75f);
        loc.getWorld().playSound(loc,Sound.ENTITY_BLAZE_SHOOT,1,2f);
        for (int i = 0;i<2;i++) {
            loc.getWorld().spawnParticle(Particle.ITEM_CRACK, loc.clone().add(0,0.5f,0), 20, 0.3f, 0.3f, 0.3f, 0.15f, new ItemStack(Material.REDSTONE_BLOCK));
            loc.getWorld().spawnParticle(Particle.ITEM_CRACK, loc.clone().add(0,0.5f,0), 40, 0.35f, 0.35f, 0.35f, 0.25f, new ItemStack(Material.ROTTEN_FLESH));
        }
    }


    @Override
    public ChatColor getNameColor() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public double getDamage(){
        return 6.5f;
    }

    @Override
    public double getAttackSpeed() {
        return -2.2;
    }
}
