package Chap2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Chap2_UDPEchoServer {
    public static final int LENGTH = 3;
    public static void main(String[]args) throws IOException {
        DatagramSocket socket = new DatagramSocket(Integer.parseInt(args[0]));
        DatagramPacket packet = new DatagramPacket(new byte[LENGTH],LENGTH);
        while(true){
            socket.receive(packet);
            System.out.println("handling client at "+packet.getAddress().toString()+" port is "+packet.getPort());
            socket.send(packet);
            packet.setLength(LENGTH);
        }
    }
}
