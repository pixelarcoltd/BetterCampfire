package com.pixel4r.plugin.enderice.bettercampfire.listeners;

import com.pixel4r.plugin.enderice.bettercampfire.registries.items.CustomItems;
import io.papermc.paper.event.block.BlockBreakBlockEvent;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;


public final class ItemDrops implements Listener {
    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        if (event.getBlock().getType().equals(Material.TALL_GRASS) || event.getBlock().getType().equals(Material.GRASS)) {
            int rand = (int) ((Math.random() * (2)) + 1);
            if (rand == 0) return;
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), CustomItems.PLANT_FIBRE.add(rand));
        }
    }
}
