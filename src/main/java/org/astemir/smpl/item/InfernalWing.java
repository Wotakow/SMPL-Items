package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.world.entity.EquipmentSlot;
import org.astemir.smpl.event.IHurtByEntityEventListener;
import org.astemir.smpl.event.ITickEventListener;
import org.astemir.smpl.event.PlayerTickEvent;
import org.astemir.smpl.graphics.ItemModel;
import org.astemir.smpl.utils.RandomUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class InfernalWing extends ItemCustomArmor implements ITickEventListener {

    public InfernalWing(ItemModel texture, String nameKey) {
        super(texture, nameKey);
    }

    @Override
    public ChatColor getNameColor() {
        return ChatColor.LIGHT_PURPLE;
    }

    @Override
    public int getDefense() {
        return 4;
    }

    @Override
    public double getToughness() {
        return 2;
    }

    @Override
    public double getKnockbackResistance() {
        return 1;
    }

    @Nullable
    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.CHEST;
    }

    @Override
    public ItemStack toItemStack() {
        ItemStack stack = super.toItemStack();
        stack.addUnsafeEnchantment(Enchantment.DURABILITY,4);
        return stack;
    }

    @Override
    public void onTick(PlayerTickEvent e) {
        if (e.getPlayer().isGliding()) {
            e.getPlayer().setVelocity(e.getPlayer().getVelocity().multiply(0.99f));
        }
        if (e.getTick() % 10 == 0){
            Location loc = e.getPlayer().getLocation();
            for (int i = 0;i<10;i++) {
                Color[] colors = new Color[]{Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.BLUE,Color.AQUA,Color.PURPLE};
                loc.getWorld().spawnParticle(Particle.REDSTONE, loc.clone().add(RandomUtils.randomFloat(-1f, 1f), 1.75f+RandomUtils.randomFloat(-1.5f,-0.25f), RandomUtils.randomFloat(-1f, 1f)), 0, 0, 0, 0,new Particle.DustOptions(colors[RandomUtils.randomInt(0,colors.length)],RandomUtils.randomFloat(0.5f,2f)));
            }
            loc.getWorld().spawnParticle(Particle.END_ROD, loc.clone().add(RandomUtils.randomFloat(-1f, 1f), 1.75f+RandomUtils.randomFloat(-1.5f,-0.25f), RandomUtils.randomFloat(-1f, 1f)), 0, 0, 0, 0);
        }
    }
}
