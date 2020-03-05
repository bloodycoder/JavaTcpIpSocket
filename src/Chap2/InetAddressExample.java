package Chap2;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class InetAddressExample {
    public  static void main(String args[]) throws SocketException {
        Enumeration<NetworkInterface>interfaceList = NetworkInterface.getNetworkInterfaces();
        if(interfaceList == null){
            System.out.println("--no interface found --");
        }
        else{
            while(interfaceList.hasMoreElements()){
                NetworkInterface iface = interfaceList.nextElement();
                System.out.println("Interface "+iface.getName());
                Enumeration<InetAddress> addrList = iface.getInetAddresses();
                if(!addrList.hasMoreElements()){
                    System.out.println("\t no address for this interface");
                }
                while(addrList.hasMoreElements()){
                    InetAddress adress = addrList.nextElement();
                    System.out.print("Adress "+adress.getHostAddress());
                }
            }
        }
    }
}
