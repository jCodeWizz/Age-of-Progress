package dev.codewizz.input.console.commands;

import com.badlogic.gdx.graphics.Color;
import dev.codewizz.input.console.CommandExecutor;
import dev.codewizz.input.console.Console;
import dev.codewizz.world.World;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.items.ItemType;

public class FillInvCommand implements CommandExecutor {

    @Override
    public String getUsage() {
        return "fillinv {item_count:1000}";
    }

    @Override
    public boolean execute(String command, World world, String[] args) {

        int count = 1000;

        if (args.length > 0) {
            try {
                count = Integer.parseInt(args[0]);
            } catch (Exception e) {
                Console.printLine("Couldn't parse number, adding 1000!", Color.RED);
            }
        }

        for (ItemType type : ItemType.types.values()) {
            world.settlement.inventory.addItem(new Item(type, count));
        }
        return true;
    }
}
