package dev.codewizz.input.console.commands;

import dev.codewizz.input.console.CommandExecutor;
import dev.codewizz.utils.Logger;
import dev.codewizz.world.World;
import dev.codewizz.world.objects.ConstructionObject;

public class CostFreeCommand implements CommandExecutor {

    @Override
    public boolean execute(String command, World world, String[] args) {

        if(args.length == 0) {
            ConstructionObject.FREE = !ConstructionObject.FREE;
        } else {
            try {
                ConstructionObject.FREE = Boolean.parseBoolean(args[0]);
            } catch(Exception e) {
                Logger.error("Couldn't parse '" + args[0] + "' to a boolean!");
                return false;
            }
        }

        return true;
    }
}
