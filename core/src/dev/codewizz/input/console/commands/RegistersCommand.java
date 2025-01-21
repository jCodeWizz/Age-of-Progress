package dev.codewizz.input.console.commands;

import dev.codewizz.input.console.CommandExecutor;
import dev.codewizz.input.console.Console;
import dev.codewizz.modding.Registers;
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
                    Console.printLine("Mod list: ");
                    for (String value : Registers.mods.keySet()) {
                        Console.printLine(value);
                    }
                    return true;
                } else if (args[1].equalsIgnoreCase("tiles")) {
                    Console.printLine("Tile list: ");
                    for (String value : Registers.tiles.keySet()) {
                        Console.printLine(value);
                    }
                    return true;
                } else if (args[1].equalsIgnoreCase("objects")) {
                    Console.printLine("Object list: ");
                    for (String value : Registers.objects.keySet()) {
                        Console.printLine(value);
                    }
                    return true;
                } else if (args[1].equalsIgnoreCase("commands")) {
                    Console.printLine("Command list: ");
                    for (String value : Registers.commands.keySet()) {
                        Console.printLine(value);
                    }
                    return true;
                } else if (args[1].equalsIgnoreCase("events")) {
                    Console.printLine("Event subscriber list: ");
                    for (String value : Registers.events.keySet()) {
                        Console.printLine(value);
                    }
                    return true;
                } else if (args[1].equalsIgnoreCase("recipes")) {
                    Console.printLine("Recipe list: ");
                    for (String value : Registers.recipes.keySet()) {
                        Console.printLine(value);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
