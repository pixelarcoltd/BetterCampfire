package com.pixel4r.plugin.enderice.bettercampfire.registries.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomModel {

    public CustomModel() {

    }

    public static ItemStack getCampfirePitModel(int stage) {
        ItemStack campfirePit = new ItemStack(Material.FLINT);
        ItemMeta itemMeta = campfirePit.getItemMeta();
        itemMeta.setCustomModelData(stage);
        campfirePit.setItemMeta(itemMeta);
        return campfirePit;
    }

}
