package org.astemir.smpl.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.function.Predicate;

public class BeamUtils {

    public static TraceResult sendDamageableBeam(Particle particle, Location begin, Vector direction, int distance, Predicate<LivingEntity> predicate, boolean ghost){
        boolean meetHardBlock = false;
        for (double i = 0;i<distance;i+=0.25f){
            Location point = begin.clone().add(direction.clone().normalize().multiply(i));
            if (Float.isFinite((float)point.getX()) && Float.isFinite((float)point.getY()) && Float.isFinite((float)point.getZ())) {
                if (point.getBlock().isSolid() && !ghost){
                    return new TraceResult(point,true);
                }
                point.getWorld().spawnParticle(particle, point, 0);
                point.getNearbyLivingEntities(0.25f, 0.25f, 0.25f, (entity) -> {
                    predicate.test(entity);
                    return true;
                });
            }
        }
        return new TraceResult(begin.clone().add(direction.clone().normalize().multiply(distance)),false);
    }

    public static TraceResult sendBeam(Particle particle, Location begin, Vector direction, int distance,boolean ghost){
        boolean meetHardBlock = false;
        for (double i = 0;i<distance;i+=0.25f){
            Location point = begin.clone().add(direction.clone().normalize().multiply(i));
            if (Float.isFinite((float)point.getX()) && Float.isFinite((float)point.getY()) && Float.isFinite((float)point.getZ())) {
                if (point.getBlock().isSolid() && !ghost){
                    return new TraceResult(point,true);
                }
                point.getWorld().spawnParticle(particle, point, 0);
            }
        }
        return new TraceResult(begin.clone().add(direction.clone().normalize().multiply(distance)),false);
    }

    public static TraceResult sendParticleLightning(Particle particle, Location begin, Vector direction, int distance, Predicate<LivingEntity> predicate,int segmentsCount,float offset,boolean ghost){
        Location loc = begin.clone();
        for (int i = 0;i<segmentsCount;i++){
            TraceResult result = sendDamageableBeam(particle,loc,direction.clone().add(new Vector(RandomUtils.randomFloat(-offset,offset),RandomUtils.randomFloat(-offset,offset),RandomUtils.randomFloat(-offset,offset))),distance,predicate,ghost);
            if (result.isMeetHardBlock()){
                return result;
            }else{
                loc = result.getLoc();
            }
        }
        return new TraceResult(loc,false);
    }

    public static class TraceResult{

        private Location loc;
        private boolean meetHardBlock = false;

        public TraceResult(Location loc, boolean meetHardBlock) {
            this.loc = loc;
            this.meetHardBlock = meetHardBlock;
        }

        public Location getLoc() {
            return loc;
        }

        public boolean isMeetHardBlock() {
            return meetHardBlock;
        }
    }
}
