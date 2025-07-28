package dev.codewizz.input.console.commands;

import dev.codewizz.input.console.CommandExecutor;
import dev.codewizz.input.console.Console;
import dev.codewizz.modding.Registers;
import dev.codewizz.world.World;

public class HelpCommand implements CommandExecutor {

    @Override
    public String getUsage() {
        return "help";
    }

    @Override
    public boolean execute(String command, World world, String[] args) {
        for (CommandExecutor e : Registers.commands.values()) {
            Console.printLine(e.getUsage());
        }
        return true;
    }
}
