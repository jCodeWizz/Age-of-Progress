package dev.codewizz.input.console;

import dev.codewizz.world.World;

public interface CommandExecutor {

    String getUsage();
    boolean execute(String command, World world, String[] args);

}
