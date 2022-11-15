package org.astemir.smpl.graphics;

import org.astemir.smpl.utils.RandomUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ParticleEffect<T> {

    private Particle particle;
    private T particleData;
    private double speedX = 0;
    private double speedY = 0;
    private double speedZ = 0;
    private double sizeX = 0;
    private double sizeY = 0;
    private double sizeZ = 0;
    private double delta = 0;
    private int count = 0;
    private int renderTimes = 1;
    private boolean showFromDistance = false;
    private boolean randomSpeed = false;

    public ParticleEffect(Particle particle) {
        this.particle = particle;
    }

    public ParticleEffect speed(double x, double y, double z) {
        this.speedX = x;
        this.speedY = y;
        this.speedZ = z;
        return this;
    }

    public ParticleEffect size(double x, double y, double z) {
        this.sizeX = x;
        this.sizeY = y;
        this.sizeZ = z;
        return this;
    }

    public ParticleEffect count(int count) {
        this.count = count;
        return this;
    }

    public ParticleEffect renderTimes(int count) {
        this.renderTimes = count;
        return this;
    }

    public ParticleEffect delta(double delta) {
        this.delta = delta;
        return this;
    }

    public ParticleEffect item(Material material, int count) {
        this.particleData = (T) new ItemStack(material, count);
        return this;
    }

    public ParticleEffect block(Material material) {
        this.particleData = (T) material.createBlockData();
        return this;
    }


    public ParticleEffect distant() {
        this.showFromDistance = true;
        return this;
    }

    public ParticleEffect randomSpeed() {
        this.randomSpeed = true;
        return this;
    }


    public ParticleEffect color(Color color, float size) {
        this.particleData = (T) new Particle.DustOptions(color, size);
        return this;
    }

    public void render(Location loc) {
        for (int i = 0; i < renderTimes; i++) {
            double sX = this.speedX;
            double sY = this.speedY;
            double sZ = this.speedZ;
            if (randomSpeed){
                sX*=RandomUtils.randomFloat(-1,1);
                sY*=RandomUtils.randomFloat(-1,1);
                sZ*=RandomUtils.randomFloat(-1,1);
            }
            Vector
            if (count == 0) {
                if (delta != 0) {
                    loc.getWorld().spawnParticle(particle, loc.clone().add(RandomUtils.randomFloat(-(float) sizeX, (float) sizeX), RandomUtils.randomFloat(-(float) sizeY, (float) sizeY), RandomUtils.randomFloat(-(float) sizeZ, (float) sizeZ)), 0, sX, sY, sZ, delta, particleData, showFromDistance);
                }else{
                    if (particleData != null) {
                        loc.getWorld().spawnParticle(particle, loc.clone().add(RandomUtils.randomFloat(-(float) sizeX, (float) sizeX), RandomUtils.randomFloat(-(float) sizeY, (float) sizeY), RandomUtils.randomFloat(-(float) sizeZ, (float) sizeZ)), 0, sX, sY, sZ, particleData);
                    }else{
                        loc.getWorld().spawnParticle(particle, loc.clone().add(RandomUtils.randomFloat(-(float) sizeX, (float) sizeX), RandomUtils.randomFloat(-(float) sizeY, (float) sizeY), RandomUtils.randomFloat(-(float) sizeZ, (float) sizeZ)), 0, sX, sY, sZ);
                    }
                }
            } else {
                loc.getWorld().spawnParticle(particle, loc, count, sizeX, sizeY, sizeZ, delta, particleData, showFromDistance);
            }
        }
    }

}