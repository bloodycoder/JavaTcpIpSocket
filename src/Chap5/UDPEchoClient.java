package Chap5;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/*
* 课后练习题2
* */
public class UDPEchoClient {
    public static void main(String[]args) throws IOException {
        if(args.length!=3)
            throw new IllegalArgumentException("<Server><Port><message>");
        String serverAddr = args[0];
        int port = Integer.parseInt(args[1]);
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress(serverAddr,port));
        ByteBuffer buffer = ByteBuffer.wrap(args[2].getBytes());
        while(true){
            if(buffer.hasRemaining())
                channel.write(buffer);
            else
                break;
        }
        buffer.flip();
        int totalBytes = 0;
        int bytesRead;
        while(true){
            if(buffer.hasRemaining()){
                bytesRead = channel.read(buffer);
                totalBytes+=bytesRead;
                if(bytesRead == -1)
                    throw new IOException("connection closed");
            }
            else
                break;
        }
        System.out.println("received "+new String(buffer.array(),0,totalBytes));
    }
}
