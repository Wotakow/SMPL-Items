package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import org.astemir.smpl.event.IBreakBlockEventListener;
import org.astemir.smpl.graphics.ItemModel;
import org.astemir.smpl.utils.RandomUtils;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class HeftyPickaxe extends ItemCustomSword implements IBreakBlockEventListener {

    public HeftyPickaxe(ItemModel texture, String nameKey) {
        super(texture, nameKey);
    }




    @Override
    public ChatColor getNameColor() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public double getDamage(){
        return 5;
    }

    @Override
    public double getAttackSpeed() {
        return -2.8;
    }

    private static void spawnNaturalBlockParticle(Location loc, Material material , int amount){
        loc.getWorld().playEffect(loc,Effect.STEP_SOUND,material,amount);
    }

    @Override
    public void onBreakBlock(BlockBreakEvent e) {
        Location loc = e.getBlock().getLocation();
        for (int i = -1;i<1;i++){
            for (int j = -1;j<1;j++){
                for (int k = -1;k<1;k++){
                    if (RandomUtils.doWithChance(25)) {
                        Location newLoc = loc.clone().add(i, j, k);
                        if (!(newLoc.getBlockX() == loc.getBlockX() && newLoc.getBlockY() == loc.getBlockY() && newLoc.getBlockZ() == loc.getBlockZ())) {
                            Collection<ItemStack> drops = newLoc.getBlock().getDrops(e.getPlayer().getInventory().getItemInMainHand());
                            if (!drops.isEmpty()) {
                                spawnNaturalBlockParticle(loc, newLoc.getBlock().getType(), 10);
                                newLoc.getBlock().setType(Material.AIR);
                                if (e.isDropItems()) {
                                    for (ItemStack item : drops) {
                                        newLoc.getWorld().dropItem(newLoc, item);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

