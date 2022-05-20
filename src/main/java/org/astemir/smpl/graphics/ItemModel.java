package org.astemir.smpl.graphics;

import org.bukkit.Material;

public class ItemModel {

    private Material material;
    private int customModelData;

    public ItemModel(Material material, int customModelData) {
        this.material = material;
        this.customModelData = customModelData;
    }

    public Material getMaterial(){
        return material;
    }

    public int getCustomModelData(){
        return customModelData;
    }
}
