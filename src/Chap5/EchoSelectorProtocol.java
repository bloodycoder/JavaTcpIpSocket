package Chap5;

import Chap4.EchoProtocol;
import com.sun.org.apache.bcel.internal.generic.Select;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class EchoSelectorProtocol implements TCPProtocol{
    private int bufSize;
    public EchoSelectorProtocol(int bufSize){
        this.bufSize = bufSize;
    }
    @Override
    public void handleAccept(SelectionKey key) throws IOException {
        SocketChannel clntChan = ((ServerSocketChannel)key.channel()).accept();//从键中获取信道并接受连接
        clntChan.configureBlocking(false);
        clntChan.register(key.selector(),SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));
        /*可以通过selector方法获取相应的Selector。
        * */
    }

    @Override
    public void handleRead(SelectionKey key) throws IOException {
        SocketChannel clntChan = (SocketChannel)key.channel();
        ByteBuffer buf = (ByteBuffer) key.attachment(); //获取与键相关联的缓冲区
        long bytesRead = clntChan.read(buf);
        if(bytesRead == -1){
            clntChan.close();
        }
        else if(bytesRead>0){
            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }
    }

    @Override
    public void handleWrite(SelectionKey key) throws IOException {
        ByteBuffer buf = (ByteBuffer) key.attachment();
        buf.flip();//写数据
        SocketChannel clntChan = (SocketChannel)key.channel();
        clntChan.write(buf);
        if(!buf.hasRemaining()){
            key.interestOps(SelectionKey.OP_READ);
        }
        buf.compact();//使缓冲区又变为可读
    }
}
