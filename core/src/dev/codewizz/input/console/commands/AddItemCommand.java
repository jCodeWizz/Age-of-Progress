package dev.codewizz.input.console.commands;

import com.badlogic.gdx.graphics.Color;
import dev.codewizz.input.console.CommandExecutor;
import dev.codewizz.input.console.Console;
import dev.codewizz.utils.Logger;
import dev.codewizz.world.World;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.items.ItemType;

public class AddItemCommand implements CommandExecutor {

    @Override
    public String getUsage() {
        return "additem [item_id] {item_count:1}";
    }

    @Override
    public boolean execute(String command, World world, String[] args) {
        if (args.length > 0) {
            String id = args[0].contains(":") ? args[0] : "aop:" + args[0];
            int amount = 1;

            if (args.length > 1) {
                try {
                    amount = Integer.parseInt(args[1]);
                } catch (NumberFormatException e) {
                    Console.printLine("Couldn't parse " + args[1] + " to a number!", Color.RED);
                }
            }

            if (!ItemType.types.containsKey(id)) {
                Console.printLine("Couldn't find " + id + " as an item!", Color.RED);
                return false;
            }

            world.settlement.inventory.addItem(new Item(ItemType.types.get(id), amount));

            return true;
        }
        return false;
    }
}
