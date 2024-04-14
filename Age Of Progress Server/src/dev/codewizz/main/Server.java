package dev.codewizz.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server {

	private static final int SERVER_PORT = 25565;
	
	private final int MAX_PACKET_SIZE = 1024;
	private byte[] receivedDataBuffer = new byte[MAX_PACKET_SIZE * 10];
	
	private DatagramSocket socket;
	public Thread listenThread;
	private boolean connected; 
	
	public Server() {
		if(!connect()) {
			System.err.println("Could NOT start listening for Clients");
		} else {
			System.out.println("Listening...");
		}
	}
	
	private void process(DatagramPacket packet) {
		byte[] data = packet.getData();
		
		 
	}
	
	private boolean connect() {
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
		}, "packet-handler");
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
				if(e.getMessage() != null && e.getMessage().equals("Socket closed")) {
					System.err.println("Socket was closed!");
				} else {
					System.err.println("Faulty packet received, dropping...");
				}
			}
			if(packet.getData().length > 0 && connected) {
				process(packet);
			}
		}
	}
	
	public void send(byte[] data, InetAddress address, int port) {
		DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		connected = false;
		socket.close();
	}
}
