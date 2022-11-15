package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import org.astemir.smpl.event.*;
import org.astemir.smpl.graphics.ItemModel;
import org.astemir.smpl.graphics.ParticleEffect;
import org.astemir.smpl.utils.PlayerUtils;
import org.astemir.smpl.utils.RandomUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UnstablePowder extends Item {

    public UnstablePowder(ItemModel texture, String nameKey) {
        super(texture, nameKey);
    }

    @Override
    public ChatColor getNameColor() {
        return ChatColor.BLUE;
    }


    @Override
    public boolean onRightClick(PlayerClickEvent e) {
        if (!PlayerUtils.hasCooldown(e.getPlayer(),"unstable_powder")) {
            if (e.getHand() == EquipmentSlot.HAND) {
                e.getPlayer().swingMainHand();
            }else{
                e.getPlayer().swingOffHand();
            }
            e.getItem().subtract(1);
            ParticleEffect effect = new ParticleEffect(Particle.END_ROD).speed(0.5f,0.5f,0.5f).randomSpeed().renderTimes(30);
            effect.render(e.getPlayer().getLocation());
            e.getPlayer().getLocation().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_WITCH_DRINK,1,0.5f);
            e.getPlayer().getLocation().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CURE,1,2);
            e.getPlayer().getNearbyEntities(10,10,10).forEach((entity)->{
                if (entity instanceof LivingEntity){
                    ((LivingEntity)entity).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,400,0,false,false));
                }
            });
            PlayerUtils.cooldown(e.getPlayer(),"unstable_powder",200);
        }
        return super.onRightClick(e);
    }
}
