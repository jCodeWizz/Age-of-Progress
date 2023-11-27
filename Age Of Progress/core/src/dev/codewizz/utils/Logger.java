package dev.codewizz.utils;

import java.time.LocalTime;

public class Logger {

	public static void error(String message) {
		Thread t = Thread.currentThread();
		LocalTime time = LocalTime.now();
		String s = t.getStackTrace()[2].getFileName().substring(0, t.getStackTrace()[2].getFileName().length()-5);
		System.err.println("[" + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() + "] [" + t.getName() + ":" + s + ":" + t.getStackTrace()[2].getLineNumber() + "] [ERROR]: " + message);
		
		
	}
	
	public static void log(String message) {
		Thread t = Thread.currentThread();
		LocalTime time = LocalTime.now();
		String s = t.getStackTrace()[2].getFileName().substring(0, t.getStackTrace()[2].getFileName().length()-5);
		System.out.println("[" + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() + "] [" + t.getName() + ":" + s + ":" + t.getStackTrace()[2].getLineNumber() + "] [INFO]: " + message);
	}
	
	public static void warn(String message) {
		Thread t = Thread.currentThread();
		LocalTime time = LocalTime.now();
		String s = t.getStackTrace()[2].getFileName().substring(0, t.getStackTrace()[2].getFileName().length()-5);
		System.err.println("[" + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond() + "] [" + t.getName() + ":" + s + ":" + t.getStackTrace()[2].getLineNumber() + "] [WARN]: " + message);
	}
}
