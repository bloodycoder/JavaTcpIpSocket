package Chap4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class TCPEchoServerExecutor {
    public static void main(String[] args)throws IOException{
        if(args.length !=1){
            throw new IllegalArgumentException("<Port>");
        }
        int echoServPort = Integer.parseInt(args[0]);
        ServerSocket servsock = new ServerSocket(echoServPort);
        Logger logger = Logger.getLogger("practical");
        Executor service = Executors.newCachedThreadPool();
        while(true){
            Socket clntsock = servsock.accept();
            service.execute(new EchoProtocol(clntsock,logger));
        }
    }
}
