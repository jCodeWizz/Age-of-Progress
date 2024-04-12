package dev.codewizz.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {

	private final static String SERVER_IP = "80.61.15.205";
	private final static int SERVER_PORT = 25565;
	private static InetAddress SERVER_ADDRESS;
	
	private final int MAX_PACKET_SIZE = 1024;
	private byte[] receivedDataBuffer = new byte[MAX_PACKET_SIZE * 10];
	
	private DatagramSocket socket;
	public Thread listenThread;
	private boolean connected = false;
	
	
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
	}
	
	private void process(DatagramPacket packet) {
		byte[] data = packet.getData();
		
		if(data[0] == (byte) 0b11001100) {
			
		}
	}
	
	/**
	 * Tries to open the connection to the server.
	 * @return if the try was successful or not.
	 */
	public boolean connect() {
		try {
			socket = new DatagramSocket(SERVER_PORT);
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
		}, "listen-thread");
		listenThread.start();

		connected = true;
		return true;
	}
	
	private void listen() {
		while (connected) {
			DatagramPacket packet = new DatagramPacket(receivedDataBuffer, MAX_PACKET_SIZE);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(packet.getData().length > 0) {
				process(packet);
			}
		}
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
