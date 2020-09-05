package me.jwhz.witchshop.gui.guis;

import me.jwhz.witchshop.WitchShop;
import me.jwhz.witchshop.config.ConfigHandler;
import me.jwhz.witchshop.config.ConfigValue;
import me.jwhz.witchshop.gui.GUI;
import me.jwhz.witchshop.shop.ShopItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VendorGUI extends GUI {

    public static ArrayList<VendorGUI> guis = new ArrayList<VendorGUI>();

    private HashMap<Integer, ShopItem> items = new HashMap<Integer, ShopItem>();
    private DecimalFormat format = new DecimalFormat("#,###.##");

    @ConfigValue(path = "gui.size")
    public int size = 6;
    @ConfigValue(path = "gui.name")
    public String guiName = "&aVendor Shop";
    @ConfigValue(path = "gui.purchase lore")
    public String purchaseLore = "&7Click to purchase for: &a$%cost%";
    @ConfigValue(path = "gui.time left lore")
    public String timeLeftLore = "&7Expires: &a%time%";

    @ConfigValue(path = "messages.not enough money")
    public String notEnoughMoney = "&cYou do not have enough money to purchase this!";
    @ConfigValue(path = "messages.not enough space")
    public String notEnoughSpace = "&cYou do not have enough space in your inventory to purchase this!";
    @ConfigValue(path = "messages.purchased item")
    public String purchasedItem = "&aYou have purchased an item from the vendor!";

    public VendorGUI(Player player) {

        ConfigHandler.setPresets(this);
        ConfigHandler.reload(this);

        inventory = Bukkit.createInventory(null, 9 * size, ChatColor.translateAlternateColorCodes('&', guiName));

        addDefaultListening(player);

        setupGUI();

        player.openInventory(inventory);

        guis.add(this);

    }

    @Override
    public void onClick(InventoryClickEvent e) {

        e.setCancelled(true);

        if (items.containsKey(e.getSlot())) {

            ShopItem item = items.get(e.getSlot());

            if(WitchShop.getEconomy().getBalance((Player) e.getWhoClicked()) < item.getCost()){

                e.getWhoClicked().sendMessage(notEnoughMoney);
                return;

            } else if(e.getWhoClicked().getInventory().firstEmpty() == -1){

                e.getWhoClicked().sendMessage(notEnoughSpace);
                return;

            }

            WitchShop.getEconomy().withdrawPlayer((Player)e.getWhoClicked(), item.getCost());
            e.getWhoClicked().getInventory().addItem(item.getItem());

            e.getWhoClicked().sendMessage(purchasedItem);

        }

    }

    @Override
    public void setupGUI() {

        int count = 0;

        for(ShopItem item : core.shop.getList()){

            items.put(count, item);

            ItemStack guiItem = item.getItem().clone();
            ItemMeta meta = guiItem.getItemMeta();

            List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<String>();

            lore.add("");
            lore.add(purchaseLore.replace("%cost%", format.format(item.getCost())));
            lore.add(timeLeftLore.replace("%time%", formatSeconds(item.getExpire() - System.currentTimeMillis())));

            meta.setLore(lore);

            guiItem.setItemMeta(meta);

            inventory.setItem(count, guiItem);

            count ++;

        }

    }

    private String formatSeconds(long time) {

        long leftOverSeconds = time / 1000 % 60;
        long minutes = time / (60 * 1000) % 60;
        long hours = time / (60 * 60 * 1000) % 24;
        long days = time / (24 * 60 * 60 * 1000);

        return (days == 0 ? "" : days + "d ") + (hours == 0 ? "" : hours + "h ") +
                (minutes == 0 ? "" : minutes + "m ") + (leftOverSeconds == 0 ? "" : leftOverSeconds + "s");

    }
}
