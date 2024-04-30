package dev.codewizz.utils.saving;

import dev.codewizz.utils.Logger;
import dev.codewizz.utils.Pair;
import dev.codewizz.utils.serialization.ByteUtils;
import dev.codewizz.world.GameObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameObjectData extends DataSaveLoader{

	private Class<? extends GameObject> type;
	private UUID uuid;

	public GameObjectData(GameObject object) {
		save(object);
	}

	@SuppressWarnings("unchecked")
	public GameObjectData(byte[] total) {
		super(total);
		
		byte[] data = this.take();
		uuid = ByteUtils.toUUID(data, 0);
		try {
			type = (Class<? extends GameObject>) Class.forName(ByteUtils.toString(data, 16));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public GameObjectData() { }

	public GameObjectData copy() {
		GameObjectData data = new GameObjectData();
		data.uuid = this.uuid;
		data.type = this.type;
		data.data = this.data;
		data.size = this.size;
		data.current = this.current;

		return data;
	}
	
	public Pair<GameObject, Boolean> load(GameObjectDataLoader loader) {
		GameObject object;
		try {
			object = type.getConstructor().newInstance();
			boolean success = load(loader, object);
			object.setUUID(uuid);
			return new Pair<>(object, success);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			Logger.error("Couldn't load " + type.toString() + " because: ");
			e.printStackTrace();
		}
		return null;
	}

	private void save(GameObject object) {
		type = object.getClass();
		uuid = object.getUUID();

		String typeString = type.toString().substring(6);

		addArray(ByteUtils.toBytes(uuid));
		addString(typeString);
		end();

		object.save(this);
	}

	private boolean load(GameObjectDataLoader loader, GameObject object) {
		return object.load(loader, this.copy(), true);
	}

	public UUID getUUID() {
		return uuid;
	}
}
