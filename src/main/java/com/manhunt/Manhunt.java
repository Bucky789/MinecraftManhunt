package com.manhunt;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class Manhunt extends JavaPlugin implements Listener {

    private Player speedrunner;
    private final Set<Player> hunters = new HashSet<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("Manhunt plugin enabled with compass tracking!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Manhunt plugin disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("startmanhunt")) {
            if (!(sender instanceof Player)) return false;
            Player starter = (Player) sender;

            List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
            Collections.shuffle(players);
            speedrunner = players.get(0);
            hunters.clear();

            for (Player p : players) {
                if (!p.equals(speedrunner)) {
                    hunters.add(p);
                }
                p.setGameMode(GameMode.SURVIVAL);
                p.teleport(starter.getLocation());
            }

            // Give compass to all hunters
            for (Player hunter : hunters) {
                giveCompass(hunter);
            }

            Bukkit.broadcastMessage("§a[Manhunt] Speedrunner: §e" + speedrunner.getName());
            Bukkit.broadcastMessage("§a[Manhunt] Hunters: §e" +
                    hunters.stream().map(Player::getName).reduce((a, b) -> a + ", " + b).orElse("None"));
            return true;
        }
        return false;
    }

    private void giveCompass(Player hunter) {
        // Give a compass
        hunter.getInventory().addItem(new ItemStack(Material.COMPASS));
        
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player hunter = event.getPlayer();
        if (hunters.contains(hunter) && speedrunner != null && speedrunner.isOnline()) {
            if (hunter.getWorld().equals(speedrunner.getWorld())) {
                Location targetLoc = speedrunner.getLocation();
                updateCompass(hunter, targetLoc); // Works in both Overworld and Nether
            }
        }
    }
    
    public void updateCompass(Player hunter, Location targetLocation) {
        ItemStack compass = new ItemStack(Material.COMPASS);
        CompassMeta meta = (CompassMeta) compass.getItemMeta();

        if (meta != null) {
            // Set lodestone target
            meta.setLodestone(targetLocation);
            meta.setLodestoneTracked(false); // Required to use a custom lodestone
            compass.setItemMeta(meta);

            // Give compass to player (or replace existing one)
            hunter.getInventory().remove(Material.COMPASS);
            hunter.getInventory().addItem(compass);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player respawned = event.getPlayer();
        // If hunter, give compass on respawn
        if (hunters.contains(respawned)) {
            // Delay giving compass to allow inventory load
            Bukkit.getScheduler().runTaskLater(this, () -> giveCompass(respawned), 20L); // 1 second delay
        }
    }
}
