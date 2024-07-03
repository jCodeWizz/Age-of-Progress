package dev.codewizz.console;

import dev.codewizz.console.commands.TimeCommand;
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
    }

    public void start() {
        consoleThread = new Thread("console") {
            public void run() {
                scanner = new Scanner(System.in);

                while(Main.PLAYING || Main.RUNNING) {
                    try {
                        String command = scanner.nextLine();
                        Logger.log("Executing command: '" + command + "'!");

                        if(command.contains(" ")) {
                            String name = command.split(" ")[0];
                            String[] values = command.split(" ");
                            String[] args = Arrays.copyOfRange(values, 1, values.length);
                            if(Registers.commands.containsKey(name)) {
                                Registers.commands.get(name).execute(name, Main.inst.world, args);
                            } else {
                                Logger.error("couldn't find command '" + name + "'!");
                            }
                        } else {
                            if(Registers.commands.containsKey(command)) {
                                Registers.commands.get(command).execute(command, Main.inst.world, new String[] {});
                            } else {
                                Logger.error("couldn't find command '" + command + "'!");
                            }
                        }
                    } catch (NoSuchElementException ignored) {
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
