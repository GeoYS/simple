/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.test.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import simple.net.UDPSocket;
import simple.net.UDPSocket;

/**
 *
 * @author geshe9243
 */
public class TestMulticastSender {
    
    static final int PORT = 9999, GROUPPORT = 9555;
    static final String GROUPNAME = "235.1.1.1";
    
    public UDPSocket socket;
    
    public TestMulticastSender() throws UnknownHostException, SocketException, IOException{
        socket = new UDPSocket(InetAddress.getLocalHost().getHostName(),
                PORT,
                GROUPNAME,
                GROUPPORT,
                false,
                true);
    }
    
    public static void main(String[] args) throws UnknownHostException, SocketException, IOException, InterruptedException{
        TestMulticastSender server = new TestMulticastSender();
        
        while(true){
            Thread.sleep(50);
            System.out.println("Message sent");
            server.socket.multicast(new TestMessage("Hello world", Math.round(Math.random() * 100)));
        }
    }
}
