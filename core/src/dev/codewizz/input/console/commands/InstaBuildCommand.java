package dev.codewizz.input.console.commands;

import dev.codewizz.input.MouseInput;
import dev.codewizz.input.console.CommandExecutor;
import dev.codewizz.world.World;
import dev.codewizz.world.objects.ConstructionObject;

public class InstaBuildCommand implements CommandExecutor {

    @Override
    public boolean execute(String command, World world, String[] args) {
        if(MouseInput.hoveringOverCell != null && MouseInput.hoveringOverCell.getObject() != null && MouseInput.hoveringOverCell.getObject().getId().equals("aop:construction")) {
            ConstructionObject object = (ConstructionObject) MouseInput.hoveringOverCell.getObject();

            //TODO: figure out a way to finish builds. (Clear task ass wel etc.)

            return true;
        }

        return false;
    }
}
