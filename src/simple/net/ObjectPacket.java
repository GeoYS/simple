/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.net;

import java.net.InetAddress;

/**
 *
 * @author geshe9243
 */
public class ObjectPacket {    
    public int port;
    public InetAddress address;
    public Object object;
    public ObjectPacket(int p, InetAddress a, Object o){
        port = p;
        address = a;
        object = o;
    }
}
