package org.astemir.smpl.event;


import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

public interface IShotEventListener {


    public void onShoot(EntityShootBowEvent e);
}
