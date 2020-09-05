package me.jwhz.witchshop.shop;

import me.jwhz.witchshop.config.ConfigFile;
import me.jwhz.witchshop.manager.ManagerObject;
import me.jwhz.witchshop.utils.ItemUtils;
import org.bukkit.inventory.ItemStack;

public class ShopItem extends ManagerObject<String> {

    private String path;
    private ConfigFile configFile;

    public ShopItem(ConfigFile configFile, String path) {

        this.path = path;
        this.configFile = configFile;

    }

    public double getCost() {

        return configFile.getYamlConfiguration().getDouble(path + ".cost");

    }

    public ItemStack getItem() {

        return ItemUtils.readString(configFile.getYamlConfiguration().getString(path + ".item"));

    }

    public long getExpire(){

        return configFile.getYamlConfiguration().getLong(path + ".expire");

    }

    public void delete(){

        configFile.getYamlConfiguration().set(path, null);

        configFile.save();

    }

    @Override
    public String getIdentifier() {

        return path;

    }
}
