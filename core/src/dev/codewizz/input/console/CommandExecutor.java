package dev.codewizz.input.console;

import dev.codewizz.world.World;

public interface CommandExecutor {

    boolean execute(String command, World world, String[] args);

}
