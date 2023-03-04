package com.pixel4r.plugin.enderice.bettercampfire;

import com.pixel4r.plugin.enderice.bettercampfire.listeners.CampfireBuilder;
import com.pixel4r.plugin.enderice.bettercampfire.listeners.ItemDrops;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterCampfire extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new CampfireBuilder(), this);
        getServer().getPluginManager().registerEvents(new ItemDrops(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }
}
