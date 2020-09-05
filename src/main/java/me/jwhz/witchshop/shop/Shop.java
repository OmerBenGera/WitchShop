package me.jwhz.witchshop.shop;

import me.jwhz.witchshop.WitchShop;
import me.jwhz.witchshop.gui.guis.VendorGUI;
import me.jwhz.witchshop.manager.Manager;
import me.jwhz.witchshop.utils.ItemUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.UUID;

public class Shop extends Manager<ShopItem> {

    public Shop() {

        super("shop");

        if (yamlConfiguration.isSet("Shop"))
            for (String key : yamlConfiguration.getConfigurationSection("Shop").getKeys(false))
                list.add(new ShopItem(this, "Shop." + key));

        new BukkitRunnable() {

            @Override
            public void run() {

                Iterator<ShopItem> items = getList().iterator();

                while (items.hasNext()) {

                    ShopItem item = items.next();

                    if (System.currentTimeMillis() >= item.getExpire()) {

                        item.delete();
                        items.remove();

                    }

                }

                Iterator<VendorGUI> guis = VendorGUI.guis.iterator();

                while (guis.hasNext()) {

                    VendorGUI gui = guis.next();

                    if (gui.closed) {

                        guis.remove();
                        continue;

                    }
                    gui.setupGUI();

                }

            }

        }.runTaskTimer(WitchShop.getInstance(), 0, 20);

    }

    public void create(ItemStack item, double cost, long expire) {

        UUID id = UUID.randomUUID();

        getYamlConfiguration().set("Shop." + id.toString() + ".item", ItemUtils.readItemStack(item));
        getYamlConfiguration().set("Shop." + id.toString() + ".cost", cost);
        getYamlConfiguration().set("Shop." + id.toString() + ".expire", System.currentTimeMillis() + expire);

        save();

        add(new ShopItem(this, "Shop." + id.toString()));

    }

    @Override
    public void add(Object obj) {

        list.add((ShopItem) obj);

    }

}
