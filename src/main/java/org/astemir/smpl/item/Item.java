package org.astemir.smpl.item;

import io.papermc.paper.inventory.ItemRarity;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.astemir.smpl.event.PlayerClickEvent;
import org.astemir.smpl.graphics.ItemModel;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class Item {

    public static final DecimalFormat format = new DecimalFormat("###.#",new DecimalFormatSymbols(Locale.ENGLISH));

    private ItemModel texture;
    private String nameKey;

    public Item(ItemModel texture, String nameKey){
        this.texture = texture;
        this.nameKey = nameKey;
    }

    public boolean onRightClick(PlayerClickEvent e){
        return false;
    }

    public boolean onLeftClick(PlayerClickEvent e){
        return false;
    }

    public boolean onDrop(PlayerDropItemEvent e){
        return false;
    }

    public boolean onAttackEntity(EntityDamageByEntityEvent e){
        return false;
    }

    public boolean onEntityClick(PlayerInteractAtEntityEvent e){
        return false;
    }

    public boolean onConsume(PlayerItemConsumeEvent e){
        return false;
    }

    public boolean onMoveToAnotherInventory(InventoryClickEvent e){
        return false;
    }

    public boolean onSpawnInWorldAsEntity(EntitySpawnEvent e){
        return false;
    }

    public static ItemStack changeTexture(ItemStack itemStack, ItemModel texture){
        if (itemStack.getItemMeta() != null){
            itemStack.setType(texture.getMaterial());
            ItemMeta meta = itemStack.getItemMeta();
            meta.setCustomModelData(texture.getCustomModelData());
            itemStack.setItemMeta(meta);
        }
        return itemStack;
    }

    public List<Component> getLore(){
        return null;
    }

    public ItemModel getTexture(){
        return texture;
    }

    public String getNameKey(){
        return nameKey;
    }

    public ChatColor getNameColor(){
        return ChatColor.WHITE;
    }

    public ItemStack toItemStack(){
        ItemStack itemStack = new ItemStack(texture.getMaterial());
        ItemMeta meta = itemStack.getItemMeta();
        TranslatableComponent component = new TranslatableComponent();
        component.setItalic(false);
        component.setColor(getNameColor());
        component.setTranslate("item.smpl."+nameKey);
        meta.setDisplayNameComponent(new BaseComponent[]{component});
        meta.setCustomModelData(texture.getCustomModelData());
        if (getLore() != null) {
            meta.lore(getLore());
        }
        itemStack.setItemMeta(meta);
        itemStack = setStringTag(itemStack,"custom_id",nameKey);
        return itemStack;
    }

    public static ItemStack setIntTag(ItemStack itemStack, String key, int value){
        net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        CompoundTag tagCompound = getTag(nmsItemStack);
        tagCompound.putInt(key,value);
        nmsItemStack.setTag(tagCompound);
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    public static ItemStack setDoubleTag(ItemStack itemStack,String key,double value){
        net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        CompoundTag tagCompound = getTag(nmsItemStack);
        tagCompound.putDouble(key,value);
        nmsItemStack.setTag(tagCompound);
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    public static ItemStack setStringTag(ItemStack itemStack,String key,String value){
        net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        CompoundTag tagCompound = getTag(nmsItemStack);
        tagCompound.putString(key,value);
        nmsItemStack.setTag(tagCompound);
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    public static ItemStack setTag(ItemStack itemStack, String key, Tag tag){
        net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        CompoundTag tagCompound = getTag(nmsItemStack);
        tagCompound.put(key,tag);
        nmsItemStack.setTag(tagCompound);
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    public static ItemStack removeTag(ItemStack itemStack, String key){
        net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        CompoundTag tagCompound = getTag(nmsItemStack);
        tagCompound.remove(key);
        nmsItemStack.setTag(tagCompound);
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }

    public static boolean hasTag(ItemStack itemStack, String key){
        net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        CompoundTag tagCompound = getTag(nmsItemStack);
        return tagCompound.contains(key);
    }

    public static String getStringTag(ItemStack itemStack,String key){
        net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        CompoundTag tagCompound = getTag(nmsItemStack);
        if (tagCompound != null){
            if (tagCompound.getString(key) != null){
                return tagCompound.getString(key);
            }
        }
        return null;
    }

    public static CompoundTag getTag(net.minecraft.world.item.ItemStack stack){
        if (stack.getTag() == null){
            return new CompoundTag();
        }else{
            return stack.getTag();
        }
    }

    public static int getIntTag(ItemStack itemStack,String key){

        net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);

        CompoundTag tagCompound = getTag(nmsItemStack);
        if (tagCompound != null){
            if (tagCompound.getString(key) != null){
                return tagCompound.getInt(key);
            }
        }
        return 0;
    }

    public static double getDoubleTag(ItemStack itemStack,String key){
        net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        CompoundTag tagCompound = getTag(nmsItemStack);
        if (tagCompound != null){
            if (tagCompound.getString(key) != null){
                return tagCompound.getDouble(key);
            }
        }
        return 0;
    }

    public static Tag getTag(ItemStack itemStack,String key){
        net.minecraft.world.item.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        CompoundTag tagCompound = getTag(nmsItemStack);
        if (tagCompound != null){
            if (tagCompound.getString(key) != null){
                return tagCompound.get(key);
            }
        }
        return null;
    }
}
