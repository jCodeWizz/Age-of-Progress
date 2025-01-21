package dev.codewizz.input.console;

import dev.codewizz.input.console.commands.*;
import dev.codewizz.main.Main;
import dev.codewizz.modding.Registers;
import dev.codewizz.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Console {

    private BufferedReader reader;
    private Thread consoleThread;
    private volatile boolean running;

    public void register() {
        Registers.registerCommand("time", new TimeCommand());
        Registers.registerCommand("costfree", new CostFreeCommand());
        Registers.registerCommand("instabuild", new InstaBuildCommand());
        Registers.registerCommand("createobject", new CreateObjectCommand());
        Registers.registerCommand("registers", new RegistersCommand());
        Registers.registerCommand("removeobject", new RemoveObjectCommand());
        Registers.registerCommand("additem", new AddItemCommand());
    }
}