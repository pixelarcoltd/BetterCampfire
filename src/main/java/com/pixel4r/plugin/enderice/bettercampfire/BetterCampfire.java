package com.pixel4r.plugin.enderice.bettercampfire;

import com.pixel4r.plugin.enderice.bettercampfire.listeners.CampfireBuilder;
import com.pixel4r.plugin.enderice.bettercampfire.listeners.ItemDrops;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterCampfire extends JavaPlugin {
    public FileConfiguration config = getConfig();
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new CampfireBuilder(), this);
        getServer().getPluginManager().registerEvents(new ItemDrops(), this);
        Bukkit.removeRecipe(new NamespacedKey("minecraft" ,"campfire"));

    }

    @Override
    public void onDisable() {

    }

    public void loadDefaultConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void CustomPlayerConfig() {
    }

}
