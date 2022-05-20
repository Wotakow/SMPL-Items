package org.astemir.smpl.event;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerClickEvent extends PlayerInteractEvent {


    private Entity clickedEntity;
    private Vector clickedPosition;

    public PlayerClickEvent(@NotNull Player who, @NotNull Action action, @Nullable ItemStack item, @Nullable Block clickedBlock, @NotNull BlockFace clickedFace,@Nullable Entity clickedEntity,@Nullable Vector clickedPosition) {
        super(who, action, item, clickedBlock, clickedFace);
        this.clickedEntity = clickedEntity;
        this.clickedPosition = clickedPosition;
    }

    public Vector getClickedPosition(){
        return clickedPosition;
    }


    public Entity getClickedEntity(){
        return clickedEntity;
    }

    public boolean isClickedOnEntity(){
        return clickedEntity != null;
    }
}
