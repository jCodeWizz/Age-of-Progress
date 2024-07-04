package dev.codewizz.input.console;

import dev.codewizz.input.console.commands.*;
import dev.codewizz.main.Main;
import dev.codewizz.modding.Registers;
import dev.codewizz.utils.Logger;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console {

    private Scanner scanner;
    private Thread consoleThread;

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
        consoleThread = new Thread("console") {
            public void run() {
                scanner = new Scanner(System.in);


                Logger.log(Main.PLAYING + " " + Main.RUNNING + " " + this.isInterrupted());


                while((Main.PLAYING || Main.RUNNING) && !this.isInterrupted()) {
                    try {
                        String command = scanner.nextLine();
                        Logger.log("Executing command: '" + command + "'!");
                        boolean success = false;
                        if(command.contains(" ")) {
                            String name = command.split(" ")[0];
                            String[] values = command.split(" ");
                            String[] args = Arrays.copyOfRange(values, 1, values.length);
                            if(Registers.commands.containsKey(name)) {
                                success = Registers.commands.get(name).execute(name, Main.inst.world, args);
                            } else {
                                Logger.error("couldn't find command '" + name + "'!");
                            }
                        } else {
                            if(Registers.commands.containsKey(command)) {
                                success = Registers.commands.get(command).execute(command, Main.inst.world, new String[] {});
                            } else {
                                Logger.error("couldn't find command '" + command + "'!");
                            }
                        }

                        if(success) {
                            Logger.log("Command successfully executed!");
                        } else {
                            Logger.error("Command could not be executed!");
                        }

                    } catch (NoSuchElementException ignored) {
                        Logger.log("V: " + Main.PLAYING + " " + Main.RUNNING);
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
    }
}
