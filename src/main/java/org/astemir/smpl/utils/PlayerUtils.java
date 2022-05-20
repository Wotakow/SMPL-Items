package org.astemir.smpl.utils;

import org.astemir.smpl.SMPLItems;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class PlayerUtils {

    public static Location getPlayerEyeLocation(Player player){
        return player.getEyeLocation().clone().add(0,- 0.10000000149011612D,0);
    }

    public static void cooldown(Player player,String name,int ticks){
        if (!hasCooldown(player,name)) {
            cooldownForce(player, name, ticks);
        }
    }

    public static void cooldownForce(Player player,String name,int ticks){
        SMPLItems.getPlugin().getCooldownManager().setCooldown(player, name, ticks);
    }

    public static boolean hasCooldown(Player player,String name){
        return SMPLItems.getPlugin().getCooldownManager().hasCooldown(player,name);
    }

    public static Vector getPlayerDirection(Player player){
        float rotationYaw = player.getLocation().getYaw(), rotationPitch = player.getLocation().getPitch();
        float vx = (float) ((float)-Math.sin(Math.toRadians(rotationYaw)) * Math.cos(Math.toRadians(rotationPitch)));
        float vz = (float) ((float)Math.cos(Math.toRadians(rotationYaw)) * Math.cos(Math.toRadians(rotationPitch)));
        float vy = (float)- Math.sin(Math.toRadians(rotationPitch));
        return new Vector(vx,vy,vz);
    }

    public static void dropItem(Player player,ItemStack itemStack){
        Item item = (Item)player.getWorld().spawnEntity(player.getLocation().clone().add(0,0.5f,0), EntityType.DROPPED_ITEM);
        item.setItemStack(itemStack);
    }

    public static ItemStack[] getHotbarItems(Player player){
        ItemStack[] res = new ItemStack[9];
        for (int i = 0;i<res.length;i++){
            ItemStack itemStack = player.getInventory().getItem(i);
            if (itemStack == null){
                res[i] = null;
            }else
            if (itemStack.getType().isAir()){
                res[i] = null;
            }else {
                res[i] = itemStack;
            }
        }
        return res;
    }

}
