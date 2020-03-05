package Chap4;

import Chap3.module.VoteMsg;
import Chap3.module.VoteMsgCoder;
import Chap3.module.VoteMsgTextCoder;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class VoteMulticastSender {
    public static final int CANDIDATEID = 475;
    public static void main(String[] args)throws IOException{
        if((args.length<2) || (args.length>3)){
            throw new IllegalArgumentException("[]");
        }
        InetAddress destAddr = InetAddress.getByName(args[0]);
        if(!destAddr.isMulticastAddress()){
            throw new IllegalArgumentException("Not multicast addr");
        }
        int destPort = Integer.parseInt(args[1]);
        int TTL = (args.length == 3)?Integer.parseInt((args[2])):1;
        MulticastSocket sock = new MulticastSocket();
        sock.setTimeToLive(TTL);
        VoteMsgCoder coder = new VoteMsgTextCoder();
        VoteMsg vote = new VoteMsg(true,true,CANDIDATEID,1000001L);
        byte[] msg = coder.toWire(vote);
        DatagramPacket message = new DatagramPacket(msg,msg.length,destAddr,destPort);
        System.out.println("Sending text-encoded request ("+msg.length+" bytes):");
        sock.send(message);
        sock.close();
    }
}
