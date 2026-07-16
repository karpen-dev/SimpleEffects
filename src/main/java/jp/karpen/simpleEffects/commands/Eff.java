package jp.karpen.simpleEffects.commands;

import jp.karpen.simpleEffects.SimpleEffects;
import jp.karpen.simpleEffects.menus.SelectEffectMenu;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Eff implements CommandExecutor {

    private final Configuration config;
    private final SimpleEffects plugin;
    private final SelectEffectMenu effectMenu;

    public Eff(SimpleEffects plugin, SelectEffectMenu effectMenu) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.effectMenu = effectMenu;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)){
            commandSender.sendMessage(SimpleEffects.getLanguageManager().getMessage("err-console"));

            return true;
        }

        if (strings.length == 0){
            effectMenu.openMenu(player);

            return true;
        }

        switch (strings[0].toLowerCase()) {
            case "cherry" -> plugin.getCherryListener().toggleEffect(player);
            case "endrod" -> plugin.getEndrodListener().toggleEffect(player);
            case "totem" -> plugin.getTotemListener().toggleEffect(player);
            case "heart" -> plugin.getHeartListener().toggleEffect(player);
            case "pale" -> plugin.getPaleListener().toggleEffect(player);
            case "note" -> plugin.getNoteListener().toggleEffect(player);
            case "purple" -> plugin.getPurpleListener().toggleEffect(player);
            case "spiral" -> selectSpiral(player, strings);
            default -> errCmd(player);
        }

        return true;
    }

    private void selectSpiral(Player player, String[] args) {
        if (args.length != 2) {
            player.sendMessage(SimpleEffects.getLanguageManager().getMessage("err-args"));

            return;
        }

        switch (args[1].toLowerCase()) {
            case "totem" -> plugin.getTotemSpiralListener().toggleEffect(player);
            case "endrod" -> plugin.getEndrodSpiralListener().toggleEffect(player);
            case "cherry" -> plugin.getCherrySpiralListener().toggleEffect(player);
            case "pale" -> plugin.getPaleSpiralListener().toggleEffect(player);
            case "purple" -> plugin.getPurpleSpiralListener().toggleEffect(player);
            case "note" -> plugin.getNoteSpiralListener().toggleEffect(player);
            default -> errCmd(player);
        }

    }

    private boolean errCmd(Player player) {
        player.sendMessage(SimpleEffects.getLanguageManager().getMessage("err-command"));
        return true;
    }
}
