package dev.codewizz.utils.saving;

import dev.codewizz.utils.Logger;
import dev.codewizz.utils.Pair;
import dev.codewizz.utils.serialization.ByteUtils;
import dev.codewizz.world.GameObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameObjectData {

	private List<byte[]> data;
	private int size = 0;
	private List<Byte> current;

	private Class<? extends GameObject> type;
	private UUID uuid;

	public GameObjectData(GameObject object) {
		data = new ArrayList<>();
		current = new ArrayList<>();

		save(object);
	}

	@SuppressWarnings("unchecked")
	public GameObjectData(byte[] total) {
		data = new ArrayList<>();

		int index = 0;

		while (index < total.length) {
			int length = ByteUtils.toInteger(total, index);
			byte[] data = new byte[length];

			System.arraycopy(total, index + 4, data, 0, length);
			this.data.add(data);
			index += length + 4;
		}
		
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
	
	public byte[] getTotalBytes() {
		byte[] total = new byte[size + 4 * this.data.size()];

		int index = 0;
        for (byte[] data : this.data) {
            byte[] length = ByteUtils.toBytes(data.length, 4);

            total[index] = length[0];
            total[index + 1] = length[1];
            total[index + 2] = length[2];
            total[index + 3] = length[3];

            System.arraycopy(data, 0, total, index + 4, data.length);
            index += data.length + 4;
        }
		
		return total;
	}

	public Pair<GameObject, Boolean> load(GameObjectDataLoader loader) {
		GameObject object;
		try {
			object = type.getConstructor().newInstance();
			boolean success = load(loader, object);
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

	public void addList(List<Byte> data) {
		byte[] array = new byte[data.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = data.get(i);
		}

		this.data.add(array);
		size += array.length;
	}

	public void end() {
		addList(current);
		current = new ArrayList<>();
	}

	public void addInteger(int value) {
		byte[] r = ByteUtils.toBytes(value, 4);
        for (byte b : r) {
            current.add(b);
        }
	}

	public void addFloat(float value) {
		byte[] r = ByteUtils.toBytes(value);
        for (byte b : r) {
            current.add(b);
        }
	}

	public void addArray(byte[] value) {
        for (byte b : value) {
            current.add(b);
        }
	}

	public void addByte(byte value) {
		current.add(value);
	}

	public void addString(String s) {
		byte[] data = ByteUtils.toBytes(s);
        for (byte datum : data) {
            current.add(datum);
        }
	}

	public List<byte[]> getDataList() {
		return data;
	}

	public byte[] take() {
		return data.remove(0);
	}

	public UUID getUUID() {
		return uuid;
	}
}
