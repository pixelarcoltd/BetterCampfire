package com.pixel4r.plugin.enderice.bettercampfire.listeners;

import com.pixel4r.plugin.enderice.bettercampfire.registries.items.CustomItems;
import com.pixel4r.plugin.enderice.bettercampfire.registries.items.CustomModel;
import com.pixel4r.plugin.enderice.bettercampfire.utils.LocationManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.io.File;
import java.util.*;


public final class CampfireBuilder implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteractWithBlock(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();
        Set<Material> materials = new HashSet<>();
        materials.add(Material.AIR);

        Block targetBlock = player.getTargetBlock(materials, 8);

        if (!isValidTool(event.getPlayer(), CustomItems.plantFibre())) return;

        if (!isPlaceable(targetBlock)) return;

        player.getInventory().removeItem(CustomItems.PLANT_FIBRE);

        if (isPitFilled(targetBlock)) return;

        ItemFrame pitBase = player.getWorld().spawn(targetBlock.getLocation().add(0, 1, 0), ItemFrame.class);
        pitBase.setFacingDirection(BlockFace.UP);
        pitBase.setItem(CustomModel.getCampfirePitModel(1001));
        pitBase.customName(Component.text("fire_pit"));
        pitBase.setInvulnerable(true);
        pitBase.setVisible(false);

    }

    public void onInteractWithCampfire(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Set<Material> materials = new HashSet<>();
        materials.add(Material.AIR);

        if (!event.getPlayer().getTargetBlock(materials, 8).getType().equals(Material.CAMPFIRE)) return;
        if (!event.getPlayer().isSneaking()) return;

    }


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteractWithEntity(PlayerInteractEntityEvent event) {
        if (event.getRightClicked().getType() != EntityType.ITEM_FRAME) return;
        if (Objects.equals(event.getRightClicked().customName(), Component.text("fire_pit"))) {
            event.setCancelled(true);

            ItemFrame pitBase = (ItemFrame) event.getRightClicked();
            int pitStage = pitBase.getItem().getItemMeta().getCustomModelData();

            if (isValidTool(event.getPlayer(), CustomItems.plantFibre())) {
                event.getPlayer().getInventory().removeItem(CustomItems.PLANT_FIBRE);
                if ((pitStage) < 1008) {
                    pitBase.setItem(CustomModel.getCampfirePitModel(pitStage + 1));
                }
            }

            if (isValidTool(event.getPlayer(), new ItemStack(Material.STICK))) {
                event.getPlayer().getInventory().removeItem(new ItemStack(Material.STICK));
                if ((pitStage) >= 1008) {
                    Location location = pitBase.getLocation();
                    pitBase.remove();
                    location.getBlock().setType(Material.COAL_BLOCK);

                    ItemFrame campfireBase = location.getWorld().spawn(location.add(0, 1, 0), ItemFrame.class);
                    campfireBase.customName(Component.text("fire_camp"));
                    campfireBase.setItem(CustomModel.getCampfirePitModel(1011));
                    campfireBase.setInvulnerable(true);
                    campfireBase.setVisible(false);
                    return;
                }
            }
        }

        if (Objects.equals(event.getRightClicked().customName(), Component.text("fire_camp"))) {
            ItemFrame campfireBase = (ItemFrame) event.getRightClicked();

            event.getPlayer().getInventory().removeItem(new ItemStack(Material.STICK));
            event.setCancelled(true);

            int pitStage = campfireBase.getItem().getItemMeta().getCustomModelData();

            if (pitStage == 1011) {
                campfireBase.setItem(CustomModel.getCampfirePitModel(1012));
            }

            if (pitStage >= 1012) {
                campfireBase.remove();
                Location location = campfireBase.getLocation();
                location.getBlock().setType(Material.CAMPFIRE);
            }
        }
    }

    private boolean isPlaceable(Block block) {
        if (block.getType() == Material.AIR) return false;
        if (block.getLocation().add(0, -1, 0).getBlock().getType() == Material.AIR) return false;

        Block start = block.getLocation().add(-1, 1 , -1).getBlock();
        Block end   = block.getLocation().add(1, 1, 1).getBlock();

        List<Block> blocks = LocationManager.getBlockBetweenPoints(start.getLocation(), end.getLocation());

        int i = 0;
        for (Block singleBlock : blocks) {
            if (singleBlock.getType() != Material.AIR)
                i++;
        }

        return i >= 8;
    }

    private boolean isPitFilled(Block block) {
        return getCampfirePit(block) != null;
    }

    private boolean isValidTool(Player player, ItemStack tool) {
        if (!player.getInventory().getItemInMainHand().asOne().equals(tool.asOne())) return false;

        return true;
    }

    @Nullable
    private Entity getCampfirePit(Block block) {
        Collection<Entity> entities = block.getWorld().getNearbyEntities(block.getLocation(), 1.1, 1.1, 1.1);
        for (Entity entity : entities) {
            if (entity.getType() == EntityType.ITEM_FRAME){
                return entity;
            }
        }
        return null;
    }

}
