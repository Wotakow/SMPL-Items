package org.astemir.smpl.utils;

import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.astemir.smpl.SMPLItems;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

public class EntityUtils {


    public static void hideEntity(Entity entity){
        for (org.bukkit.entity.Player player : entity.getWorld().getPlayers()) {
            ClientboundRemoveEntitiesPacket packet = new ClientboundRemoveEntitiesPacket(entity.getEntityId());
            ((CraftPlayer)player).getHandle().connection.send(packet);
        }
    }

    public static void hideEntities(Entity[] entity){
        int ids[] = new int[entity.length];
        for (int i = 0; i < entity.length; i++) {
            ids[i] = entity[i].getEntityId();
        }
        for (org.bukkit.entity.Player player : entity[0].getWorld().getPlayers()) {
            ClientboundRemoveEntitiesPacket packet = new ClientboundRemoveEntitiesPacket(ids);
            ((CraftPlayer)player).getHandle().connection.send(packet);
        }
    }

    public static void explodeFirework(Location loc, int power,FireworkEffect... effects){
        Firework firework = (Firework)loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta meta = firework.getFireworkMeta();
        meta.setPower(power);
        meta.addEffects(effects);
        firework.setFireworkMeta(meta);
        firework.detonate();
    }

    public static org.bukkit.entity.AbstractArrow shootArrow(Entity shooter, ItemStack stack, float accuracy, float power, float scatter, boolean crossbow){
        Level level = ((CraftEntity)shooter).getHandle().getLevel();
        net.minecraft.world.item.ItemStack serverItemStack = CraftItemStack.asNMSCopy(stack);
        ArrowItem itemArrow = (ArrowItem)(serverItemStack.getItem() instanceof ArrowItem ? serverItemStack.getItem() : Items.ARROW);
        LivingEntity shooterLiving = (LivingEntity) ((CraftEntity)shooter).getHandle();
        AbstractArrow entityArrow = itemArrow.createArrow(level, serverItemStack, shooterLiving);
        if (shooterLiving instanceof Player) {
            entityArrow.setCritArrow(true);
        }
        if (crossbow) {
            entityArrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
            entityArrow.setShotFromCrossbow(true);
            int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, serverItemStack);
            if (i > 0) {
                entityArrow.setPierceLevel((byte) i);
            }
            entityArrow.shootFromRotation(shooterLiving, shooterLiving.getXRot(), shooterLiving.getYRot(), scatter, power * 3.0F, scatter);
        }else{
            int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, serverItemStack);
            if (k > 0) {
                entityArrow.setBaseDamage(entityArrow.getBaseDamage() + (double)k * 0.5D + 0.5D);
            }

            int l = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, serverItemStack);
            if (l > 0) {
                entityArrow.setKnockback(l);
            }

            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, serverItemStack) > 0) {
                entityArrow.setSecondsOnFire(100);
            }
            entityArrow.shootFromRotation(shooterLiving, shooterLiving.getXRot(), shooterLiving.getYRot(), scatter, power * 3.0F, accuracy);
        }
        level.addFreshEntity(entityArrow);
        return (org.bukkit.entity.AbstractArrow) entityArrow.getBukkitEntity();
    }

    public static Firework shootFirework(Entity shooter, ItemStack stack, float power, float accuracy){
        Level level = ((CraftEntity)shooter).getHandle().getLevel();
        net.minecraft.world.item.ItemStack serverItemStack = CraftItemStack.asNMSCopy(stack);
        LivingEntity shooterLiving = (LivingEntity) ((CraftEntity)shooter).getHandle();
        FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(level, serverItemStack, shooterLiving, shooterLiving.getX(), shooterLiving.getEyeY() - 0.15000000596046448D, shooterLiving.getZ(), true);
        fireworkRocketEntity.shootFromRotation(shooterLiving, shooterLiving.getXRot(), shooterLiving.getYRot(), accuracy, power * 1.6F, accuracy);
        level.addFreshEntity(fireworkRocketEntity);
        return (Firework) fireworkRocketEntity.getBukkitEntity();
    }
}
