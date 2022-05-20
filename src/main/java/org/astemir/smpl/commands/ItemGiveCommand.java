package org.astemir.smpl.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.astemir.smpl.item.Item;
import org.astemir.smpl.item.Items;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;


public class ItemGiveCommand implements CommandExecutor {

    private String HELP =
            "§e/itemgive [item name] - выдать себе предмет\n"+
            "§e/itemgive [item name] nbt - выдать себе нбт предмета\n"+
            "§e/itemgive list - меню с предметами\n";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (args.length > 0) {
                String id = args[0];
                if (!id.equals("list")) {
                    Item item = Items.getItem(id);
                    if (item != null) {
                        if (args.length < 2) {
                            ((Player) sender).getInventory().addItem(item.toItemStack());
                        } else {
                            if (args[1].equals("nbt")) {
                                String tagText = Item.getTag(CraftItemStack.asNMSCopy(item.toItemStack())).toString();
                                Component component = Component.text("§aNBT предмета: ").append(Component.translatable(item.getNameKey()));
                                component = component.clickEvent(ClickEvent.copyToClipboard(tagText));
                                component = component.hoverEvent(HoverEvent.showText(Component.text(tagText)));
                                sender.sendMessage(component);
                            }
                        }
                    }
                }else{
                    Inventory[] allItems = allItems(27);
                    if (args.length > 1) {
                        int listId = Integer.parseInt(args[1]);
                        if (listId < allItems.length) {
                            ((Player) sender).openInventory(allItems[listId]);
                        } else {
                            ((Player) sender).sendMessage("§cОшибка, возможное число страницы 0 - " + (allItems.length - 1));
                        }
                    }else{
                        ((Player) sender).sendMessage("§cОшибка, возможное число страницы 0 - " + (allItems.length - 1));
                    }
                }
            }else{
                sender.sendMessage(HELP);
            }
            return true;
        }
        return false;
    }

    private Inventory[] allItems(int maxItems){
        int size = Items.getItemsRegistry().size()/maxItems;
        if (size == 0){
            size = 1;
        }
        Inventory[] inventories = new Inventory[size];
        int inventoryId = -1;
        int slotId = 0;
        for (int i = 0; i < Items.getItemsRegistry().size(); i++) {
            if (i % maxItems == 0){
                inventoryId++;
                inventories[inventoryId] = Bukkit.createInventory(null,27,"§lСтраница "+inventoryId+"/"+(size-1));
                slotId = 0;
            }
            inventories[inventoryId].setItem(slotId,Items.getItemsRegistry().get(i).toItemStack());
            slotId++;
        }
        return inventories;
    }
}
