package dev.codewizz.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
			currentFile = Gdx.files.external(Assets.pathFolderLogs + "LOG-" + date + ".txt").file();
			
			if(currentFile.createNewFile()) {
				writer = new BufferedWriter(new FileWriter(currentFile));
				open = true;
				Logger.log("Opened log file!");
			} else {
				Logger.error("Couldn't create a log file!");
			}
			
			for(File file : Assets.folderLogs.listFiles()) {
				long diff = new Date().getTime() - file.lastModified();
				
				if(diff > 24 * 60 * 60 * 1000) {
					file.delete();
				}
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

	public static String time() {
		LocalDateTime now = LocalDateTime.now();
		return TIME_FORMAT.format(now);
	}
	
	private static String prefix() {
		Thread t = Thread.currentThread();
		String s = t.getStackTrace()[3].getFileName().substring(0, t.getStackTrace()[3].getFileName().length()-5);
		return "[" + time() + "] [" + t.getName() + ":" + s + ":" + t.getStackTrace()[3].getLineNumber() + "] ";
	}
	
	public static void error(Object message) {
		String prefix = prefix();
		String total = prefix += "[ERROR]: " + message.toString();
		System.err.println(total);
		write(total);
	}
	
	public static void log(Object message) {
		String prefix = prefix();
		String total = prefix += "[INFO]: " + message.toString();
		System.out.println(total);
		write(total);
	}
	
	public static void warn(Object message) {
		String prefix = prefix();
		String total = prefix += "[WARN]: " + message.toString();
		System.err.println(total);
		write(total);
	}
}
