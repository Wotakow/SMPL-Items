package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import org.astemir.smpl.graphics.ItemModel;
import org.astemir.smpl.utils.BeamUtils;
import org.astemir.smpl.utils.PlayerUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityCategory;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class WithersBane extends ItemCustomSword{

    public WithersBane(ItemModel texture, String nameKey) {
        super(texture, nameKey);
    }


    @Override
    public boolean onAttackEntity(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Monster){
            if (!PlayerUtils.hasCooldown((Player)e.getDamager(),"withersbane")){
                PlayerUtils.cooldown((Player) e.getDamager(), "withersbane", 20);
                int maxTargets = 6;
                int i = 0;
                if (((Monster) e.getEntity()).getCategory() == EntityCategory.UNDEAD) {
                    for (Entity livingEntity : e.getEntity().getNearbyEntities(10, 10, 10)) {
                        if (livingEntity instanceof Monster) {
                            if (i < maxTargets) {
                                Monster monster = (Monster) livingEntity;
                                if (monster.getCategory() == EntityCategory.UNDEAD) {
                                    Location startLoc = e.getEntity().getLocation().clone().add(new Vector(0, 1, 0));
                                    Location monsterLoc = monster.getLocation().clone().add(new Vector(0, 1, 0));
                                    Vector dir = monsterLoc.toVector().add(startLoc.toVector().multiply(-1)).normalize();
                                    int distance = (int) startLoc.distance(monsterLoc) + 1;
                                    BeamUtils.sendDamageableBeam(Particle.END_ROD, startLoc, dir, distance, (ent) -> {
                                        if (ent instanceof Monster) {
                                            if (ent.getCategory() == EntityCategory.UNDEAD) {
                                                ent.damage(4, e.getDamager());
                                                return true;
                                            }
                                        }
                                        return false;
                                    }, false);
                                    BeamUtils.sendBeam(Particle.CLOUD, startLoc, dir, distance, false);
                                    i++;
                                }
                            }
                        }
                    }
                }
            }
        }
        return super.onAttackEntity(e);
    }

    @Override
    public ItemStack toItemStack() {
        ItemStack stack = super.toItemStack();
        stack.addUnsafeEnchantment(Enchantment.DURABILITY,4);
        return stack;
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

