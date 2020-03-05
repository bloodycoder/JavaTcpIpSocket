package Chap3;

import Chap3.module.*;

import java.io.OutputStream;
import java.net.Socket;
public class VoteClientTCP {
    public static final int CANDIDATEID = 888;
    public static void main(String[]args)throws  Exception{
        if(args.length !=2){
            throw new IllegalArgumentException("Parameter(s):<Server><Port>");
        }
        String destAddr = args[0];
        int destPort = Integer.parseInt(args[1]);
        Socket sock = new Socket(destAddr,destPort);
        OutputStream out = sock.getOutputStream();
        VoteMsgCoder coder = new VoteMsgBinCoder();
        Framer framer = new LengthFramer(sock.getInputStream());
        VoteMsg msg = new VoteMsg(false,false ,CANDIDATEID,0);
        byte[]encodedMsg = coder.toWire(msg);
        System.out.println("sending inquiry("+encodedMsg.length+" bytes):");
        System.out.println(msg);
        framer.frameMsg(encodedMsg,out);
        //receive inquiry response
        encodedMsg = framer.nextMsg();
        msg = coder.fromWire(encodedMsg);
        System.out.println("received response ("+encodedMsg.length+" bytes):");
        System.out.println(msg);
        sock.close();
    }
}
