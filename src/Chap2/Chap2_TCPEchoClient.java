package Chap2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.SocketHandler;

public class Chap2_TCPEchoClient {
    public static void main(String[] args)throws IOException {
        if(args.length<2 || args.length>3)
            throw new IllegalArgumentException("Parameter[s]: <Server><Word>[<Port>]");
        String server = args[0];
        byte[]data = args[1].getBytes();
        int servPort = (args.length == 3)?(Integer.parseInt(args[2])):7;
        Socket socket = new Socket(server,servPort);
        System.out.println("Connected to server..sending echo string");
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        out.write(data);
        out.flush();
        int totalBytesRecv = 0;
        int bytesRcvd;
        while(totalBytesRecv <data.length){
            if((bytesRcvd = in.read(data,totalBytesRecv,data.length-totalBytesRecv)) == -1)
                throw new SocketException("Connection closed");
            totalBytesRecv += bytesRcvd;
        }
        System.out.println("recved "+new String(data));
        socket.close();
    }
}
