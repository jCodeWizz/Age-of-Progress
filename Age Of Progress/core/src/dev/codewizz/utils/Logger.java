package dev.codewizz.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.badlogic.gdx.Gdx;

public class Logger {

	private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss"); 
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"); 
	
	private static File currentFile;
	private static boolean open = false;
	private static BufferedWriter writer;
	
	public static void setup() {
		try {
			LocalDateTime now = LocalDateTime.now();
			String date = DATE_FORMAT.format(now);
			//currentFile = Gdx.files.external(Assets.pathFolderLogs + "LOG-" + date + ".txt").file();
			
			if(currentFile.createNewFile()) {
				writer = new BufferedWriter(new FileWriter(currentFile));
				open = true;
				Logger.log("Opened log file!");
			} else {
				Logger.error("Couldn't create a log file!");
			}
		} catch (Exception e) {
			Logger.error("Couldn't create a log file!");
		}
	}
	
	private static void write(String line) {
		try {
			if(open) {
				writer.write(line);
				writer.newLine();
				writer.flush();
			}
		} catch(Exception e) { }
	}
	
	private static String prefix() {
		Thread t = Thread.currentThread();
		LocalTime now = LocalTime.now();
		String s = t.getStackTrace()[3].getFileName().substring(0, t.getStackTrace()[3].getFileName().length()-5);
		String time = TIME_FORMAT.format(now);
		return "[" + time + "] [" + t.getName() + ":" + s + ":" + t.getStackTrace()[3].getLineNumber() + "] ";
	}
	
	public static void error(String message) {
		String prefix = prefix();
		String total = prefix += "[ERROR]: " + message;
		System.err.println(total);
		write(total);
	}
	
	public static void log(String message) {
		String prefix = prefix();
		String total = prefix += "[INFO]: " + message;
		System.out.println(total);
		write(total);
	}
	
	public static void warn(String message) {
		String prefix = prefix();
		String total = prefix += "[WARN]: " + message;
		System.err.println(total);
		write(total);
	}
}
