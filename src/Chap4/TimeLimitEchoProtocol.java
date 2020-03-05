package Chap4;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class TimeLimitEchoProtocol implements Runnable{
    private static final int BUFSIZE = 32;
    private static final String TIMELIMIT = "10000";
    private static final String TIMELIMITPROP = "Timelimit";
    private static int timelimit;
    private Socket clntSock;
    private Logger logger;
    public TimeLimitEchoProtocol(Socket clntSock,Logger logger){
        this.clntSock = clntSock;
        this.logger = logger;
    }
    public static void handleEchoClient(Socket clntSock,Logger logger){
        try{
            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();
            timelimit = Integer.parseInt(System.getProperty(TIMELIMITPROP,TIMELIMIT));
            int recvMsgSize;
            int totalBytesEchoed = 0;
            byte[] echoBuffer = new byte[BUFSIZE];
            long endTime = System.currentTimeMillis() + timelimit;
            int timeBoundMillis = timelimit;
            clntSock.setSoTimeout(timelimit);
            while((timeBoundMillis>0)&&((recvMsgSize = in.read(echoBuffer))!=-1)){
                out.write(echoBuffer,0,recvMsgSize);
                totalBytesEchoed += recvMsgSize;
                timeBoundMillis = (int)(endTime - System.currentTimeMillis());
                clntSock.setSoTimeout(timeBoundMillis);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        handleEchoClient(this.clntSock,this.logger);
    }
}
