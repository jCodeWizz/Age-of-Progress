package dev.codewizz.input.console.commands;

import dev.codewizz.input.console.CommandExecutor;
import dev.codewizz.modding.Registers;
import dev.codewizz.modding.events.Reason;
import dev.codewizz.utils.Logger;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.World;

public class CreateObjectCommand implements CommandExecutor {

    @Override
    public boolean execute(String command, World world, String[] args) {
        if (args.length > 0) {
            String id = args[0];

            if(!id.contains(":")) {
                id = "aop:" + id;
            }

            int amount = 1;
            int x = 0;
            int y = 0;

            if (args.length > 2) {
                try {
                    x = Integer.parseInt(args[1]);
                    y = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    Logger.error("Could not parse x or y '" + args[1] + "', '" + args[2] + "', using default values!");
                }

                if (args.length > 3) {
                    try {
                        amount = Integer.parseInt(args[3]);
                    } catch (NumberFormatException e) {
                        Logger.error("Couldn't parse amount '" + args[3] + "', using default amount 1!");
                    }
                }
            }

            for(int i = 0; i < amount; i++) {
                GameObject object = Registers.createGameObject(id, x, y);
                if(object != null) {
                    world.addObject(object, Reason.COMMAND);
                } else {
                    Logger.error("Something went wrong during command execution.");
                    return false;
                }
            }

            return true;
        }

        return false;
    }
}
