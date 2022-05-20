package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;
import org.astemir.smpl.event.PlayerClickEvent;
import org.astemir.smpl.graphics.ItemModel;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

public abstract class ItemCustomSword extends Item{


    public ItemCustomSword(ItemModel texture, String nameKey) {
        super(texture, nameKey);
    }


    @Override
    public boolean onRightClick(PlayerClickEvent e) {
        return false;
    }

    @Override
    public boolean onLeftClick(PlayerClickEvent e) {
        return false;
    }

    public double getDamage(){
        return 3;
    }

    public double getAttackSpeed(){
        return -2.4;
    }

    @Override
    public ItemStack toItemStack() {
        ItemStack itemStack = super.toItemStack();
        ItemMeta meta = itemStack.getItemMeta();
        double damage = getDamage();
        double attackSpeed = getAttackSpeed();

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        TextComponent empty = new TextComponent();
        TranslatableComponent mainHand = new TranslatableComponent();
        mainHand.setColor(ChatColor.GRAY);
        mainHand.setItalic(false);
        mainHand.setTranslate("item.modifiers.mainhand");

        TextComponent damageText = new TextComponent(" "+Item.format.format((1+damage))+" ");
        damageText.setItalic(false);
        damageText.setColor(ChatColor.DARK_GREEN);

        TranslatableComponent damageTextText = new TranslatableComponent();
        damageTextText.setColor(ChatColor.DARK_GREEN);
        damageTextText.setItalic(false);
        damageTextText.setTranslate("attribute.name.generic.attack_damage");

        TextComponent attackSpeedText = new TextComponent(" "+Item.format.format(4+attackSpeed)+" ");
        attackSpeedText.setItalic(false);
        attackSpeedText.setColor(ChatColor.DARK_GREEN);

        TranslatableComponent attackSpeedTextText = new TranslatableComponent();
        attackSpeedTextText.setColor(ChatColor.DARK_GREEN);
        attackSpeedTextText.setItalic(false);
        attackSpeedTextText.setTranslate("attribute.name.generic.attack_speed");

        meta.setLoreComponents(Arrays.asList(
                new BaseComponent[]{empty},
                new BaseComponent[]{mainHand},
                new BaseComponent[]{damageText,damageTextText},
                new BaseComponent[]{attackSpeedText,attackSpeedTextText}));

        itemStack.setItemMeta(meta);
        net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        nmsItemStack.addAttributeModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF"), "Weapon modifier", damage, AttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
        nmsItemStack.addAttributeModifier(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3"), "Weapon modifier", attackSpeed,AttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }
}
