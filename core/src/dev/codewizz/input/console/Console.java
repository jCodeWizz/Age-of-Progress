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

    public void start() {
        running = true;
        consoleThread = new Thread("console") {
            public void run() {
                reader = new BufferedReader(new InputStreamReader(System.in));

                while ((Main.PLAYING || Main.RUNNING) && running && !Thread.currentThread().isInterrupted()) {
                    try {
                        if (reader.ready()) {
                            String command = reader.readLine();
                            Logger.log("Executing command: '" + command + "'!");
                            boolean success = false;
                            if (command.contains(" ")) {
                                String name = command.split(" ")[0];
                                String[] values = command.split(" ");
                                String[] args = Arrays.copyOfRange(values, 1, values.length);
                                if (Registers.commands.containsKey(name)) {
                                    success = Registers.commands.get(name).execute(name, Main.inst.world, args);
                                } else {
                                    Logger.error("couldn't find command '" + name + "'!");
                                }
                            } else {
                                if (Registers.commands.containsKey(command)) {
                                    success = Registers.commands.get(command).execute(command, Main.inst.world, new String[] {});
                                } else {
                                    Logger.error("couldn't find command '" + command + "'!");
                                }
                            }

                            if (success) {
                                Logger.log("Command successfully executed!");
                            } else {
                                Logger.error("Command could not be executed!");
                                if (Registers.commands.containsKey(command)) {
                                    Logger.log("Usage: " + Registers.commands.get(command).getUsage());
                                }                           }
                        } else {
                            Thread.sleep(100);
                        }
                    } catch (IOException e) {
                        if (running) {
                            Logger.error("IOException while reading command: ");
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        break;
                    } catch (Exception e) {
                        Logger.error("Exception while executing command: ");
                        e.printStackTrace();
                    }
                }
            }
        };
        consoleThread.start();
    }

    public void stop() {
        consoleThread.interrupt();
        try {
            reader.close();
        } catch (IOException ignored) {
        }
    }
}