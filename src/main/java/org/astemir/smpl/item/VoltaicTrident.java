package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import org.astemir.smpl.SMPLItems;
import org.astemir.smpl.event.IThrowEventListener;
import org.astemir.smpl.event.ITickEventListener;
import org.astemir.smpl.event.PlayerTickEvent;
import org.astemir.smpl.graphics.ItemModel;
import org.astemir.smpl.graphics.ParticleEffect;
import org.astemir.smpl.utils.BeamUtils;
import org.astemir.smpl.utils.EntityHandler;
import org.astemir.smpl.utils.EntityUtils;
import org.astemir.smpl.utils.RandomUtils;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class VoltaicTrident extends Item implements IThrowEventListener, ITickEventListener {

    public VoltaicTrident(ItemModel texture, String nameKey) {
        super(texture, nameKey);
    }


    @Override
    public ChatColor getNameColor() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public void onThrownHit(ProjectileHitEvent e) {
        if (e.getEntity() instanceof Trident){
            if (e.getEntity().hasMetadata("voltaic")){
                strike(e);
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        strike(e);
                    }
                }.runTaskLater(SMPLItems.getPlugin(),4);
            }
        }
    }

    private void strike(ProjectileHitEvent e){
        ParticleEffect color = new ParticleEffect(Particle.REDSTONE).size(2,2,2).color(Color.BLUE,2).renderTimes(50);
        ParticleEffect whiteExplosion = new ParticleEffect(Particle.FIREWORKS_SPARK).renderTimes(100).size(0.5f,0.5f,0.5f).speed(2f,2f,2f).randomSpeed();
        for (int i = 0;i<20;i++) {
            BeamUtils.sendParticleLightning(new ParticleEffect(Particle.REDSTONE).color(Color.FUCHSIA,1),e.getEntity().getLocation(),new Vector(Math.cos(RandomUtils.randomInt(0,360)),Math.sin(RandomUtils.randomInt(0,360)),Math.sin(RandomUtils.randomInt(0,360))),2,(entity)->{
                Player player = (Player)e.getEntity().getShooter();
                if (!entity.getUniqueId().equals(player.getUniqueId())) {
                    entity.damage(5,player);
                    return true;
                }
                return false;
            },7,1f,false);
        }
        EntityUtils.explodeFirework(e.getEntity().getLocation(),10,FireworkEffect.builder().withColor(Color.BLUE).build());
        whiteExplosion.render(e.getEntity().getLocation());
        color.render(e.getEntity().getLocation());
    }

    @Override
    public void onThrow(ProjectileLaunchEvent e) {
        if (e.getEntity() instanceof Trident){
            Trident trident = (Trident) e.getEntity();
            trident.setCustomName("Voltaic");
            trident.setMetadata("voltaic",new FixedMetadataValue(SMPLItems.getPlugin(),true));
            EntityHandler.getInstance().watchEntity(new EntityHandler.EntityRunnable(trident) {
                @Override
                public void run(Entity entity, long ticks) {
                    entity.getLocation().getWorld().spawnParticle(Particle.REDSTONE,  entity.getLocation().clone().add(RandomUtils.randomFloat(-1f, 1f), 1.75f+RandomUtils.randomFloat(-1.5f,-0.25f), RandomUtils.randomFloat(-1f, 1f)), 0, 0, 0, 0,new Particle.DustOptions(Color.BLUE,1));
                }
            });
        }
    }

    @Override
    public void onTick(PlayerTickEvent e) {
        if (e.getTick() % 5 == 0){
            Location loc = e.getPlayer().getLocation();
            for (int i = 0;i<2;i++) {
                loc.getWorld().spawnParticle(Particle.END_ROD, loc.clone().add(RandomUtils.randomFloat(-1f, 1f), 2f+RandomUtils.randomFloat(-1.75f,-0.25f), RandomUtils.randomFloat(-1f, 1f)), 0, 0, 0, 0);
            }
        }
    }

    @Override
    public ItemStack toItemStack() {
        ItemStack stack = super.toItemStack();
        stack.addUnsafeEnchantment(Enchantment.LOYALTY,3);
        return stack;
    }
}

