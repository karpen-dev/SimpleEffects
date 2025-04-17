package com.karpen.simpleEffects.menus;

import com.karpen.simpleEffects.model.Config;
import com.karpen.simpleEffects.model.Types;
import com.karpen.simpleEffects.utils.Effects;
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
        Inventory inventory = Bukkit.createInventory(player, 27, config.getMenuName());
        playerInventors.put(player, inventory);

        inventory.setItem(1, cherryItem(player));
        inventory.setItem(2, endrodItem(player));
        inventory.setItem(3, totemItem(player));
        inventory.setItem(4, heartItem(player));
        inventory.setItem(5, paleItem(player));
        inventory.setItem(6, purpleItem(player));
        inventory.setItem(7, noteItem(player));

        inventory.setItem(13, cloudItem(player));

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

                case 13 -> activeCloud(player);
            }
        }
    }

    private ItemStack cherryItem(Player player){
        ItemStack item = new ItemStack(Material.PINK_DYE, 1);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getItemCherryName()));

        if (types.cherryPlayers.contains(player)){
            meta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', config.getItemsDisable())));
        } else {
            meta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', config.getItemsEnable())));
        }

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack endrodItem(Player player){
        ItemStack item = new ItemStack(Material.WHITE_DYE, 1);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getItemEndRodName()));

        if (types.endRodPlayers.contains(player)){
            meta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', config.getItemsDisable())));
        } else {
            meta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', config.getItemsEnable())));
        }

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack totemItem(Player player){
        ItemStack item = new ItemStack(Material.YELLOW_DYE, 1);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getItemTotemName()));

        if (types.totemPlayers.contains(player)){
            meta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', config.getItemsDisable())));
        } else {
            meta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', config.getItemsEnable())));
        }

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack heartItem(Player player){
        ItemStack item = new ItemStack(Material.RED_DYE, 1);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getItemHeartName()));

        if (types.heartPlayers.contains(player)){
            meta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', config.getItemsDisable())));
        } else {
            meta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', config.getItemsEnable())));
        }

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack paleItem(Player player){
        ItemStack item = new ItemStack(Material.GRAY_DYE, 1);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getItemPaleName()));

        if (types.palePlayers.contains(player)){
            meta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', config.getItemsDisable())));
        } else {
            meta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', config.getItemsEnable())));
        }

        if (config.isOldVer()){
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getUnsupportedName()));
            meta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', config.getNotAvailableMsg())));
        }

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack purpleItem(Player player){
        ItemStack item = new ItemStack(Material.PURPLE_DYE, 1);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getItemPurpleName()));

        if (types.purplePlayers.contains(player)){
            meta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', config.getItemsDisable())));
        } else {
            meta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', config.getItemsEnable())));
        }

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack noteItem(Player player){
        ItemStack item = new ItemStack(Material.GREEN_DYE, 1);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getItemNotesName()));

        if (types.notePlayers.contains(player)){
            meta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', config.getItemsDisable())));
        } else {
            meta.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', config.getItemsEnable())));
        }

        item.setItemMeta(meta);

        return item;
    }

    private ItemStack cloudItem(Player player){
        ItemStack item = new ItemStack(Material.LIGHT_GRAY_DYE, 1);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getItemCloudName()));

        List<String> lore = new ArrayList<>();

        if (types.cloudPlayers.contains(player)){
            lore.add(ChatColor.translateAlternateColorCodes('&', config.getItemsDisable()));
        } else {
            lore.add(ChatColor.translateAlternateColorCodes('&', config.getItemsEnable()));
        }

        if (config.getWarning() != null){
            lore.add(" ");
            lore.add(ChatColor.translateAlternateColorCodes('&', config.getWarning()));
        }

        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    private boolean activeCherry(Player player){
        playerInventors.remove(player);

        player.closeInventory();

        if (config.isRightsUsing() && !player.hasPermission(config.getRightsCherry())){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));

            return true;
        }

        if (types.cherryPlayers.contains(player)){
            types.cherryPlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else{
            types.cherryPlayers.add(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
        }

        return true;
    }

    private boolean activeEndRod(Player player){
        playerInventors.remove(player);

        player.closeInventory();

        if (config.isRightsUsing() && !player.hasPermission(config.getRightsEndRod())){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));

            return true;
        }

        if (types.endRodPlayers.contains(player)){
            types.endRodPlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.endRodPlayers.add(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
        }

        return true;
    }

    private boolean activeTotem(Player player){
        playerInventors.remove(player);

        player.closeInventory();

        if (config.isRightsUsing() && !player.hasPermission(config.getRightsTotem())){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));

            return true;
        }

        if (types.totemPlayers.contains(player)){
            types.totemPlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.totemPlayers.add(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
        }

        return true;
    }

    private boolean activeHeart(Player player){
        playerInventors.remove(player);

        player.closeInventory();

        if (config.isRightsUsing() && !player.hasPermission(config.getRightsHeart())){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));

            return true;
        }

        if (types.heartPlayers.contains(player)){
            types.heartPlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.heartPlayers.add(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
        }

        return true;
    }

    private boolean activePale(Player player){
        playerInventors.remove(player);

        player.closeInventory();

        if (config.isOldVer()){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getNotAvailableMsg()));

            return true;
        }

        if (config.isRightsUsing() && !player.hasPermission(config.getRightsPale())){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));

            return true;
        }

        if (types.palePlayers.contains(player)){
            types.palePlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.palePlayers.add(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
        }

        return true;
    }

    private boolean activePurple(Player player){
        playerInventors.remove(player);

        player.closeInventory();

        if (config.isRightsUsing() && !player.hasPermission(config.getRightsPurple())){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));

            return true;
        }

        if (types.purplePlayers.contains(player)){
            types.purplePlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.purplePlayers.add(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
        }

        return true;
    }

    private boolean activeNote(Player player){
        playerInventors.remove(player);

        player.closeInventory();

        if (config.isRightsUsing() && !player.hasPermission(config.getRightsNotes())){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));

            return true;
        }

        if (types.notePlayers.contains(player)){
            types.notePlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.notePlayers.add(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
        }

        return true;
    }

    private boolean activeCloud(Player player){
        playerInventors.remove(player);

        player.closeInventory();

        if (config.isRightsUsing() && !player.hasPermission(config.getRightsCloud())){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));

            return true;
        }

        if (types.cloudPlayers.contains(player)){
            types.cloudPlayers.remove(player);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.cloudPlayers.add(player);
            effects.startCloudEffect(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
        }

        return true;
    }
}
