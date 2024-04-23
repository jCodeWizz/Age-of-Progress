package dev.codewizz.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

import dev.codewizz.main.Main;
import dev.codewizz.utils.Logger;

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
	
	/**
	 * Construct a new connection to the server.
	 * Uses default IP and PORT.
	 */
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
	
	private void process(DatagramPacket packet) {
		byte[] data = packet.getData();
		
		if(data[0] == (byte) 0b11001100) {
			System.out.println("CONNECTED TO SERVER");
		}
	}
	
	/**
	 * Tries to open the connection to the server.
	 * @return if the try was successful or not.
	 */
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
