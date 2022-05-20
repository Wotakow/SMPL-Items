package org.astemir.smpl.event;


import org.astemir.smpl.item.Item;
import org.astemir.smpl.item.Items;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Horse;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class EventListener implements Listener {


    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        ItemStack itemStack = e.getItem();
        Item item = Items.getItem(itemStack);
        if (item != null){
            PlayerClickEvent clickEvent = new PlayerClickEvent(e.getPlayer(),e.getAction(),e.getItem(),e.getClickedBlock(),e.getBlockFace(),null,null);
            boolean cancel = false;
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
                cancel = item.onRightClick(clickEvent);
            }else
            if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
                cancel = item.onLeftClick(clickEvent);
            }
            e.setCancelled(cancel);
        }
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent e){
        ItemStack itemStack = e.getBow();
        Item item = Items.getItem(itemStack);
        if (item != null){
            if (item instanceof IShotEventListener) {
                ((IShotEventListener)item).onShoot(e);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        ItemStack itemStack = e.getPlayer().getInventory().getItemInMainHand();
        Item item = Items.getItem(itemStack);
        if (item != null){
            if (item instanceof IBreakBlockEventListener) {
                ((IBreakBlockEventListener)item).onBreakBlock(e);
            }
        }
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent e){
        if (e.getEntity() instanceof org.bukkit.entity.Item) {
            ItemStack itemStack = ((org.bukkit.entity.Item) e.getEntity()).getItemStack();
            Item item = Items.getItem(itemStack);
            if (item != null) {
                e.setCancelled(item.onSpawnInWorldAsEntity(e));
            }
        }
    }

    @EventHandler
    public void onMoveItemToInventory(InventoryClickEvent e){
        ItemStack itemStack = e.getCursor();
        Item item = Items.getItem(itemStack);
        if (item != null) {
            e.setCancelled(item.onMoveToAnotherInventory(e));
        }else{
            ItemStack itemStackB = e.getCurrentItem();
            Item itemB = Items.getItem(itemStackB);
            if (itemB != null){
                e.setCancelled(itemB.onMoveToAnotherInventory(e));
            }
        }
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e){
        ItemStack itemStack = e.getItem();
        Item item = Items.getItem(itemStack);
        if (item != null){
            if (item.onConsume(e)) {
                e.setCancelled(true);
            }else{
                e.setCancelled(false);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof Player) {
            ItemStack itemStack = ((Player)e.getEntity()).getItemInHand();
            Item item = Items.getItem(itemStack);
            if (item != null) {
                if (item instanceof IHurtByEntityEventListener){
                    ((IHurtByEntityEventListener)item).onHurt(e);
                }
            }
        }

        if (e.getDamager() instanceof Player) {
            ItemStack itemStack = ((Player)e.getDamager()).getItemInHand();
            Item item = Items.getItem(itemStack);
            if (item != null) {
                if (item.onAttackEntity(e)) {
                    e.setCancelled(true);
                } else {
                    e.setCancelled(false);
                }
            }
        }

        for (Item item : Items.getItemsRegistry()) {
            if (item instanceof IEntityDamageEntityEventListener){
                ((IEntityDamageEntityEventListener)item).onEntityDamageByEntity(e);
            }
        }
    }

    @EventHandler
    public void onHurt(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            PlayerHurtEvent hurtEvent = new PlayerHurtEvent((Player)e.getEntity(),e.getCause(),e.getDamage());
            ItemStack itemStack = hurtEvent.getPlayer().getItemInHand();
            if (itemStack != null) {
                Item item = Items.getItem(itemStack);
                if (item != null) {
                    if (item instanceof IQuitEventListener){
                        ((IHurtEventListener)item).onHurt(hurtEvent);
                    }
                }
            }
        }
    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().getKiller() instanceof Player) {
            ItemStack itemStack = e.getEntity().getKiller().getPlayer().getItemInHand();
            if (itemStack != null) {
                Item item = Items.getItem(itemStack);
                if (item != null) {
                    if (item instanceof IEntityDeathEventListener){
                        ((IEntityDeathEventListener)item).onEntityDeath(e);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        ItemStack itemStack = e.getItemDrop().getItemStack();
        Item item = Items.getItem(itemStack);
        if (item != null){
            if (item.onDrop(e)) {
                e.setCancelled(true);
            }else{
                e.setCancelled(false);
            }
        }
    }

    @EventHandler
    public void onInteractWithEntity(PlayerInteractAtEntityEvent e){
        ItemStack itemStack = e.getPlayer().getItemInHand();
        Item item = Items.getItem(itemStack);
        if (item != null){
            e.setCancelled(item.onEntityClick(e));
        }
    }

    @EventHandler
    public void onInteractDirectlyWithEntity(PlayerInteractEntityEvent e){
        ItemStack itemStack = e.getPlayer().getItemInHand();
        Item item = Items.getItem(itemStack);
        if (item != null){
            PlayerClickEvent clickEvent = new PlayerClickEvent(e.getPlayer(),Action.RIGHT_CLICK_AIR,itemStack,e.getRightClicked().getLocation().getBlock(), BlockFace.DOWN,e.getRightClicked(),null);
            item.onRightClick(clickEvent);
            if (e.getRightClicked() instanceof Horse) {

            }
            if (e.getRightClicked() instanceof ItemFrame) {

            }
        }
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        ItemStack itemStack = player.getItemInHand();
        if (itemStack != null) {
            Item item = Items.getItem(itemStack);
            if (item != null) {
                if (item instanceof IJoinEventListener){
                    ((IJoinEventListener)item).onJoin(e);
                }
            }
        }
    }

    @EventHandler
    public void onExit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        ItemStack itemStack = player.getItemInHand();
        if (itemStack != null) {
            Item item = Items.getItem(itemStack);
            if (item != null) {
                if (item instanceof IQuitEventListener){
                    ((IQuitEventListener)item).onQuit(e);
                }
            }
        }
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e){
        Player player = e.getPlayer();
        ItemStack itemStack = player.getItemInHand();
        if (itemStack != null) {
            Item item = Items.getItem(itemStack);
            if (item != null) {
                if (item instanceof ISneakListener){
                    ((ISneakListener)item).onSneak(e);
                }
            }
        }
    }

    @EventHandler
    public void onSprint(PlayerToggleSprintEvent e){
        Player player = e.getPlayer();
        ItemStack itemStack = player.getItemInHand();
        if (itemStack != null) {
            Item item = Items.getItem(itemStack);
            if (item != null) {
                if (item instanceof ISprintEventListener){
                    ((ISprintEventListener)item).onSprint(e);
                }
            }
        }
    }


}
