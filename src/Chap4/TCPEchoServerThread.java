package Chap4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Logger;

public class TCPEchoServerThread {
    public static void main(String[] args)throws IOException{
        if(args.length !=1){
            throw new IllegalArgumentException("Parameter(s):<Port>");
        }
        int echoServPort = Integer.parseInt(args[0]);
        ServerSocket servSock = new ServerSocket(echoServPort);
        Logger logger = Logger.getLogger("practical");
        while(true){
            Socket clntSock = servSock.accept();
            Thread thread = new Thread(new EchoProtocol(clntSock,logger));
            thread.start();
            logger.info("created and started thread "+thread.getName());
        }
    }
}
