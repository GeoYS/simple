/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.test.net;

import java.io.Serializable;

/**
 *
 * @author GeoYS_2
 */
public class TestMessage implements Serializable{
    private String message;
    private long number;
    public TestMessage(String message, long number){
        this.message = message;
        this.number = number;
    }
    public String getMessage(){
        return message;
    }
    public long getNumber(){
        return number;
    }
    @Override
    public String toString() {
        return "Message: " + message + " Number: " + number;
    }
}
