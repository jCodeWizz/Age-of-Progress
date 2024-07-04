package dev.codewizz.input.console.commands;

import dev.codewizz.input.console.CommandExecutor;
import dev.codewizz.modding.JavaMod;
import dev.codewizz.modding.ModInfo;
import dev.codewizz.modding.Registers;
import dev.codewizz.utils.Logger;
import dev.codewizz.utils.Pair;
import dev.codewizz.world.Tile;
import dev.codewizz.world.World;
import java.util.HashMap;
import java.util.Map;

public class RegistersCommand implements CommandExecutor {

    @Override
    public boolean execute(String command, World world, String[] args) {

        if(args.length == 2) {
            if(args[0].equalsIgnoreCase("list")) {
                if(args[1].equalsIgnoreCase("mods")) {
                    Logger.log("Mod list: ");
                    for(String value : Registers.mods.keySet()) {
                        Logger.log(value);
                    }
                } else if(args[1].equalsIgnoreCase("tiles")) {
                    Logger.log("Tile list: ");
                    for(String value : Registers.tiles.keySet()) {
                        Logger.log(value);
                    }
                } else if(args[1].equalsIgnoreCase("objects")) {
                    Logger.log("Object list: ");
                    for(String value : Registers.objects.keySet()) {
                        Logger.log(value);
                    }
                } else if(args[1].equalsIgnoreCase("commands")) {
                    Logger.log("Command list: ");
                    for(String value : Registers.commands.keySet()) {
                        Logger.log(value);
                    }
                } else if(args[1].equalsIgnoreCase("tiles")) {
                    Logger.log("Tile list: ");
                    for(String value : Registers.tiles.keySet()) {
                        Logger.log(value);
                    }
                } else if(args[1].equalsIgnoreCase("events")) {
                    Logger.log("Event subscriber list: ");
                    for(String value : Registers.events.keySet()) {
                        Logger.log(value);
                    }
                }








            }




        }







        return false;
    }
}
