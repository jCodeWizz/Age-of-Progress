package dev.codewizz.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	public static final String PATH = "jdbc:sqlite:sample.db";

	private Connection connection;
	private boolean open;
	
	public Database() {

	}

	public void open() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:test.db");
			open = true;
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			open = false;
		}
		System.out.println("Opened database successfully");
	}
	
	public boolean setup() {
		if(!open) open();
		
		
		if(open) {
			try {
				Statement statement = connection.createStatement();
				String command = "CREATE TABLE IF NOT EXISTS PLAYERS " +
									"(ID INT PRIMARY KEY NOT NULL," +
									"NAME TEXT NOT NULL," +
									"IP TEXT NOT NULL)";
				
				statement.executeUpdate(command);
				statement.close();
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean insertPlayer(String name, String ip) {
		try {
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			String command = "INSERT INTO PLAYERS (NAME, IP) " +
								"VALUES ('" + name + "', '" + ip + "')";
			
			System.out.println(command);
			
			statement.executeUpdate(command);
			statement.close();
			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public void close() {
		open = false;
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
