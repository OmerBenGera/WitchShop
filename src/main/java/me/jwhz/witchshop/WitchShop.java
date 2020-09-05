package me.jwhz.witchshop;

import me.jwhz.witchshop.command.CommandManager;
import me.jwhz.witchshop.shop.Shop;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class WitchShop extends JavaPlugin {

    private static Economy econ = null;
    private static WitchShop instance;

    public Shop shop;
    public CommandManager commandManager;

    @Override
    public void onEnable() {

        instance = this;

        if(!setupEconomy()){

            System.out.println("Could not find economy plugin! Disabling.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;

        }

        shop = new Shop();

        commandManager = new CommandManager();

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static WitchShop getInstance() {
        return instance;
    }

    public static Economy getEconomy() {
        return econ;
    }
}

