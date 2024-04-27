package dev.codewizz.utils.saving;

import dev.codewizz.utils.Logger;
import dev.codewizz.utils.Pair;
import dev.codewizz.utils.serialization.ByteUtils;
import dev.codewizz.world.items.Inventory;
import dev.codewizz.world.items.Item;
import dev.codewizz.world.items.ItemType;

import java.util.*;

public abstract class DataSaveLoader {

    protected List<byte[]> data = new ArrayList<>();
    protected List<Byte> current = new ArrayList<>();
    protected int size = 0;

    public DataSaveLoader() {}

    public DataSaveLoader(byte[] total) {
        int index = 0;

        while (index < total.length) {
            int length = ByteUtils.toInteger(total, index);
            byte[] data = new byte[length];

            System.arraycopy(total, index + 4, data, 0, length);
            this.data.add(data);
            index += length + 4;
        }
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

    public void addDouble(double value) {
        byte[] r = ByteUtils.toBytes(value);
        for (byte b : r) {
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

    public void addItem(Item item) {
        String type = item.getType().getId();
        int size = item.getSize();

        addInteger(size);
        addString(type);
    }

    public void addInventory(Inventory inventory) {
        addInteger(inventory.size);
        addInteger(inventory.getItems().size());
        for(Item item : inventory.getItems()) {
            addItem(item);
        }
    }

    public Pair<Inventory, Integer> readInventory(byte[] data, int index) {
        int size = ByteUtils.toInteger(data, index);
        int used = ByteUtils.toInteger(data, index + 4);
        Inventory inventory = new Inventory(size);

        int pos = 0;

        for (int i = 0; i < used; i++) {
            Pair<Item, Integer> result = readItem(data, pos + index + 8);
            pos += result.getTypeB();
            inventory.addItem(result.getTypeA());
        }

        return new Pair<>(inventory, pos + 8);
    }

    public Pair<Item, Integer> readItem(byte[] data, int index) {
        int size = ByteUtils.toInteger(data, index);
        String type = ByteUtils.toString(data, index + 4);

        return new Pair<>(new Item(ItemType.types.get(type), size), type.length() + 5);
    }

    public List<byte[]> getDataList() {
        return data;
    }

    public byte[] take() {
        return data.remove(0);
    }
}
