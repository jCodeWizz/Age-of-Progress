package dev.codewizz.input.console;

import com.badlogic.gdx.graphics.Color;
import dev.codewizz.gfx.gui.elements.UILabel;
import dev.codewizz.gfx.gui.menus.ConsoleMenu;
import dev.codewizz.input.console.commands.*;
import dev.codewizz.main.Main;
import dev.codewizz.modding.Registers;
import dev.codewizz.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Console {

    public static ConsoleMenu menu;

    public void register() {
        Registers.registerCommand("time", new TimeCommand());
        Registers.registerCommand("costfree", new CostFreeCommand());
        Registers.registerCommand("instabuild", new InstaBuildCommand());
        Registers.registerCommand("createobject", new CreateObjectCommand());
        Registers.registerCommand("registers", new RegistersCommand());
        Registers.registerCommand("removeobject", new RemoveObjectCommand());
        Registers.registerCommand("additem", new AddItemCommand());
    }

    public static void printLine(String text) {
        printLine(text, Color.WHITE);
    }

    public static void printLine(String text, Color color) {
        if (menu == null) return;
        UILabel l = UILabel.create(text, UILabel.smallStyle);
        l.setColor(color);

        menu.lines.add(l);
        menu.refresh();
    }
}