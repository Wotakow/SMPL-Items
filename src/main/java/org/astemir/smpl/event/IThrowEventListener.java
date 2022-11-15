package org.astemir.smpl.event;


import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public interface IThrowEventListener {

    public void onThrownHit(ProjectileHitEvent e);

    public void onThrow(ProjectileLaunchEvent e);
}
