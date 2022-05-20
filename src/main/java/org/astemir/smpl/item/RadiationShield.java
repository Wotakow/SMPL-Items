package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import org.astemir.smpl.event.IHurtByEntityEventListener;
import org.astemir.smpl.event.ITickEventListener;
import org.astemir.smpl.event.PlayerTickEvent;
import org.astemir.smpl.graphics.ItemModel;
import org.astemir.smpl.utils.RandomUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RadiationShield extends Item implements IHurtByEntityEventListener, ITickEventListener {

    public RadiationShield(ItemModel texture, String nameKey) {
        super(texture, nameKey);
    }

    @Override
    public ChatColor getNameColor() {
        return ChatColor.GREEN;
    }


    @Override
    public void onHurt(EntityDamageByEntityEvent e) {
        Player player = (Player)e.getEntity();
        if (player.isBlocking()){
            if (e.getDamager() instanceof LivingEntity){
                if (!((LivingEntity)e.getDamager()).hasPotionEffect(PotionEffectType.POISON)) {
                    ((LivingEntity) e.getDamager()).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 150, 1, false, false));
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
                loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(RandomUtils.randomFloat(-1f, 1f), 1.75f+RandomUtils.randomFloat(-1.5f,-0.25f), RandomUtils.randomFloat(-1f, 1f)), 0, 0, 0, 0,new Particle.DustOptions(Color.GREEN,2f));
            }
        }
    }
}
