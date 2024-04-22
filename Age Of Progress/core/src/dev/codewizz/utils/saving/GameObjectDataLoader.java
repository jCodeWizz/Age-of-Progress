package dev.codewizz.utils.saving;


import java.util.HashMap;
import java.util.Map;

import dev.codewizz.utils.serialization.ByteUtils;
import dev.codewizz.world.GameObject;

public class GameObjectDataLoader {

	private HashMap<Integer, GameObjectData> toLoad;
	private HashMap<Integer, GameObject> loaded;
	
	public void loadFromData(byte[] total) {
		toLoad = new HashMap<>();
		int index = 0;
		
		while (index < total.length) {
			byte[] lengthData = new byte[4];
			lengthData[0] = total[index];
			lengthData[1] = total[index+1];
			lengthData[2] = total[index+2];
			lengthData[3] = total[index+3];
			
			int length = ByteUtils.toInteger(total, index);
			
			byte[] data = new byte[length];
			System.arraycopy(total, index + 4, data, 0, length);
			
			GameObjectData object = new GameObjectData(data);
			toLoad.put(object.getUUID(), object);
			
			index += (length + 4);
		}
	}
	
	public void loadGameObjects() {
		loaded = new HashMap<>();
		
		for(Map.Entry<Integer, GameObjectData> entry : toLoad.entrySet()) {
			GameObjectData data = entry.getValue();
			loaded.put(entry.getKey(), data.load());
		}
	}
}
