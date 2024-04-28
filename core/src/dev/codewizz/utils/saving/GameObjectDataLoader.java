package dev.codewizz.utils.saving;


import dev.codewizz.utils.Logger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import dev.codewizz.utils.Pair;
import dev.codewizz.utils.serialization.ByteUtils;
import dev.codewizz.world.GameObject;

public class GameObjectDataLoader {

	private HashMap<UUID, GameObjectData> toLoad;
	private HashMap<UUID, GameObject> loaded;
	private final HashMap<UUID, GameObjectData> toSave;
	private int size = 0;

	public GameObjectDataLoader() {
		toSave = new HashMap<>();
	}

	public void loadFromData(byte[] total) {
		toLoad = new HashMap<>();
		if(total.length <= 4) return;

		int index = 0;
		while (index < total.length) {
			int length = ByteUtils.toInteger(total, index);
			if(length == 0) break;

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
			for(Map.Entry<UUID, GameObjectData> entry : new HashSet<>(toLoad.entrySet())) {
				GameObjectData data = entry.getValue();
				Pair<GameObject, Boolean> result = data.load(this);

				if (result.getTypeB()) {
					loaded.put(entry.getKey(), result.getTypeA());
					toLoad.remove(entry.getKey());
				}
			}
		}
	}

	public byte[] getTotalBytes() {
		byte[] total = new byte[size * 2];


		int index = 0;
		for(GameObjectData data : toSave.values()) {
			byte[] bytes = data.getTotalBytes();
			byte[] lengthBytes = ByteUtils.toBytes(bytes.length);

			System.arraycopy(lengthBytes, 0, total, index, lengthBytes.length);
			index += lengthBytes.length;
			System.arraycopy(bytes, 0, total, index, bytes.length);

			index += bytes.length;
		}

		Logger.log("R: " + index);
		Logger.log("A: " + size);
		Logger.log("");
		return total;
	}

	public void save(GameObject object) {
		GameObjectData data = new GameObjectData(object);
		toSave.put(object.getUUID(), data);
		this.size += 4 + data.size;
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
