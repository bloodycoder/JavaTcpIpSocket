package Chap4;

import java.io.*;
import java.net.Socket;

public class CompressClient {
    public static final int BUFSIZE = 256;
    public static void main(String[]args)throws IOException {
        if(args.length !=3){
            throw new IllegalArgumentException("Parameter(s):<Server><Port><File>");
        }
        String server = args[0];
        int port = Integer.parseInt(args[1]);
        String filename = args[2];
        FileInputStream fileIn = new FileInputStream(filename);
        FileOutputStream fileOut = new FileOutputStream(filename+".gz");
        Socket sock = new Socket(server,port);
        sendBytes(sock,fileIn);
        InputStream sockIn = sock.getInputStream();
        int bytesRead;
        byte[] buffer = new byte[BUFSIZE];
        while((bytesRead = sockIn.read(buffer))!=-1){
            fileOut.write(buffer,0,bytesRead);
        }
        System.out.println();
        sock.close();
        fileIn.close();
        fileOut.close();
    }
    private static void sendBytes(Socket sock, InputStream fileIn) throws IOException {
        OutputStream sockOut = sock.getOutputStream();
        int bytesRead;
        byte[]buffer = new byte[BUFSIZE];
        while((bytesRead = fileIn.read(buffer))!=-1){
            sockOut.write(buffer,0,bytesRead);
            System.out.print("W");
        }
        sock.shutdownOutput();
    }
}
