package dev.codewizz.modding;

import java.io.InputStream;
import java.util.Scanner;

public class ModInfo {

	private String id = "";
	private String version = "";
	private String gameVersion = "";
	private String main = "";
	private String description = "";
	private String[] dependencies;

	public boolean initialized = false;
	
	public ModInfo(InputStream stream) {
		Scanner s = new Scanner(stream);
		
		while(s.hasNext()) {
			String l = s.nextLine();
			
        	int i = l.indexOf(':');
		
			String a = l.substring(0, i);
        	String b = l.substring(i+1, l.length());
        	
        	a = a.replaceAll("[^a-zA-Z.0-9-]", "");
			
        	if(a.equalsIgnoreCase("main")) {
        		main = b.replaceAll("[^a-zA-Z.0-9-]", "");
        	} else if(a.equalsIgnoreCase("version")) {
        		version = b.replaceAll("[^a-zA-Z.0-9-]", "");
        	} else if(a.equalsIgnoreCase("game-version")) {
        		gameVersion = b.replaceAll("[^a-zA-Z.0-9-]", "");
        	} else if(a.equalsIgnoreCase("description")) {
        		description = b.replaceAll("[^a-zA-Z.0-9-]", "");
        	} else if(a.equalsIgnoreCase("mod-id")) {
        		id = b.replaceAll("[^a-zA-Z.0-9-]", "");
        	} else if(a.equalsIgnoreCase("dependencies")) {
        		b = b.replaceAll("[^a-zA-Z.0-9-,]", "");
        		dependencies = b.split(",");
        	}
		}
		
		s.close();
	}
	
	public String[] getDependencies() {
		return dependencies;
	}

	@Override
	public String toString() {
		return "id: " + id + System.lineSeparator() + "version: " + version + System.lineSeparator() + "game-version: " + gameVersion + System.lineSeparator() + "main: " + main + System.lineSeparator() + "description: " + description;
	}

	public String getId() {
		return id;
	}

	public String getVersion() {
		return version;
	}

	public String getGameVersion() {
		return gameVersion;
	}

	public String getMain() {
		return main;
	}

	public String getDescription() {
		return description;
	}
}
