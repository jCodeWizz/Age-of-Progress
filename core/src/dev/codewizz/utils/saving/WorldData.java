package dev.codewizz.utils.saving;

import java.io.File;
import java.nio.file.Files;

import com.badlogic.gdx.Gdx;

import dev.codewizz.utils.Assets;
import dev.codewizz.world.World;

public class WorldData {
	
	private byte[] data;
	
	private World world;
	
	public WorldData(World world) {
		
	}
	
	public static void save(String worldName, WorldData b) {
		try {
			String path = Assets.folderSaves + "/" + worldName + "/";
			
			File folder = Gdx.files.absolute(path).file();
			folder.mkdir(); 
			
			File main = new File(path + "main.world");
			main.createNewFile();
			
			Files.write(main.toPath(), b.data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static WorldData open(String worldName) {
		String path = Assets.folderSaves + "/" + worldName;
		
		return null;
	}
}
