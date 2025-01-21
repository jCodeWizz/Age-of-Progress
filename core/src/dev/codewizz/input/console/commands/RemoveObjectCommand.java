package dev.codewizz.input.console.commands;

import dev.codewizz.input.console.CommandExecutor;
import dev.codewizz.input.console.Console;
import dev.codewizz.utils.Logger;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.World;

public class RemoveObjectCommand implements CommandExecutor {

    @Override
    public String getUsage() {
        return "removeobject [object_id]";
    }

    @Override
    public boolean execute(String command, World world, String[] args) {
        if (args.length > 0) {
            String id = args[0].contains(":") ? args[0] : "aop:" + args[0];

            int counter = 0;
            for (GameObject object : world.getGameObjects()) {
                if (object.getId().equalsIgnoreCase(id)) {
                    counter++;
                    object.destroy();
                }
            }

            Console.printLine("Removed " + counter + " objects!");
            return true;
        }

        return false;
    }
}
