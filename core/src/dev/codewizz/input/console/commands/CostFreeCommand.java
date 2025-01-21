package dev.codewizz.input.console.commands;

import com.badlogic.gdx.graphics.Color;
import dev.codewizz.input.console.CommandExecutor;
import dev.codewizz.input.console.Console;
import dev.codewizz.utils.Logger;
import dev.codewizz.world.World;
import dev.codewizz.world.objects.ConstructionObject;

public class CostFreeCommand implements CommandExecutor {

    @Override
    public String getUsage() {
        return "costfree {true|false}";
    }

    @Override
    public boolean execute(String command, World world, String[] args) {

        if (args.length == 0) {
            ConstructionObject.FREE = !ConstructionObject.FREE;
        } else {
            try {
                ConstructionObject.FREE = Boolean.parseBoolean(args[0]);
            } catch (Exception e) {
                Console.printLine("Couldn't parse '" + args[0] + "' to a boolean!", Color.RED);
                return false;
            }
        }

        return true;
    }
}
