package jp.karpen.simpleEffects.menus;

import jp.karpen.simpleEffects.SimpleEffects;
import jp.karpen.simpleEffects.model.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SelectEffectMenu implements Listener {

    private final SimpleEffects plugin;
    private final Map<Player, Inventory> playerInventors;

    public SelectEffectMenu(SimpleEffects plugin){
        this.plugin = plugin;
        this.playerInventors = plugin.getPlayerInventors();
    }

    public void openMenu(Player player){
        Inventory inventory = Bukkit.createInventory(player, 27,
                SimpleEffects.getLanguageManager().getMessage("menu-name"));

        playerInventors.put(player, inventory);

        inventory.setItem(1, createItem(Type.CHERRY));
        inventory.setItem(2, createItem(Type.ENDROD));
        inventory.setItem(3, createItem(Type.TOTEM));
        inventory.setItem(4, createItem(Type.HEART));
        inventory.setItem(5, createItem(Type.PALE));
        inventory.setItem(6, createItem(Type.PURPLE));
        inventory.setItem(7, createItem(Type.NOTE));

        inventory.setItem(10, createItem(Type.CHERRY_SPIRAL));
        inventory.setItem(11, createItem(Type.ENDROD_SPIRAL));
        inventory.setItem(12, createItem(Type.TOTEM_SPIRAL));
        inventory.setItem(14, createItem(Type.PALE_SPIRAL));
        inventory.setItem(15, createItem(Type.PURPLE_SPIRAL));
        inventory.setItem(16, createItem(Type.NOTE_SPIRAL));

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        if (!(event.getWhoClicked() instanceof Player player)){
            return;
        }

        if (event.getInventory().equals(playerInventors.get(player))){
            event.setCancelled(true);

            switch (event.getRawSlot()){
                case 1 -> plugin.getCherryListener().toggleEffect(player);
                case 2 -> plugin.getEndrodListener().toggleEffect(player);
                case 3 -> plugin.getTotemListener().toggleEffect(player);
                case 4 -> plugin.getHeartListener().toggleEffect(player);
                case 5 -> plugin.getPaleListener().toggleEffect(player);
                case 6 -> plugin.getPurpleListener().toggleEffect(player);
                case 7 -> plugin.getNoteListener().toggleEffect(player);
                case 10 -> plugin.getCherrySpiralListener().toggleEffect(player);
                case 11 -> plugin.getEndrodSpiralListener().toggleEffect(player);
                case 12 -> plugin.getTotemSpiralListener().toggleEffect(player);
                case 14 -> plugin.getPaleSpiralListener().toggleEffect(player);
                case 15 -> plugin.getPurpleSpiralListener().toggleEffect(player);
                case 16 -> plugin.getNoteSpiralListener().toggleEffect(player);
            }
        }
    }

    private static ItemStack createItem(Type type) {
        ItemStack item = getItemStack(type);
        ItemMeta meta = item.getItemMeta();

        assert meta != null;
        meta.setDisplayName(SimpleEffects.getLanguageManager().getMessage(type.toString().toLowerCase()));

        meta.setLore(List.of(SimpleEffects.getLanguageManager().getMessage("item-enable")));

        item.setItemMeta(meta);

        return item;
    }

    @NotNull
    private static ItemStack getItemStack(Type type) {
        Material material = switch (type) {
            case CHERRY, CHERRY_SPIRAL -> Material.PINK_DYE;
            case ENDROD, ENDROD_SPIRAL -> Material.WHITE_DYE;
            case TOTEM, TOTEM_SPIRAL -> Material.YELLOW_DYE;
            case PALE, PALE_SPIRAL -> Material.GRAY_DYE;
            case PURPLE, PURPLE_SPIRAL -> Material.PURPLE_DYE;
            case NOTE, NOTE_SPIRAL -> Material.LIGHT_GRAY_DYE;
            case HEART -> Material.RED_DYE;
        };

        return new ItemStack(material, 1);
    }
}
