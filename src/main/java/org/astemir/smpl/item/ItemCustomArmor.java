package org.astemir.smpl.item;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.FireworkRocketItem;
import org.astemir.smpl.event.PlayerClickEvent;
import org.astemir.smpl.graphics.ItemModel;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class ItemCustomArmor extends Item{

    private static final UUID[] ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};


    public ItemCustomArmor(ItemModel texture, String nameKey) {
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

    public int getDefense(){
        return 2;
    }

    public double getToughness(){
        return 0;
    }

    public double getKnockbackResistance(){
        return 0;
    }

    public @Nullable EquipmentSlot getSlot(){
        return EquipmentSlot.CHEST;
    }


    @Override
    public ItemStack toItemStack() {
        ItemStack itemStack = super.toItemStack();
        ItemMeta meta = itemStack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        TextComponent empty = new TextComponent();
        TranslatableComponent mainHand = new TranslatableComponent();
        mainHand.setColor(ChatColor.GRAY);
        mainHand.setItalic(false);
        switch(getSlot()){
            case HEAD:{
                mainHand.setTranslate("item.modifiers.head");
                break;
            }
            case CHEST:{
                mainHand.setTranslate("item.modifiers.chest");
                break;
            }
            case LEGS:{
                mainHand.setTranslate("item.modifiers.legs");
                break;
            }
            case FEET:{
                mainHand.setTranslate("item.modifiers.feet");
                break;
            }
        }

        List<BaseComponent[]> lore = new ArrayList(Arrays.asList(new BaseComponent[]{empty}, new BaseComponent[]{mainHand}));

        if (getDefense() > 0) {
            TextComponent defenseText = new TextComponent(" " + Item.format.format((getDefense())) + " ");
            defenseText.setItalic(false);
            defenseText.setColor(ChatColor.BLUE);
            TranslatableComponent defenceTextText = new TranslatableComponent();
            defenceTextText.setColor(ChatColor.BLUE);
            defenceTextText.setItalic(false);
            defenceTextText.setTranslate("attribute.name.generic.armor");
            lore.add(new BaseComponent[]{defenseText,defenceTextText});
        }

        if (getToughness() > 0) {
            TextComponent toughnessText = new TextComponent(" " + Item.format.format(getToughness()) + " ");
            toughnessText.setItalic(false);
            toughnessText.setColor(ChatColor.BLUE);

            TranslatableComponent toughnessTextText = new TranslatableComponent();
            toughnessTextText.setColor(ChatColor.BLUE);
            toughnessTextText.setItalic(false);
            toughnessTextText.setTranslate("attribute.name.generic.armor_toughness");
            lore.add(new BaseComponent[]{toughnessText,toughnessTextText});
        }

        if (getKnockbackResistance() > 0) {
            TextComponent knockBackText = new TextComponent(" " + Item.format.format(getKnockbackResistance()) + " ");
            knockBackText.setItalic(false);
            knockBackText.setColor(ChatColor.BLUE);

            TranslatableComponent knockBackTextText = new TranslatableComponent();
            knockBackTextText.setColor(ChatColor.BLUE);
            knockBackTextText.setItalic(false);
            knockBackTextText.setTranslate("attribute.name.generic.knockback_resistance");
            lore.add(new BaseComponent[]{knockBackText,knockBackTextText});
        }

        meta.setLoreComponents(lore);

        itemStack.setItemMeta(meta);
        net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        UUID uuid = ARMOR_MODIFIER_UUID_PER_SLOT[getSlot().getIndex()];
        if (getDefense() > 0) {
            nmsItemStack.addAttributeModifier(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", getDefense(), AttributeModifier.Operation.ADDITION), getSlot());
        }
        if (getToughness() > 0) {
            nmsItemStack.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor modifier", getToughness(), AttributeModifier.Operation.ADDITION), getSlot());
        }
        if (getKnockbackResistance() > 0) {
            nmsItemStack.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor modifier", getKnockbackResistance(), AttributeModifier.Operation.ADDITION), getSlot());
        }
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }
}
