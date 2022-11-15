package org.astemir.smpl.item;

import org.astemir.smpl.graphics.ItemModel;
import org.astemir.smpl.graphics.ItemModels;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Items {


    private static List<Item> itemsRegistry = new ArrayList<>();

    public static final Item CHILLING_BLADE = registerItem(ChillingBlade.class,"chilling_blade", ItemModels.CHILLING_BLADE);
    public static final Item DAYBREAKER = registerItem(Daybreaker.class,"daybreaker", ItemModels.DAYBREAKER);
    public static final Item SCARLET_DAGGER = registerItem(ScarletDagger.class,"scarlet_dagger", ItemModels.VAMPIRE);
    public static final Item BLAZING_HATCHET = registerItem(BlazingHatchet.class,"blazing_hatchet", ItemModels.BLAZING_HATCHET);
    public static final Item MULTIPLEX_CROSSBOW = registerItem(MultiplexCrossbow.class,"multiplex_crossbow", ItemModels.MULTIPLEX_CROSSBOW);
    public static final Item WITHER_BLADE = registerItem(WitherBlade.class,"wither_blade", ItemModels.WITHER_BLADE);
    public static final Item WITHERSBANE = registerItem(WithersBane.class,"withersbane", ItemModels.WITHERSBANE);
    public static final Item RAGNAROK = registerItem(Ragnarok.class,"ragnarok", ItemModels.RAGNAROK);
    public static final Item SENTRYS_WRATH = registerItem(SentrysWrath.class,"sentrys_wrath", ItemModels.SENTRYS_WRATH);
    public static final Item NECROTIC_SHIELD = registerItem(NecroticShield.class,"necrotic_shield", ItemModels.NECROTIC_SHIELD);
    public static final Item RADIATION_SHIELD = registerItem(RadiationShield.class,"radiation_shield", ItemModels.RADIATION_SHIELD);
    public static final Item PRISMATIC_SHIELD = registerItem(PrismaticShield.class,"prismatic_shield", ItemModels.PRISMATIC_SHIELD);
    public static final Item HOLY_WRATH = registerItem(HolyWrath.class,"holy_wrath", ItemModels.HOLY_WRATH);
    public static final Item SACRIFICE_SWORD = registerItem(SacrificeSword.class,"sacrifice_sword", ItemModels.SACRIFICE_SWORD);
    public static final Item HEFTY_PICKAXE = registerItem(HeftyPickaxe.class,"hefty_pickaxe", ItemModels.HEFTY_PICKAXE);
    public static final Item FIRESTORM = registerItem(Firestorm.class,"firestorm", ItemModels.FIRESTORM);
    public static final Item VOLTAIC_TRIDENT = registerItem(VoltaicTrident.class,"voltaic_trident", ItemModels.VOLTAIC_TRIDENT);
    public static final Item TRAILBLAZER = registerItem(Trailblazer.class,"trailblazer", ItemModels.TRAILBLAZER);
    public static final Item INFERNAL_WING = registerItem(InfernalWing.class,"infernal_wing", ItemModels.INFERNAL_WING);
    public static final Item UNSTABLE_POWDER = registerItem(UnstablePowder.class,"unstable_powder", ItemModels.UNSTABLE_POWDER);


    public static Item registerItem(Class<? extends Item> itemClass, String key, ItemModel texture){
        try {
            Item item = itemClass.getConstructor(ItemModel.class,String.class).newInstance(texture,key);
            itemsRegistry.add(item);
            return item;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Item getItem(String id){
        for (Item i : itemsRegistry) {
            if (i.getNameKey().equals(id)){
                return i;
            }else
            if (i.getNameKey().contains(id)){
                return i;
            }
        }
        return null;
    }

    public static boolean isItem(ItemStack itemStack,Class<? extends Item> itemClass){
        if (itemStack != null) {
            Item compare = getItem(itemStack);
            if (compare != null) {
                if (itemClass.isInstance(compare)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Item getItem(ItemStack item){
        if (item == null){
            return null;
        }
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (!meta.hasCustomModelData()){
                return null;
            }
            for (Item i : itemsRegistry) {
                String id = Item.getStringTag(item,"custom_id");
                if (id != null){
                    if (i.getNameKey().equals(id)){
                        return i;
                    }
                }
            }
        }
        return null;
    }

    public static List<Item> getItemsRegistry() {
        return itemsRegistry;
    }
}
