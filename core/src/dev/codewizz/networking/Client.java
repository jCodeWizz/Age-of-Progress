package dev.codewizz.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import dev.codewizz.main.Main;
import dev.codewizz.utils.Assets;
import dev.codewizz.utils.Logger;
import dev.codewizz.utils.serialization.ByteUtils;

public class Client {

	private final static String SERVER_IP = "localhost";
	private final static int SERVER_PORT = 25565;
	private static InetAddress SERVER_ADDRESS;

	private final int MAX_PACKET_SIZE = 1024;
	private byte[] receivedDataBuffer = new byte[MAX_PACKET_SIZE * 10];

	private DatagramSocket socket;
	public Thread listenThread;
	private boolean connected = false;

	private String username = "CodeWizz";

	private boolean receivingFile;
	private int receivingFileSize = 0;
	private int receivingFileChunks = 0;
	private String receivingFilePath = "";
	private int progress = 0;

	private final HashMap<Integer, byte[]> receivedData = new HashMap<>();

	public Client() {
		try {
			SERVER_ADDRESS = InetAddress.getByName(SERVER_IP);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		if(!connect()) {
			Logger.error("Could NOT connect to the Server.");
		} else {
			Logger.log("Connected!");
		}

		sendConnectionPacket();
	}

	private void sendConnectionPacket() {
		byte[] data = new byte[17];
		data[0] = (byte) 0b11001100;

		byte[] nameData = username.getBytes(StandardCharsets.UTF_8);
		System.arraycopy(nameData, 0, data, 1, nameData.length);

		send(data);
	}

	public void sendFileRequest(String fileName) {
		this.receivingFilePath = fileName;
		byte[] data = new byte[fileName.length() + 1];
		data[0] = NetworkProtocol.DOWNLOAD_FILE_REQUEST;
		System.arraycopy(fileName.getBytes(), 0, data, 1, fileName.length());
		send(data);
	}

	private void process(DatagramPacket packet) {
		byte[] data = packet.getData();

		if (data[0] == NetworkProtocol.FIRST_CONNECT) {
			System.out.println("CONNECTED TO SERVER");
		} else if (data[0] == NetworkProtocol.DOWNLOAD_FILE_RESPONSE) {
			receivingFile = true;
			receivingFileSize = ByteUtils.toInteger(data, 1);
			receivingFileChunks = (receivingFileSize + (MAX_PACKET_SIZE-5) - 1) / (MAX_PACKET_SIZE-5);
		} else if (data[0] == NetworkProtocol.DOWNLOAD_FILE_RESPONSE_FILE) {
			int index = ByteUtils.toInteger(data, 1);
			byte[] buffer = new byte[MAX_PACKET_SIZE - 5];
			System.arraycopy(data, 5, buffer, 0, buffer.length);
			receivedData.put(index, buffer);
			checkReceivedFileData();
		}
	}

	private void checkReceivedFileData() {
		int newProgress = (int)(((float)receivedData.size()/(float)receivingFileChunks)*100f);

		if(newProgress >= progress + 7 || newProgress >= 100) {
			progress = (int)(((float)receivedData.size()/(float)receivingFileChunks)*100f);
			Logger.log("Downloading file '" + receivingFilePath + "' (" + progress +"%)");
		}

		if(!receivingFile || receivedData.size() < receivingFileChunks) {
			return;
		}

		int offset = 0;
		byte[] data = new byte[receivingFileSize];
		for(int i = 0; i < receivingFileChunks; i++) {
			byte[] buffer = receivedData.get(i);
			int length = Math.min(data.length - offset, buffer.length);
			System.arraycopy(buffer, 0, data, offset, length);

			offset += buffer.length;
		}

		saveFile(data);
		receivedData.clear();
		receivingFile = false;
		receivingFileSize = 0;
		receivingFileChunks = 0;
	}

	private void saveFile(byte[] fileData) {
		Gdx.files.external(receivingFilePath).writeBytes(fileData, false);
		Logger.log("Saved file: '" + receivingFilePath + "'!");
	}

	private boolean connect() {
		try {
			socket = new DatagramSocket(0);
		} catch (SocketException e) {
			e.printStackTrace();
			connected = false;
			return false;
		}

		listenThread = new Thread(new Runnable() {
			@Override
			public void run() {
				listen();
			}
		}, "packet-handler");
		listenThread.start();

		connected = true;
		return true;
	}

	private void listen() {
		while (Main.RUNNING) {
			DatagramPacket packet = new DatagramPacket(receivedDataBuffer, MAX_PACKET_SIZE);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				if(e.getMessage() != null && e.getMessage().equals("Socket closed")) {
					Logger.log("Closed client socket!");
				} else {
					Logger.error("Could not receive packet...");
				}
			}
			if(packet.getData().length > 0 && connected) {
				process(packet);
			}
		}
	}

	public void stop() {
		connected = false;
		socket.close();
	}

	public void send(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, SERVER_ADDRESS, SERVER_PORT);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isConnected() {
		return connected;
	}
}