package dev.codewizz.gfx.gui.menus;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import dev.codewizz.gfx.gui.elements.UILabel;
import dev.codewizz.gfx.gui.elements.UITextField;
import dev.codewizz.gfx.gui.layers.GameLayer;
import dev.codewizz.input.console.Console;
import dev.codewizz.main.Main;
import dev.codewizz.modding.Registers;
import dev.codewizz.utils.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConsoleMenu extends Menu {

    private UITextField input;
    private InputListener clickListener;
    private InputListener escapeKeyListener;

    public List<UILabel> lines = new ArrayList<>();

    Table outputTable;

    public ConsoleMenu(Stage stage, GameLayer layer) {
        super(stage, layer);
    }

    public void refresh() {
        outputTable.clear();

        for (UILabel line : lines) {
            outputTable.add(line);
            outputTable.row();
        }
    }

    @Override
    protected void setup() {
        Console.menu = this;

        Table main = new Table();
        main.setBackground(createBackground(0.5f));
        base.add(main).expand().center().top().padTop(10);

        input = UITextField.create("command. . .");
        main.add(input);
        main.row();

        outputTable = new Table();
        main.add(outputTable).expand().fill();


        clickListener = new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Actor target = event.getTarget();
                if (!(target instanceof TextField)) {
                    stage.setKeyboardFocus(null);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        };

        escapeKeyListener = new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.GRAVE) {
                    stage.setKeyboardFocus(null);
                    stage.setScrollFocus(null);
                    close();
                    return false;
                }

                if (keycode == Input.Keys.ENTER) {
                    execute(input.getText());
                    input.setText("");
                    return true;
                }
                return false;
            }
        };
    }

    private void execute(String command) {
        boolean success = false;

        String[] args = command.split(" ");
        String cmd = args[0];
        String[] values = Arrays.copyOfRange(args, 1, args.length);
        if (Registers.commands.containsKey(cmd)) {
            success = Registers.commands.get(cmd).execute(cmd, Main.inst.world, values);
        } else {
            Logger.error("couldn't find command '" + cmd + "'!");
        }
        if (success) {
            Logger.log("Command successfully executed: " + command);
        } else {
            Logger.error("Command could not be executed: " + command);
            Console.printLine("Command could not be executed: " + command, Color.RED);
            if (Registers.commands.containsKey(command)) {
                Console.printLine("Usage: " + Registers.commands.get(command).getUsage());
            }
        }
    }

    @Override
    public void onOpen() {
        stage.addListener(clickListener);
        stage.addListener(escapeKeyListener);
    }

    @Override
    public void onClose() {
        stage.removeListener(clickListener);
        stage.removeListener(escapeKeyListener);
        stage.setKeyboardFocus(null);
        stage.setScrollFocus(null);

        input.setText("");
    }
}
