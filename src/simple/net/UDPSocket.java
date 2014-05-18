/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

/**
 * Essentially a wrapper class of a DatagramSocket,
 * designed to read Objects from the socket and send
 * Objects to other sockets or multicast easily.
 * 
 * IMPORTANT:
 * - currently does not implement any sequencing of packets, so 
 *   an object cannot at the moment be larger than the buffer size.
 * - the buffer size 128 kilobytes
 *
 * @author geshe9243
 */
public class UDPSocket {
    
    public static int TIMEOUT = 100; // milliseconds to block recieve() call
    public static int TTL = 100; // Time To Live of multicasted packets in milliseconds
    
    private DatagramSocket socket = null;
    private MulticastSocket groupSocket = null;
    
    private byte[] buffer = new byte[128 * 1024]; // 128 kB
    private String multicastAddress = null;
    private int multicastPort = -1;
    
    /**
     * Create an empty UDP socket. 
     * Set your properties (eg. InetAddress, port, 
     * multicast address, etc.).
     */
    public UDPSocket(){
        
    }
    
    /**
     * Create socket that receives or send data from/to a multicast group,
     * hence the use of a MulticastSocket.
     * 
     * @param localName InetAddress host name for this socket
     * @param localPort port for this socket to bind to
     * @param groupName the IP for the specific multicast group to join
     * @param groupPort the port that members of the multicast group will use
     * @throws SocketException
     * @throws UnknownHostException
     * @throws IOException 
     */
    public UDPSocket(
                String localName,
                int localPort,
                String groupName,
                int groupPort
            ) throws SocketException, UnknownHostException, IOException{
        socket = new DatagramSocket(localPort, InetAddress.getByName(localName));
        multicastAddress = groupName;
        multicastPort = groupPort;
        groupSocket = new MulticastSocket(groupPort);
        groupSocket.joinGroup(InetAddress.getByName(groupName));
        groupSocket.setSoTimeout(TIMEOUT);
        groupSocket.setTimeToLive(TTL);
    }
    
    /**
     * Create a UDPSocket that receives 
     * data individually from another UDPSocket.
     * 
     * @param localName
     * @param localPort
     * @throws SocketException
     * @throws UnknownHostException
     * @throws IOException 
     */
    public UDPSocket(
                String localName,
                int localPort
            ) throws SocketException, UnknownHostException, IOException{
        socket = new DatagramSocket(localPort, InetAddress.getByName(localName));
        socket.setSoTimeout(TIMEOUT);
    }
    
    /**
     * Returns an ObjectPacket, containing the object
     * read from its DatagramSocket, 
     * and the InetAddress and port from which it came,
     * or null if there is nothing to be received.
     * 
     * @return ObjectPacket
     */
    public ObjectPacket receive() throws IOException{
        // DatagramPacket could be abstracted out.
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try{
            socket.receive(packet);
        }
        catch(IOException e){
            System.out.println("Debugging: Nothing to receive.");
            return null;
        }
        
        ByteArrayInputStream bis = new ByteArrayInputStream(packet.getData());
        ObjectInputStream ois = new ObjectInputStream(bis);
        
        Object o = null;
        try{
            o = ois.readObject();
        }
        catch(Exception e){
            o = null;
        }

        return o != null ? 
                new ObjectPacket(packet.getPort(), packet.getAddress(), o)
                : null;
    }
    
    public ObjectPacket receiveMulticast() throws IOException{
        if(this.groupSocket == null){
            return null;
        }
        
        // DatagramPacket could be abstracted out.
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try{
            groupSocket.receive(packet);
        }
        catch(IOException e){
            System.out.println("Debugging: Nothing to receive.");
            return null;
        }
        
        ByteArrayInputStream bis = new ByteArrayInputStream(packet.getData());
        ObjectInputStream ois = new ObjectInputStream(bis);
        
        Object o = null;
        try{
            o = ois.readObject();
        }
        catch(Exception e){
            o = null;
        }

        return o != null ? 
                new ObjectPacket(packet.getPort(), packet.getAddress(), o)
                : null;
    }
    
    /**
     * Sends an Object O to address addressName, port p (which should be to the 
     * accompanying UDPServer).
     * 
     * @param o Object
     * @param a InetAddress
     * @param p port number
     */
    public void send(Object o, String addressName, int p) throws IOException{
        // Streams could be abstracted out.
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        
        oos.writeObject(o);        
        
        // DatagramPacket could be abstracted out.
        DatagramPacket packet = new DatagramPacket(bos.toByteArray(), bos.size());
        packet.setAddress(InetAddress.getByName(addressName));
        packet.setPort(p);
        
        socket.send(packet);
    }   
    
    /**
     * Multicast an Object to the group.
     * 
     * @param o - Object to send
     * @throws IOException 
     */
    public void sendMulticast(Object o) throws IOException{
        // Streams could be abstracted out.
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        
        oos.writeObject(o);        
        
        // DatagramPacket could be abstracted out.
        DatagramPacket packet = new DatagramPacket(bos.toByteArray(), bos.size(),
                InetAddress.getByName(multicastAddress), multicastPort);
        
        socket.send(packet);
    }   
    
    public InetAddress getAddress(){
        return this.socket.getInetAddress();
    }
    
    public int getPort(){
        return this.socket.getPort();
    }
    
    public void setSocket(int port, String localName) throws UnknownHostException, SocketException{
        socket = new DatagramSocket(port, InetAddress.getByName(localName));
    }
    
    public void setMulticastSocket(int groupPort, String groupName) throws IOException{
        this.multicastAddress = groupName;
        this.multicastPort = groupPort;
        groupSocket = new MulticastSocket(groupPort);
        groupSocket.joinGroup(InetAddress.getByName(groupName));
        groupSocket.setSoTimeout(TIMEOUT);
        groupSocket.setTimeToLive(TTL);
    }
}
