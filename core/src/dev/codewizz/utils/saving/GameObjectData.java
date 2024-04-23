package dev.codewizz.utils.saving;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dev.codewizz.utils.serialization.ByteUtils;
import dev.codewizz.world.GameObject;

public class GameObjectData {

	private static final Random RANDOM = new Random();

	private List<byte[]> data;
	private int size = 0;
	private List<Byte> current;

	private Class<? extends GameObject> type;
	private int uuid;

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
		
		byte[] data = this.data.remove(0);
		uuid = ByteUtils.toInteger(data, 0, 4);
		try {
			type = (Class<? extends GameObject>) Class.forName(ByteUtils.toString(data, 4));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public byte[] getTotalBytes() {
		byte[] total = new byte[size + 4 * this.data.size()];

		int index = 0;
		for (int i = 0; i < this.data.size(); i++) {
			byte[] data = this.data.get(i);

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

	public GameObject load() {
		GameObject object;
		try {
			object = type.getConstructor().newInstance();
			load(object);
			return object;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void save(GameObject object) {
		type = object.getClass();
		uuid = RANDOM.nextInt();

		String typeString = type.toString().substring(6);

		addInteger(uuid);
		addString(typeString);
		end();

		object.save(this);
	}

	private void load(GameObject object) {
		object.load(this);
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
		for (int i = 0; i < r.length; i++) {
			current.add(r[i]);
		}
	}

	public void addFloat(float value) {
		byte[] r = ByteUtils.toBytes(value);
		for (int i = 0; i < r.length; i++) {
			current.add(r[i]);
		}
	}

	public void addArray(byte[] value) {
		for (int i = 0; i < value.length; i++) {
			current.add(value[i]);
		}
	}

	public void addByte(byte value) {
		current.add(value);
	}

	public void addString(String s) {
		byte[] data = ByteUtils.toBytes(s);
		for (int i = 0; i < data.length; i++) {
			current.add(data[i]);
		}
	}

	public List<byte[]> getDataList() {
		return data;
	}

	public int getUUID() {
		return uuid;
	}

	public byte[] take() {
		return data.remove(0);
	}
}
