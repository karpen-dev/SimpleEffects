package com.karpen.simpleEffects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.block.data.type.Bed;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public final class SimpleEffects extends JavaPlugin implements Listener {

    private int count;
    private int speed;

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

    private BukkitRunnable cherryTask;
    private BukkitRunnable endRodTask;
    private BukkitRunnable totemTask;

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
        speed = config.getInt("speed");

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

    private void startCherryEffects(Player player){
        cherryTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (cherryPlayers.contains(player)){
                    player.getWorld()
                            .spawnParticle(Particle.CHERRY_LEAVES, player.getLocation().add(0, 1, 0), count, 0.5, 1, 0.5, 0);
                }
            }
        };
        cherryTask.runTaskTimer(this, speed, 20);
    }

    private void startEndRodEffects(Player player){
        endRodTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (endRodPlayers.contains(player)){
                    player.getWorld()
                            .spawnParticle(Particle.END_ROD, player.getLocation().add(0, 1, 0), count, 0.5, 1, 0.5, 0);
                }
            }
        };
        endRodTask.runTaskTimer(this, speed, 20);
    }

    private void startTotemEffects(Player player){
        totemTask = new BukkitRunnable() {
            @Override
            public void run() {
                if (totemPlayers.contains(player)){
                    player.getWorld()
                            .spawnParticle(Particle.TOTEM_OF_UNDYING, player.getLocation().add(0, 1, 0), count, 0.5, 1, 0.5, 0);
                }
            }
        };
        totemTask.runTaskTimer(this, speed, 20);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        startCherryEffects(player);
        startTotemEffects(player);
        startEndRodEffects(player);

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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("eff")){
            if (!(sender instanceof Player)){
                sender.sendMessage(ChatColor.RED + errConsole);
                return true;
            }

            Player player = (Player) sender;

            if (args.length == 0){
                player.sendMessage(ChatColor.GREEN + errArgs);
                return true;
            }

            switch (args[0].toLowerCase()) {
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
        } else {
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

    private boolean errCommand(Player player) {
        player.sendMessage(ChatColor.RED + errCommand);
        return true;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
