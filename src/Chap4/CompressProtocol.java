package Chap4;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

public class CompressProtocol implements Runnable{
    public static final int BUFSIZE = 1024;
    private Socket clntSock;
    private Logger logger;
    public CompressProtocol(Socket clntSock, Logger logger){
        this.clntSock = clntSock;
        this.logger = logger;
    }
    public static void handleCompressClient(Socket clntSock,Logger logger) throws IOException {
        try{
            InputStream in = clntSock.getInputStream();
            GZIPOutputStream out = new GZIPOutputStream(clntSock.getOutputStream());
            byte[] buffer = new byte[BUFSIZE];
            int bytesRead;
            while((bytesRead = in.read(buffer))!=-1){
                out.write(buffer,0,bytesRead);
            }
            out.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try{
            clntSock.close();
        }
        catch (IOException e){
            logger.info("exception = "+e.getMessage());
        }
    }
    public void run(){
        try {
            handleCompressClient(clntSock,logger);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
