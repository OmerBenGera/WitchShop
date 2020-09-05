package me.jwhz.witchshop.command;

import me.jwhz.witchshop.WitchShop;
import me.jwhz.witchshop.command.commands.VendorCMD;
import me.jwhz.witchshop.config.ConfigHandler;
import me.jwhz.witchshop.config.ConfigValue;
import me.jwhz.witchshop.manager.Manager;

public class CommandManager extends Manager<CommandBase> {

    @ConfigValue(path = "Command.no permission")
    public String noPermission = "&cYou do not have permission to use this command!";

    public CommandManager() {

        super("messages");

        ConfigHandler.setPresets(this);
        ConfigHandler.reload(this);

        add(new VendorCMD());

    }

    @Override
    public void add(Object obj) {

        CommandBase command = (CommandBase) obj;

        WitchShop.getInstance().getCommand(command.getAnnotationInfo().command()).setExecutor(command);
        WitchShop.getInstance().getCommand(command.getAnnotationInfo().command()).setPermission(command.getAnnotationInfo().permission());
        WitchShop.getInstance().getCommand(command.getAnnotationInfo().command()).setPermissionMessage(noPermission);

        ConfigHandler.setPresets(command, getFile());
        ConfigHandler.reload(command, getFile());

        list.add(command);

    }
}
