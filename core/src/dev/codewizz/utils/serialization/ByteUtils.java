package dev.codewizz.utils.serialization;

import java.nio.charset.StandardCharsets;

public class ByteUtils {

	public static byte[] toBytes(int value, int length) {
		byte[] result = new byte[length];
		for (int i = 0; i < length; i++) {
			result[i] = (byte) (value >> ((length - 1) * 8 - i * 8));
		}
		return result;
	}

	public static byte[] toBytes(int value) {
		byte[] result = new byte[4];
		for (int i = 0; i < 4; i++) {
			result[i] = (byte) (value >> ((4 - 1) * 8 - i * 8));
		}
		return result;
	}

	public static byte toByte(byte b, boolean flag, int pos) {
		int bits = (int) b;
		int bytes = 1 << (7 - pos);
		if (flag) {
			bits = bits | bytes;
		} else {
			int magic = ~bytes;
			bits = bits & magic;
		}
		b = (byte) bits;
		return b;
	}

	public static boolean toBoolean(byte b, int pos) {
		int checker = 1 << (7 - pos);
		return (b & checker) != 0;
	}

	public static int toInteger(byte[] bytes) {
		int result = 0;
		for (int i = 0; i < bytes.length; i++) {
			result |= (bytes[i] & 0xFF) << (8 * (bytes.length - 1 - i));
		}
		return result;
	}

	public static byte[] toBytes(float value) {
		int intValue = Float.floatToIntBits(value);
		return toBytes(intValue, Float.BYTES);
	}

	public static byte[] toBytes(String value) {
		int length = Math.min(255, value.length());

		byte[] result = new byte[length + 1];
		byte[] data = value.getBytes(StandardCharsets.US_ASCII);

		result[0] = toBytes(value.length(), 1)[0];
		System.arraycopy(data, 0, result, 1, length);

		return result;
	}

	public static String toString(byte[] data, int index) {
		int length = toInteger(data, index, 1);

		byte[] stringData = new byte[length];

		System.arraycopy(data, index + 1, stringData, 0, length);

		return new String(stringData, StandardCharsets.US_ASCII);
	}

	public static float toFloat(byte[] bytes) {
		int intValue = toInteger(bytes);
		return Float.intBitsToFloat(intValue);
	}

	public static int toInteger(byte[] data, int index, int length) {
		byte[] a = new byte[length];
		System.arraycopy(data, index, a, 0, length);
		return toInteger(a);
	}

	public static int toInteger(byte[] data, int index) {
		byte[] a = new byte[Integer.BYTES];
		System.arraycopy(data, index, a, 0, Integer.BYTES);
		return toInteger(a);
	}

	public static float toFloat(byte[] data, int index) {
		byte[] a = new byte[Float.BYTES];
		System.arraycopy(data, index, a, 0, Float.BYTES);
		return toFloat(a);
	}

	public static void main(String[] args) {
		byte b = toByte((byte)0, true, 4);
		
		System.out.println(b);
		
		b = toByte(b, true, 5);
		
		System.out.println(toBoolean(b, 4));
		System.out.println(toBoolean(b, 1));
		System.out.println(toBoolean(b, 0));
		System.out.println(toBoolean(b, 5));
		
		
	}
	
	
	
}
