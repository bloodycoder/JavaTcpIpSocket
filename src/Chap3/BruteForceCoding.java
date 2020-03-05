package Chap3;

public class BruteForceCoding {
    private static byte byteVal = 101;
    private static short shortVal = 10001;
    private static int intVal = 1000000001;
    private static long longVal = 1000000000001L;
    private final static int BSIZE = Byte.SIZE/Byte.SIZE;
    private final static int SSIZE = Short.SIZE/Byte.SIZE;
    private final static int ISIZE = Integer.SIZE/Byte.SIZE;
    private final static int LSIZE = Long.SIZE/Byte.SIZE;
    private final static int BYTEMASK = 0xFF;
    public static String byteArrayToDecimalString(byte[] bArray){
        StringBuilder rtn = new StringBuilder();
        for(byte b:bArray){
            rtn.append(b& BYTEMASK).append(" ");
        }
        return rtn.toString();
    }
    public static int encodeIntBigEndian(byte[] dst,long val,int offset,int size){
        for(int i=0;i<size;i++){
            dst[offset++] = (byte)(val>>((size-i-1)*Byte.SIZE));
        }
        return offset;
    }
    public static long decodeIntBigEndian(byte[] val,int offset,int size){
        long rtn = 0;
        for(int i=0;i<size;i++){
            rtn = (rtn<<Byte.SIZE) |((long)val[offset+i]&BYTEMASK);
        }
        return rtn;
    }
    public static void main(String[]args){
        byte[]message = new byte[BSIZE+SSIZE+ISIZE+LSIZE];
        int offset = encodeIntBigEndian(message,byteVal,0,BSIZE);
    }

}
