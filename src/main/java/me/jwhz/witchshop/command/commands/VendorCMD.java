package me.jwhz.witchshop.command.commands;

import me.jwhz.witchshop.command.CommandBase;
import me.jwhz.witchshop.gui.guis.VendorGUI;
import me.jwhz.witchshop.shop.Time;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandBase.Info(
        command = "vendor"
)
public class VendorCMD extends CommandBase {

    @Override
    public void onCommand(CommandSender sender, String[] args) {

        if (args.length == 0) {

            new VendorGUI((Player) sender);
            return;

        }
        Player player = (Player) sender;

        if (args[0].equalsIgnoreCase("additem") && sender.hasPermission("Vendor.admin")) {

            if (args.length < 3) {

                sender.sendMessage(ChatColor.RED + "Usage: /vendor additem <cost> <expire after>");
                return;

            }

            if (player.getItemInHand() == null) {

                player.sendMessage(ChatColor.RED + "You are not holding an item!");
                return;

            }

            double cost = 0;

            try {

                cost = Double.parseDouble(args[1]);

            } catch (Exception e) {

                player.sendMessage(ChatColor.RED + "Invalid number entered!");
                return;

            }

            long expire = Time.getTimeToAdd(args[2]);

            if(expire <= 0){

                player.sendMessage(ChatColor.RED + "Please enter a proper expire time.");
                return;

            }

            core.shop.create(player.getItemInHand(), cost, expire);

            player.sendMessage(ChatColor.GREEN + "You have added an item to the vendor shop!");

        }

    }

}
