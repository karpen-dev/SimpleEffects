package com.karpen.simpleEffects.menus;

import com.karpen.simpleEffects.model.Config;
import com.karpen.simpleEffects.model.Type;
import com.karpen.simpleEffects.model.Types;
import com.karpen.simpleEffects.utils.EffectAppler;
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
    private Types types;

    private final Map<Player, Inventory> playerInventors;

    public SelectEffectMenu(Config config, Types types, Map<Player, Inventory> playerInventors){
        this.types = types;
        this.config = config;
        this.playerInventors = playerInventors;
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
                case 1 -> EffectAppler.activeEff(player, Type.CHERRY);
                case 2 -> EffectAppler.activeEff(player, Type.ENDROD);
                case 3 -> EffectAppler.activeEff(player, Type.TOTEM);
                case 4 -> EffectAppler.activeEff(player, Type.HEART);
                case 5 -> EffectAppler.activeEff(player, Type.PALE);
                case 6 -> EffectAppler.activeEff(player, Type.PURPLE);
                case 7 -> EffectAppler.activeEff(player, Type.NOTE);
                case 13 -> EffectAppler.activeEff(player, Type.CLOUD);
            }
        }
    }

    private ItemStack cherryItem(Player player){
        ItemStack item = new ItemStack(Material.PINK_DYE, 1);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getItemCherryName()));

        if (types.players.containsKey(player.getUniqueId()) && types.players.get(player.getUniqueId()).equals(Type.CHERRY)){
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

        if (types.players.containsKey(player.getUniqueId()) && types.players.get(player.getUniqueId()).equals(Type.ENDROD)){
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

        if (types.players.containsKey(player.getUniqueId()) && types.players.get(player.getUniqueId()).equals(Type.TOTEM)){
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

        if (types.players.containsKey(player.getUniqueId()) && types.players.get(player.getUniqueId()).equals(Type.HEART)){
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

        if (types.players.containsKey(player.getUniqueId()) && types.players.get(player.getUniqueId()).equals(Type.PALE)){
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

        if (types.players.containsKey(player.getUniqueId()) && types.players.get(player.getUniqueId()).equals(Type.PURPLE)){
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

        if (types.players.containsKey(player.getUniqueId()) && types.players.get(player.getUniqueId()).equals(Type.NOTE)){
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

        if (types.players.containsKey(player.getUniqueId()) && types.players.get(player.getUniqueId()).equals(Type.CLOUD)){
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
}
