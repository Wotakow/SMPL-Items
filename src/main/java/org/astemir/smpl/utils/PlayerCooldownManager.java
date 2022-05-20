package org.astemir.smpl.utils;

import org.bukkit.entity.Player;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerCooldownManager {

    private CopyOnWriteArrayList<PlayerCooldown> cooldowns = new CopyOnWriteArrayList<>();

    public void update(){
        for (PlayerCooldown cooldown : cooldowns) {
            if (cooldown.getTime() <= 0){
                cooldowns.remove(cooldown);
            }else{
                cooldown.update();
            }
        }
    }

    public void setCooldown(Player player,String name,int time){
        if (time > 0) {
            PlayerCooldown cooldown = getCooldown(player, name);
            if (cooldown == null) {
                cooldowns.add(new PlayerCooldown(player, name, time));
            } else {
                cooldowns.remove(cooldown);
                cooldown.setTime(time);
                cooldowns.add(cooldown);
            }
        }
    }

    public boolean hasCooldown(Player player,String name){
        if (getCooldown(player,name) == null){
            return false;
        }
        return true;
    }

    public int getCooldownInTicks(Player player,String name){
        PlayerCooldown cooldown = getCooldown(player,name);
        if (cooldown != null){
            return cooldown.getTime();
        }
        return 0;
    }


    private PlayerCooldown getCooldown(Player player,String name){
        for (PlayerCooldown cooldown : cooldowns) {
            if (cooldown.getPlayerUUID().equals(player.getUniqueId())){
                if (cooldown.getName().equals(name)){
                    return cooldown;
                }
            }
        }
        return null;
    }

    private class PlayerCooldown
    {
        private UUID playerUUID;
        private String name;
        private int time = 0;

        public PlayerCooldown(Player player,String name,int time) {
            this.playerUUID = player.getUniqueId();
            this.name = name;
            this.time = time;
        }

        public void update(){
            if (time > 0){
                time--;
            }
        }

        public UUID getPlayerUUID() {
            return playerUUID;
        }


        public String getName() {
            return name;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }
    }
}
