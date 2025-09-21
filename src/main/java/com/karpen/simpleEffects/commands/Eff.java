package com.karpen.simpleEffects.commands;

import com.karpen.simpleEffects.menus.SelectEffectMenu;
import com.karpen.simpleEffects.model.Type;
import com.karpen.simpleEffects.model.Types;
import com.karpen.simpleEffects.utils.EffectAppler;
import com.karpen.simpleEffects.utils.Effects;
import com.karpen.simpleEffects.model.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Eff implements CommandExecutor {

    private final Config config;
    private SelectEffectMenu effectMenu;

    public Eff(Config config, SelectEffectMenu effectMenu) {
        this.config = config;
        this.effectMenu = effectMenu;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)){
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrConsole()));

            return true;
        }

        if (config == null){
            commandSender.sendMessage(ChatColor.RED + "Config not initialized");

            return true;
        }

        Player player = (Player) commandSender;

        if (strings.length == 0){
            effectMenu.openMenu(player);

            return true;
        }

        return switch (strings[0].toLowerCase()) {
            case "cherry" -> EffectAppler.activeEff(player, Type.CHERRY);
            case "endrod" -> EffectAppler.activeEff(player, Type.ENDROD);
            case "totem" -> EffectAppler.activeEff(player, Type.TOTEM);
            case "heart" -> EffectAppler.activeEff(player, Type.HEART);
            case "pale" -> EffectAppler.activeEff(player, Type.PALE);
            case "note" -> EffectAppler.activeEff(player, Type.NOTE);
            case "purple" -> EffectAppler.activeEff(player, Type.PURPLE);
            case "cloud" -> EffectAppler.activeEff(player, Type.CLOUD);
            case "spiral" -> selectSpiral(player, strings);
            default -> errCmd(player);
        };
    }

    private boolean selectSpiral(Player player, String[] args) {
        if (args.length != 2) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrArgs()));

            return true;
        }

        switch (args[1].toLowerCase()) {
            case "totem" -> EffectAppler.activeEff(player, Type.TOTEM_SPIRAL);
            case "endrod" -> EffectAppler.activeEff(player, Type.ENDROD_SPIRAL);
            case "cherry" -> EffectAppler.activeEff(player, Type.CHERRY_SPIRAL);
            case "pale" -> EffectAppler.activeEff(player, Type.PALE_SPIRAL);
            case "purple" -> EffectAppler.activeEff(player, Type.PURPLE_SPIRAL);
            case "note" -> EffectAppler.activeEff(player, Type.NOTE_SPIRAL);
            default -> errCmd(player);
        }

        return true;
    }

    private boolean errCmd(Player player) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrCommand()));
        return true;
    }
}
