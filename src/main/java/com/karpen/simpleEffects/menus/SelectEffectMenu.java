package com.karpen.simpleEffects.menus;

import com.karpen.simpleEffects.model.Config;
import com.karpen.simpleEffects.model.Type;
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
        Inventory inventory = Bukkit.createInventory(player, 27, ChatColor.translateAlternateColorCodes('&', config.getMenuName()));
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

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.CHERRY)){
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

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.ENDROD)){
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

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.TOTEM)){
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

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.HEART)){
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

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.PALE)){
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

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.PURPLE)){
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

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.NOTE)){
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

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.CLOUD)){
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

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.CHERRY)){
            types.players.remove(player, Type.CHERRY);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else{
            types.players.put(player, Type.CHERRY);
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

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.ENDROD)){
            types.players.remove(player, Type.ENDROD);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.players.put(player, Type.ENDROD);
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

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.TOTEM)){
            types.players.remove(player, Type.TOTEM);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.players.put(player, Type.TOTEM);
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

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.HEART)){
            types.players.remove(player, Type.HEART);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.players.put(player, Type.HEART);
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

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.PALE)){
            types.players.remove(player, Type.PALE);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.players.put(player, Type.PALE);
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

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.PURPLE)){
            types.players.remove(player, Type.PURPLE);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.players.put(player, Type.PURPLE);
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

        if (types.players.containsKey(player) && types.players.get(player).equals(Type.NOTE)){
            types.players.remove(player, Type.NOTE);
            effects.removePlayer(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.players.put(player, Type.NOTE);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
        }

        return true;
    }

    private boolean activeCloud(Player player) {
        playerInventors.remove(player);
        player.closeInventory();

        if (config.isRightsUsing() && !player.hasPermission(config.getRightsCloud())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getErrPerms()));
            return true;
        }

        if (Type.CLOUD.equals(types.players.get(player))) {
            types.players.remove(player);
            effects.stopCloudEffect(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgDisable()));
        } else {
            types.players.put(player, Type.CLOUD);
            effects.startCloudEffect(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getMsgEnable()));
        }

        return true;
    }
}
