package dev.codewizz.input.console.commands;

import dev.codewizz.input.console.CommandExecutor;
import dev.codewizz.modding.Registers;
import dev.codewizz.utils.Logger;
import dev.codewizz.world.World;

public class RegistersCommand implements CommandExecutor {

    @Override
    public String getUsage() {
        return "registers [list] [register_name]";
    }

    @Override
    public boolean execute(String command, World world, String[] args) {

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("list")) {
                if (args[1].equalsIgnoreCase("mods")) {
                    Logger.log("Mod list: ");
                    for (String value : Registers.mods.keySet()) {
                        Logger.log(value);
                    }
                    return true;
                } else if (args[1].equalsIgnoreCase("tiles")) {
                    Logger.log("Tile list: ");
                    for (String value : Registers.tiles.keySet()) {
                        Logger.log(value);
                    }
                    return true;
                } else if (args[1].equalsIgnoreCase("objects")) {
                    Logger.log("Object list: ");
                    for (String value : Registers.objects.keySet()) {
                        Logger.log(value);
                    }
                    return true;
                } else if (args[1].equalsIgnoreCase("commands")) {
                    Logger.log("Command list: ");
                    for (String value : Registers.commands.keySet()) {
                        Logger.log(value);
                    }
                    return true;
                } else if (args[1].equalsIgnoreCase("tiles")) {
                    Logger.log("Tile list: ");
                    for (String value : Registers.tiles.keySet()) {
                        Logger.log(value);
                    }
                    return true;
                } else if (args[1].equalsIgnoreCase("events")) {
                    Logger.log("Event subscriber list: ");
                    for (String value : Registers.events.keySet()) {
                        Logger.log(value);
                    }
                    return true;
                } else if (args[1].equalsIgnoreCase("recipes")) {
                    Logger.log("Recipe list: ");
                    for (String value : Registers.recipes.keySet()) {
                        Logger.log(value);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
