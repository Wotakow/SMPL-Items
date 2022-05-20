package org.astemir.smpl.event;

import com.google.common.base.Function;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class PlayerHurtEvent extends EntityDamageEvent {


    private Player player;

    public PlayerHurtEvent(Player player, @NotNull DamageCause cause, double damage) {
        super(player, cause, damage);
        this.player = player;
    }

    public PlayerHurtEvent(Player player, @NotNull DamageCause cause, @NotNull Map<DamageModifier, Double> modifiers, @NotNull Map<DamageModifier, ? extends Function<? super Double, Double>> modifierFunctions) {
        super(player, cause, modifiers, modifierFunctions);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
