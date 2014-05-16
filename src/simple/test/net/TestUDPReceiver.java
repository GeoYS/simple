/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.test.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import simple.net.ObjectPacket;
import simple.net.UDPSocket;

/**
 *
 * @author GeoYS_2
 */
public class TestUDPReceiver {
    
    static final int PORT = 9000;
    
    private UDPSocket socket;
    
    public TestUDPReceiver() throws UnknownHostException, SocketException, IOException{
        socket = new UDPSocket(InetAddress.getLocalHost().getHostName(),
                PORT);
    }
    
    public static void main(String[] args) throws UnknownHostException, SocketException, IOException{
        TestUDPReceiver rec = new TestUDPReceiver();
        
        while(true){
            ObjectPacket op = rec.socket.receive();
            if(op != null)
                System.out.println(op.object);
        }
    }
}
