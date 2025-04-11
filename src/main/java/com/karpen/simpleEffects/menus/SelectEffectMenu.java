package com.karpen.simpleEffects.menus;

import com.karpen.simpleEffects.model.Config;
import com.karpen.simpleEffects.model.Types;
import com.karpen.simpleEffects.services.Effects;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class SelectEffectMenu implements Listener {

    private Config config;
    private Effects effects;
    private Types types;

    private final Map<Player, Inventory> playerInventors = new HashMap<>();

    public SelectEffectMenu(Config config, Effects effects, Types types){
        this.effects = effects;
        this.types = types;
        this.config = config;
    }

    public void openMenu(Player player){
        Inventory inventory = Bukkit.createInventory(player, 9, config.getMenuName());
        playerInventors.put(player, inventory);

        inventory.setItem(1, cherryItem(player));
        inventory.setItem(2, endrodItem(player));
        inventory.setItem(3, totemItem(player));
        inventory.setItem(4, heartItem(player));
        inventory.setItem(5, paleItem(player));
        inventory.setItem(6, purpleItem(player));
        inventory.setItem(7, noteItem(player));

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if (!(event.getWhoClicked() instanceof Player)){
            return;
        }

        Player player = (Player) event.getWhoClicked();

        if (event.getInventory().equals(playerInventors.get(player))){
            event.setCancelled(true);

            switch (event.getRawSlot()){
                case 1 -> activeCherry(player);
                case 2 -> activeEndRod(player);
                case 3 -> activeTotem(player);
                case 4 -> activeHeart(player);
                case 5 -> activePale(player);
                case 6 -> activePurple(player);
                case 7 -> activeNote(player);
            }
        }
    }

    private ItemStack cherryItem(Player player){
        ItemStack item = new ItemStack(Material.PINK_DYE, 1);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + config.getItemCherryName());

        if (types.cherryPlayers.contains(player)){
            meta.setLore(Collections.singletonList(ChatColor.RED + config.getItemsDisable()));
        } else {
            meta.setLore(Collections.singletonList(ChatColor.GREEN + config.getItemsEnable()));
        }

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack endrodItem(Player player){
        ItemStack item = new ItemStack(Material.WHITE_DYE, 1);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.WHITE + config.getItemEndRodName());

        if (types.endRodPlayers.contains(player)){
            meta.setLore(Collections.singletonList(ChatColor.RED + config.getItemsDisable()));
        } else {
            meta.setLore(Collections.singletonList(ChatColor.GREEN + config.getItemsEnable()));
        }

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack totemItem(Player player){
        ItemStack item = new ItemStack(Material.YELLOW_DYE, 1);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.YELLOW + config.getItemTotemName());

        if (types.totemPlayers.contains(player)){
            meta.setLore(Collections.singletonList(ChatColor.RED + config.getItemsDisable()));
        } else {
            meta.setLore(Collections.singletonList(ChatColor.GREEN + config.getItemsEnable()));
        }

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack heartItem(Player player){
        ItemStack item = new ItemStack(Material.RED_DYE, 1);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.RED + config.getItemHeartName());

        if (types.heartPlayers.contains(player)){
            meta.setLore(Collections.singletonList(ChatColor.RED + config.getItemsDisable()));
        } else {
            meta.setLore(Collections.singletonList(ChatColor.GREEN + config.getItemsEnable()));
        }

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack paleItem(Player player){
        ItemStack item = new ItemStack(Material.GRAY_DYE, 1);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.GRAY + config.getItemPaleName());

        if (types.palePlayers.contains(player)){
            meta.setLore(Collections.singletonList(ChatColor.RED + config.getItemsDisable()));
        } else {
            meta.setLore(Collections.singletonList(ChatColor.GREEN + config.getItemsEnable()));
        }

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack purpleItem(Player player){
        ItemStack item = new ItemStack(Material.PURPLE_DYE, 1);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.DARK_PURPLE + config.getItemPurpleName());

        if (types.purplePlayers.contains(player)){
            meta.setLore(Collections.singletonList(ChatColor.RED +config.getItemsDisable()));
        } else {
            meta.setLore(Collections.singletonList(ChatColor.GREEN + config.getItemsEnable()));
        }

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack noteItem(Player player){
        ItemStack item = new ItemStack(Material.GREEN_DYE, 1);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.GREEN + config.getItemNotesName());

        if (types.notePlayers.contains(player)){
            meta.setLore(Collections.singletonList(ChatColor.RED + config.getItemsDisable()));
        } else {
            meta.setLore(Collections.singletonList(ChatColor.GREEN + config.getItemsEnable()));
        }

        item.setItemMeta(meta);

        return item;
    }

    private boolean activeCherry(Player player){
        playerInventors.remove(player);

        player.closeInventory();

        if (config.isRightsUsing() && !player.hasPermission(config.getRightsCherry())){
            player.sendMessage(ChatColor.RED + config.getErrPerms());

            return true;
        }

        if (types.cherryPlayers.contains(player)){
            types.cherryPlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisable());
        } else{
            types.cherryPlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEnable());
        }

        return true;
    }

    private boolean activeEndRod(Player player){
        playerInventors.remove(player);

        player.closeInventory();

        if (config.isRightsUsing() && !player.hasPermission(config.getRightsEndRod())){
            player.sendMessage(ChatColor.RED + config.getErrPerms());

            return true;
        }

        if (types.endRodPlayers.contains(player)){
            types.endRodPlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisable());
        } else {
            types.endRodPlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEnable());
        }

        return true;
    }

    private boolean activeTotem(Player player){
        playerInventors.remove(player);

        player.closeInventory();

        if (config.isRightsUsing() && !player.hasPermission(config.getRightsTotem())){
            player.sendMessage(ChatColor.RED + config.getErrPerms());

            return true;
        }

        if (types.totemPlayers.contains(player)){
            types.totemPlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisable());
        } else {
            types.totemPlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEnable());
        }

        return true;
    }

    private boolean activeHeart(Player player){
        playerInventors.remove(player);

        player.closeInventory();

        if (config.isRightsUsing() && !player.hasPermission(config.getRightsHeart())){
            player.sendMessage(ChatColor.RED + config.getErrPerms());

            return true;
        }

        if (types.heartPlayers.contains(player)){
            types.heartPlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisable());
        } else {
            types.heartPlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEnable());
        }

        return true;
    }

    private boolean activePale(Player player){
        playerInventors.remove(player);

        player.closeInventory();

        if (config.isRightsUsing() && !player.hasPermission(config.getRightsPale())){
            player.sendMessage(ChatColor.RED + config.getErrPerms());

            return true;
        }

        if (types.palePlayers.contains(player)){
            types.palePlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisable());
        } else {
            types.palePlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEnable());
        }

        return true;
    }

    private boolean activePurple(Player player){
        playerInventors.remove(player);

        player.closeInventory();

        if (config.isRightsUsing() && !player.hasPermission(config.getRightsPurple())){
            player.sendMessage(ChatColor.RED + config.getErrPerms());

            return true;
        }

        if (types.purplePlayers.contains(player)){
            types.purplePlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisable());
        } else {
            types.purplePlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEnable());
        }

        return true;
    }

    private boolean activeNote(Player player){
        playerInventors.remove(player);

        player.closeInventory();

        if (config.isRightsUsing() && !player.hasPermission(config.getRightsNotes())){
            player.sendMessage(ChatColor.RED + config.getErrPerms());

            return true;
        }

        if (types.notePlayers.contains(player)){
            types.notePlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgDisable());
        } else {
            types.notePlayers.add(player);
            player.sendMessage(ChatColor.GREEN + config.getMsgEnable());
        }

        return true;
    }
}
