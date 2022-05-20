package org.astemir.smpl.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;

public class WorldUtils {


    public static void breakBlockToEntity(World world,double x,double y,double z){
        Location loc = new Location(world,x,y,z);
        Block block = world.getBlockAt(loc);
        if (!block.isLiquid()) {
            FallingBlock fallingBlock = world.spawnFallingBlock(loc, block.getBlockData());
            fallingBlock.setFallDistance(1);
            fallingBlock.setTicksLived(1);
            fallingBlock.setDropItem(true);
            world.getBlockAt(loc).setType(Material.AIR);
        }
    }
}
