package org.astemir.smpl.event;


import org.bukkit.event.entity.EntityDamageByEntityEvent;

public interface IHurtByEntityEventListener {


    public void onHurt(EntityDamageByEntityEvent e);
}
