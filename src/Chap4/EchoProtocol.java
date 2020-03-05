package Chap4;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoProtocol implements Runnable{
    private static final int BUFSIZE = 32;
    private Socket clntSock;
    private Logger logger;
    public EchoProtocol(Socket clntSock,Logger logger){
        this.clntSock = clntSock;
        this.logger = logger;
    }
    public static void handleEchoClient(Socket clntSock,Logger logger){
        try{
            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();
            byte[]buf = new byte[BUFSIZE];
            int totalBytes,tmpBytes;
            totalBytes = 0;
            while( (tmpBytes = in.read(buf))!=-1 ){
                out.write(buf,0,tmpBytes);
                totalBytes+=tmpBytes;
            }
            out.flush();
            logger.info("Client "+clntSock.getRemoteSocketAddress()+" ,echoed "+totalBytes+" bytes.");
        } catch (IOException e) {
            logger.log(Level.WARNING,"exception in protocol",e);
        }
        finally {
            try{
                clntSock.close();
            }
            catch (Exception e){
                logger.log(Level.WARNING,"close sock error");
            }
        }
    }
    public void run(){
        handleEchoClient(clntSock,logger);
    }
}
