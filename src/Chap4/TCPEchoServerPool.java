package Chap4;

import sun.rmi.runtime.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TCPEchoServerPool {
    public static void main(String[] args) throws IOException {
        if(args.length != 2){
            throw new IllegalArgumentException("Parameter(s):<port><thread>");
        }
        int echoServPort = Integer.parseInt(args[0]);
        int threadPoolSize = Integer.parseInt(args[1]);
        final ServerSocket servSock = new ServerSocket(echoServPort);
        final Logger logger = Logger.getLogger("practical");
        for(int i=0;i<threadPoolSize;i++){
            Thread thread = new Thread(){
                public void run(){
                    while(true){
                        try{
                            Socket clntSock = servSock.accept();
                            EchoProtocol.handleEchoClient(clntSock,logger);
                        } catch (IOException e) {
                            logger.log(Level.WARNING,"client accept failed",e);
                        }
                    }
                }
            };
            thread.start();
            logger.info("created and started thread ="+thread.getName());
        }
    }
}
