package dev.codewizz.utils.saving;

import dev.codewizz.utils.serialization.ByteUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public List<byte[]> getDataList() {
        return data;
    }

    public byte[] take() {
        return data.remove(0);
    }
}
