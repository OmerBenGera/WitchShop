package me.jwhz.witchshop.gui;

import me.jwhz.witchshop.WitchShop;
import me.jwhz.witchshop.config.ConfigFile;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public abstract class GUI extends ConfigFile {

    public Inventory inventory;
    public Listener defaultListening;
    protected WitchShop core = WitchShop.getInstance();
    public boolean closed = false;

    public GUI() {

        super("config");

    }

    public void addDefaultListening(final Player player) {

        defaultListening = new Listener() {

            @EventHandler
            public void onInventoryClick(InventoryClickEvent e) {

                if (e.getInventory().equals(inventory) && e.getWhoClicked().equals(player)) {
                    e.setCancelled(true);

                    onClick(e);

                }
            }

            @EventHandler
            public void onInventoryInteract(InventoryInteractEvent e) {

                if (e.getInventory().equals(inventory) && e.getWhoClicked().equals(player))
                    e.setCancelled(true);

            }

            @EventHandler
            public void onInventoryClose(InventoryCloseEvent e) {

                if (e.getInventory().equals(inventory) && e.getPlayer().equals(player)) {

                    closed = true;
                    HandlerList.unregisterAll(this);

                }

            }

        };

        core.getServer().getPluginManager().registerEvents(defaultListening, core);

    }

    public void onClick(InventoryClickEvent e) {
    }

    public abstract void setupGUI();


    protected ItemStack fastItem(Material mat, String displayName, boolean enchanted, String... lore) {

        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        ArrayList<String> updatedLore = new ArrayList<String>();
        for (String s : lore)
            updatedLore.add(ChatColor.translateAlternateColorCodes('&', s));

        if (enchanted)
            meta.addEnchant(Enchantment.DURABILITY, 1, true);

        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        meta.setLore(updatedLore);
        item.setItemMeta(meta);

        return item;

    }

    protected ItemStack fastItem(Material mat, String displayName, String... lore) {

        return fastItem(mat, displayName, false, lore);

    }


}
