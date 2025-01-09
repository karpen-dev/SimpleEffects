package com.karpen.simpleEffects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SimpleEffects extends JavaPlugin implements Listener, CommandExecutor, TabCompleter {

    private int count;

    private String msgCherry;
    private String msgEndRod;
    private String msgTotem;

    private String msgDisCherry;
    private String msgDisEndRod;
    private String msgDisTotem;

    private String errConsole;
    private String errArgs;
    private String errCommand;

    private final Set<Player> cherryPlayers = new HashSet<>();
    private final Set<Player> endRodPlayers = new HashSet<>();
    private final Set<Player> totemPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        loadConfig();

        Bukkit.getPluginManager().registerEvents(this, this);

        getLogger().info("SimpleEffects by karpen");
    }

    private void loadConfig(){
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        count = config.getInt("count");

        msgCherry = config.getString("selected-cherry");
        msgEndRod = config.getString("selected-endRod");
        msgTotem = config.getString("selected-totem");

        msgDisCherry = config.getString("disable-cherry");
        msgDisEndRod = config.getString("disable-endRod");
        msgDisTotem = config.getString("disable-totem");

        errConsole = config.getString("err-console");
        errArgs = config.getString("err-args");
        errCommand = config.getString("err-command");
    }

    private void spawnEff(Location location, Particle particle){
        location.getWorld().spawnParticle(particle, location, count, 0.5, 0.5, 0.5);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        cherryPlayers.remove(player);
        totemPlayers.remove(player);
        endRodPlayers.remove(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        cherryPlayers.remove(player);
        totemPlayers.remove(player);
        endRodPlayers.remove(player);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();

        double oldX = event.getFrom().getX();
        double oldY = event.getFrom().getY();
        double oldZ = event.getFrom().getZ();

        double newX = event.getTo().getX();
        double newY = event.getTo().getY();
        double newZ = event.getTo().getZ();

        if (oldX != newX || oldY != newY || oldZ != newZ){
            if (cherryPlayers.contains(player)){
                spawnEff(player.getLocation(), Particle.CHERRY_LEAVES);
            }
            if (endRodPlayers.contains(player)){
                spawnEff(player.getLocation(), Particle.END_ROD);
            }
            if (totemPlayers.contains(player)){
                spawnEff(player.getLocation(), Particle.TOTEM_OF_UNDYING);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + errConsole);
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("eff")){
            if (args.length == 0){
                player.sendMessage(ChatColor.RED + errArgs);
                return true;
            }

            switch (args[0].toLowerCase()){
                case "cherry":
                    return activeCherry(player);
                case "endrod":
                    return activeEndRod(player);
                case "totem":
                    return activeTotem(player);
                case "reload":
                    return reloadConfig(player);
                default:
                    return errCommand(player);
            }
        }

        return false;
    }

    private boolean activeCherry(Player player){
        if (cherryPlayers.contains(player)){
            cherryPlayers.remove(player);
            player.sendMessage(ChatColor.GREEN + msgDisCherry);
        } else{
            cherryPlayers.add(player);
            player.sendMessage(ChatColor.GREEN + msgCherry);
        }
        return true;
    }

    private boolean activeEndRod(Player player){
        if (endRodPlayers.contains(player)){
            endRodPlayers.remove(player);
            player.sendMessage(ChatColor.GREEN + msgDisEndRod);
        } else {
            endRodPlayers.add(player);
            player.sendMessage(ChatColor.GREEN + msgEndRod);
        }
        return true;
    }

    private boolean activeTotem(Player player){
        if (totemPlayers.contains(player)){
            totemPlayers.remove(player);
            player.sendMessage(ChatColor.GREEN + msgDisTotem);
        } else {
            totemPlayers.add(player);
            player.sendMessage(ChatColor.GREEN + msgTotem);
        }
        return true;
    }

    private boolean reloadConfig(Player player){
        loadConfig();
        player.sendMessage(ChatColor.GREEN + "Конфиг успешно перезагружен");
        return true;
    }

    private boolean errCommand(Player player){
        player.sendMessage(ChatColor.RED + errCommand);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("eff")){
            if (args.length == 1){
                suggestions.add("cherry");
                suggestions.add("endrod");
                suggestions.add("totem");
            }
        }

        return suggestions;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
