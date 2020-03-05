package Chap4;

import Chap3.module.VoteMsg;
import Chap3.module.VoteMsgTextCoder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Arrays;

public class VoteMulticastReceiver {
    public static void main(String[] args)throws IOException{
        if(args.length !=2){
            throw new IllegalArgumentException("Parameter(s)");
        }
        InetAddress address = InetAddress.getByName(args[0]);
        if(!address.isMulticastAddress()){
            throw new IllegalArgumentException("Not a multicast addr");
        }
        int port = Integer.parseInt(args[1]);
        MulticastSocket sock = new MulticastSocket(port);
        sock.joinGroup(address);
        VoteMsgTextCoder coder = new VoteMsgTextCoder();
        DatagramPacket packet = new DatagramPacket(new byte[VoteMsgTextCoder.MAX_WIRE_LENGTH],VoteMsgTextCoder.MAX_WIRE_LENGTH);
        sock.receive(packet);
        VoteMsg vote  = coder.fromWire(Arrays.copyOfRange(packet.getData(),0,packet.getLength()));
        System.out.println("received text-encoded request "+packet.getLength()+" bytes):");
        sock.close();
    }
}
