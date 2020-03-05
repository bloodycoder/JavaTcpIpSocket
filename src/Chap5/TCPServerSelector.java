package Chap5;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class TCPServerSelector {
    private static final int BUFSIZE = 256;
    private static final int TIMEOUT = 3000;
    public static void main(String[] args)throws IOException{
        if(args.length <1){
            throw new IllegalArgumentException("Parameter(s):<port>");
        }
        Selector selector = Selector.open();
        for(String arg:args){
            ServerSocketChannel listnChannel = ServerSocketChannel.open();
            listnChannel.socket().bind(new InetSocketAddress(Integer.parseInt(arg))); //必须访问底层的ServerSocket实例来绑定端口
            listnChannel.configureBlocking(false);
            listnChannel.register(selector, SelectionKey.OP_ACCEPT);
        }
        TCPProtocol protocol = new EchoSelectorProtocol(BUFSIZE);
        while(true){
            if(selector.select(TIMEOUT) == 0){
                System.out.print(".");
                continue;
            }
            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
            while(keyIter.hasNext()){
                SelectionKey key = keyIter.next();
                if(key.isAcceptable()){
                    protocol.handleAccept(key);
                }
                if(key.isReadable()){
                    protocol.handleRead(key);
                }
                if(key.isValid() && key.isWritable()){
                    protocol.handleWrite(key);
                }
                keyIter.remove();
            }
        }
    }
}
