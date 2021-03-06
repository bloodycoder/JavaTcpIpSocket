package Chap5;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TCPEchoClientNonBlocking {
    public static void main(String[] args) throws IOException {
        if((args.length<2) || (args.length>3))
            throw new IllegalArgumentException("Parameter(s):<Server><Word>[<Port>]");
        String server = args[0];
        byte[] argument = args[1].getBytes();
        int servPort = Integer.parseInt(args[2]);
        SocketChannel clntChan = SocketChannel.open();
        clntChan.configureBlocking(false);
        if(!clntChan.connect(new InetSocketAddress(server,servPort))){
            while(!clntChan.finishConnect()){
                System.out.print(".");
            }
        }
        ByteBuffer writeBuf = ByteBuffer.wrap(argument);
        ByteBuffer readBuf = ByteBuffer.allocate(argument.length);
        int totalBytesRcvd = 0;
        int bytesRcvd;
        while(totalBytesRcvd<argument.length){
            if(writeBuf.hasRemaining()){
                clntChan.write(writeBuf);
            }
            if((bytesRcvd = clntChan.read(readBuf)) == -1){
                throw new SocketException("Connection closed");
            }
            totalBytesRcvd += bytesRcvd;
            System.out.print(".");
        }
        System.out.println("received:"+new String(readBuf.array(),0,totalBytesRcvd));
        clntChan.close();
    }
}
