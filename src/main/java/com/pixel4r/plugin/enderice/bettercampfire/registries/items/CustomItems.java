package com.pixel4r.plugin.enderice.bettercampfire.registries.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;


public class CustomItems {

    public static ItemStack PLANT_FIBRE = plantFibre();

    public CustomItems() {}

    public static ItemStack plantFibre() {
        ItemStack plantFibre = new ItemStack(Material.FLINT);
        ItemMeta itemMeta = plantFibre.getItemMeta();
        itemMeta.setCustomModelData(1);
        itemMeta.displayName(Component.text("Plant Fibre", nonItalic(TextColor.color(255, 255, 255))));
        plantFibre.setItemMeta(itemMeta);

        return plantFibre;
    };

    private static @NotNull Style nonItalic(@NotNull TextColor color) {
        return Style.style().color(color).decoration(TextDecoration.ITALIC, false).build();
    }
}
