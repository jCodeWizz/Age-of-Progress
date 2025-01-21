package dev.codewizz.input.console.commands;

import dev.codewizz.input.console.CommandExecutor;
import dev.codewizz.input.console.Console;
import dev.codewizz.modding.Registers;
import dev.codewizz.modding.events.Reason;
import dev.codewizz.world.GameObject;
import dev.codewizz.world.World;

import com.badlogic.gdx.graphics.Color;

public class CreateObjectCommand implements CommandExecutor {

    @Override
    public String getUsage() {
        return "createobject [object_id] {x:0} {y:0} {object_count:1}";
    }

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
                    Console.printLine("Could not parse x or y '" + args[1] + "', '" + args[2] + "', using default values!", Color.RED);
                }

                if (args.length > 3) {
                    try {
                        amount = Integer.parseInt(args[3]);
                    } catch (NumberFormatException e) {
                        Console.printLine("Couldn't parse amount '" + args[3] + "', using default amount 1!", Color.RED);
                    }
                }
            }

            for(int i = 0; i < amount; i++) {
                GameObject object = Registers.createGameObject(id, x, y);
                if(object != null) {
                    world.addObject(object, Reason.COMMAND);
                } else {
                    Console.printLine("Something went wrong during command execution.", Color.RED);
                    return false;
                }
            }

            return true;
        }

        return false;
    }
}
