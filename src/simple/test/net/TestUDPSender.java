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

/**
 *
 * @author GeoYS_2
 */
public class TestUDPSender {    
    static final int PORT = 9900, RECEIVEINGPORT = 9000;
    
    private UDPSocket socket;
    
    public TestUDPSender() throws UnknownHostException, SocketException, IOException{
        socket = new UDPSocket(InetAddress.getLocalHost().getHostName(),
                PORT);
    }
    
    public static void main(String[] args) throws UnknownHostException, SocketException, IOException, InterruptedException{
        TestMulticastSender server = new TestMulticastSender();
        
        while(true){
            Thread.sleep(50);
            System.out.println("Message sent");
            server.socket.send(new TestMessage("Hello world", Math.round(Math.random() * 100)),
                    InetAddress.getLocalHost().getHostName(), // Since the test sender and reciever are on the same computer
                    RECEIVEINGPORT);
        }
    }
}
