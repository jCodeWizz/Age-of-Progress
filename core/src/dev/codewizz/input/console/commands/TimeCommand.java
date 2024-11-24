package dev.codewizz.input.console.commands;

import dev.codewizz.input.console.CommandExecutor;
import dev.codewizz.main.Main;
import dev.codewizz.world.Nature;
import dev.codewizz.world.World;

public class TimeCommand implements CommandExecutor {

    private final static String DAY = "DAY";
    private final static String NIGHT = "NIGHT";

    @Override
    public String getUsage() {
        return "time [DAY|NIGHT]";
    }

    @Override
    public boolean execute(String command, World world, String[] args) {
        if (args.length > 0) {

            if (args[0].equalsIgnoreCase(DAY)) {
                world.nature.timeCounter = 0;
                world.nature.transition = false;
                world.nature.day = true;
                world.nature.onDay();
                world.nature.light = Nature.DAY_LIGHT;
                Main.inst.renderer.setAmbientLight(world.nature.light);
                return true;
            }
            if (args[0].equalsIgnoreCase(NIGHT)) {
                world.nature.timeCounter = 0;
                world.nature.transition = false;
                world.nature.day = false;
                world.nature.onNight();
                world.nature.light = Nature.NIGHT_LIGHT;
                Main.inst.renderer.setAmbientLight(world.nature.light);
                return true;
            }
        }

        return false;
    }
}
