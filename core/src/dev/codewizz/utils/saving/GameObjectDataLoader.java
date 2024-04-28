package dev.codewizz.utils.saving;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import dev.codewizz.utils.Pair;
import dev.codewizz.utils.serialization.ByteUtils;
import dev.codewizz.world.GameObject;

public class GameObjectDataLoader {

	private HashMap<UUID, GameObjectData> toLoad;
	private HashMap<UUID, GameObject> loaded;
	
	public void loadFromData(byte[] total) {
		toLoad = new HashMap<>();
		int index = 0;
		
		while (index < total.length) {
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
		
		while (!toLoad.isEmpty()) {
			for(Map.Entry<UUID, GameObjectData> entry : toLoad.entrySet()) {
				GameObjectData data = entry.getValue();
				Pair<GameObject, Boolean> result = data.load(this);

				if (result.getTypeB()) {
					loaded.put(entry.getKey(), result.getTypeA());
					toLoad.remove(entry.getKey());
				}
			}
		}
	}

	public byte[] saveGameObjects() {

	}

	private void saveGameObject(GameObject object) {
		GameObjectData data = new GameObjectData(object);
	}

	public boolean isLoaded(UUID uuid) {
		return loaded.containsKey(uuid);
	}

	public GameObject getLoadedObject(UUID uuid) {
		return loaded.get(uuid);
	}

	public Map<UUID, GameObject> getLoaded() {
		return loaded;
	}
}
