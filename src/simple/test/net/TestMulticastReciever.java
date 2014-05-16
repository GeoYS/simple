/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.test.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import simple.net.ObjectPacket;
import simple.net.UDPSocket;

/**
 *
 * @author geshe9243
 */
public class TestMulticastReciever {
    
    static final int PORT = 4455, GROUPPORT = 9555;
    static final String GROUPNAME = "235.1.1.1";
    
    UDPSocket socket;
    public TestMulticastReciever() throws UnknownHostException, SocketException, IOException{
        socket = new UDPSocket(InetAddress.getLocalHost().getHostName(),
                PORT,
                GROUPNAME,
                GROUPPORT,
                true,
                false);
    }
    
    public static void main(String[] args) throws UnknownHostException, SocketException, IOException{        
        TestMulticastReciever client = new TestMulticastReciever();
        
        while(true){
            ObjectPacket op = client.socket.receiveMulticast();
            if(op != null)
                System.out.println(op.object);
        }
    }
}
