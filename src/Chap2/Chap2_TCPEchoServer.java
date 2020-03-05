package Chap2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class Chap2_TCPEchoServer {
    private static final int BUFSIZE = 1;
    public static void main(String args[]) throws IOException, InterruptedException {
        if(args.length !=1)
            throw new IllegalArgumentException("Parameter(s):<Port>");
        int servPort = Integer.parseInt(args[0]);
        ServerSocket servSock = new ServerSocket(servPort);

        int recvMsgSize;
        byte[] receiveBuf = new byte[BUFSIZE];
        while(true){
            Socket clntSock = servSock.accept();
            SocketAddress clientAdress = clntSock.getRemoteSocketAddress();
            System.out.println("Handling client at "+clientAdress+" serverport is "+clntSock.getLocalPort());
            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();
            while((recvMsgSize = in.read(receiveBuf))!=-1){
                out.write(receiveBuf,0,recvMsgSize);
            }
            clntSock.close();
        }
    }
}
