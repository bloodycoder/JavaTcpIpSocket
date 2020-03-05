package Chap3;

import Chap3.module.VoteMsg;
import Chap3.module.VoteMsgCoder;
import Chap3.module.VoteMsgTextCoder;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.*;
import java.util.Arrays;

public class VoteClientUDP {
    public static void main(String[] args) throws IOException {
        if(args.length !=3){
            throw new IllegalArgumentException("parameter(s):<Destination>");
        }
        InetAddress destAddr = InetAddress.getByName(args[0]);
        int destPort = Integer.parseInt(args[1]);
        int candidate = Integer.parseInt(args[2]);
        DatagramSocket sock = new DatagramSocket();
        sock.connect(destAddr,destPort);
        VoteMsg vote = new VoteMsg(false,false,candidate,0);
        VoteMsgCoder coder =new VoteMsgTextCoder();
        byte[] encodedVote = coder.toWire(vote);
        System.out.println("sending text-encoded request("+encodedVote.length+" bytes)");
        DatagramPacket message = new DatagramPacket(encodedVote,encodedVote.length);
        sock.send(message);
        message = new DatagramPacket(new byte[VoteMsgTextCoder.MAX_WIRE_LENGTH],VoteMsgTextCoder.MAX_WIRE_LENGTH);
        sock.receive(message);
        encodedVote = Arrays.copyOfRange(message.getData(),0,message.getLength());
        vote = coder.fromWire(encodedVote);
        System.out.println(vote);
    }
}
