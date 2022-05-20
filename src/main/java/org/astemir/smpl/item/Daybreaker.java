package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import org.astemir.smpl.event.IEntityDeathEventListener;
import org.astemir.smpl.graphics.ItemModel;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.astemir.smpl.utils.RandomUtils;

public class Daybreaker extends ItemCustomSword implements IEntityDeathEventListener {

    public Daybreaker(ItemModel texture, String nameKey) {
        super(texture, nameKey);
    }


    @Override
    public boolean onAttackEntity(EntityDamageByEntityEvent e) {
        World world = e.getEntity().getWorld();
        for (int i = 0;i<10;i++) {
            world.spawnParticle(Particle.ENCHANTMENT_TABLE, e.getEntity().getLocation().getX()+ RandomUtils.randomFloat(-0.5f,0.5f), e.getEntity().getLocation().getY()+ 1f+RandomUtils.randomFloat(-0.5f,0.75f), e.getEntity().getLocation().getZ()+ RandomUtils.randomFloat(-0.5f,0.5f), 0, 0, 0, 0);
        }
        for (int i = 0;i<2;i++) {
            world.playSound(e.getEntity().getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1, 0.5f);
            world.playSound(e.getEntity().getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1, 1.25f);
        }
        return super.onAttackEntity(e);
    }

    @Override
    public void onEntityDeath(EntityDeathEvent e) {
        if (RandomUtils.doWithChance(25)){
            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(),new ItemStack(Material.GOLD_NUGGET,RandomUtils.randomInt(1,5)));
        }
    }

    @Override
    public ChatColor getNameColor() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public double getDamage(){
        return 7;
    }

}
