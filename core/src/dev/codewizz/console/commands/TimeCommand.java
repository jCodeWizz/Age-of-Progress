package dev.codewizz.console.commands;

import dev.codewizz.console.CommandExecutor;
import dev.codewizz.world.World;

public class TimeCommand implements CommandExecutor {

    private final static String DAY = "DAY";
    private final static String NIGHT = "NIGHT";

    @Override
    public boolean execute(String command, World world, String[] args) {
        if(args.length > 0) {

            if(args[0].equalsIgnoreCase(DAY)) {
                world.nature.timeCounter = 0;
                world.nature.transition = false;
                return true;
            }
            if(args[0].equalsIgnoreCase(NIGHT)) {
                world.nature.timeCounter = 0;
                world.nature.transition = false;
                return true;
            }
        }

        return false;
    }
}
