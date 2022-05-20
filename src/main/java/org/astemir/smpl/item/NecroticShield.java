package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import org.astemir.smpl.event.*;
import org.astemir.smpl.graphics.ItemModel;
import org.astemir.smpl.utils.BeamUtils;
import org.astemir.smpl.utils.ItemUtils;
import org.astemir.smpl.utils.PlayerUtils;
import org.astemir.smpl.utils.RandomUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NecroticShield extends Item implements IHurtByEntityEventListener, ITickEventListener {

    public NecroticShield(ItemModel texture, String nameKey) {
        super(texture, nameKey);
    }

    @Override
    public ChatColor getNameColor() {
        return ChatColor.AQUA;
    }


    @Override
    public void onHurt(EntityDamageByEntityEvent e) {
        Player player = (Player)e.getEntity();
        if (player.isBlocking()){
            if (e.getDamager() instanceof LivingEntity){
                if (!((LivingEntity)e.getDamager()).hasPotionEffect(PotionEffectType.WITHER)) {
                    ((LivingEntity) e.getDamager()).addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 150, 0, false, false));
                }
            }
        }
    }

    @Override
    public ItemStack toItemStack() {
        ItemStack stack = super.toItemStack();
        stack.addUnsafeEnchantment(Enchantment.DURABILITY,3);
        return stack;
    }

    @Override
    public void onTick(PlayerTickEvent e) {
        if (e.getTick() % 10 == 0){
            Location loc = e.getPlayer().getLocation();
            for (int i = 0;i<5;i++) {
                loc.getWorld().spawnParticle(Particle.SMOKE_LARGE, loc.clone().add(RandomUtils.randomFloat(-1f, 1f), 1.75f+RandomUtils.randomFloat(-1.5f,-0.25f), RandomUtils.randomFloat(-1f, 1f)), 0, 0, 0, 0);
            }
        }
    }
}
