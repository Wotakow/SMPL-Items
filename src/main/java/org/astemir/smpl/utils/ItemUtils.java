package org.astemir.smpl.utils;

import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {

    public static void damageItem(Player player, ItemStack item, int damage){
        net.minecraft.world.item.ItemStack itemstack = CraftItemStack.asNMSCopy(item);
        ServerPlayer entityHuman = ((CraftPlayer)player).getHandle();
        itemstack.hurtAndBreak(damage, entityHuman, (entityhuman1) -> {
            entityhuman1.broadcastBreakEvent(entityHuman.getUsedItemHand());
        });
    }

    public static boolean isWoodLog(Material material){
        switch (material){
            case ACACIA_LOG:
            case BIRCH_LOG:
            case JUNGLE_LOG:
            case DARK_OAK_LOG:
            case SPRUCE_LOG:
            case OAK_LOG:
            case STRIPPED_ACACIA_LOG:
            case STRIPPED_BIRCH_LOG:
            case STRIPPED_JUNGLE_LOG:
            case STRIPPED_DARK_OAK_LOG:
            case STRIPPED_SPRUCE_LOG:
            case STRIPPED_OAK_LOG:
                return true;
        }
        return false;
    }

}
