/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simple;

import java.awt.event.ActionListener;

/**
 *
 * @author Chris
 */
public class Timer {
    public long delay;
    public ActionListener action;
    public Timer(long millis, ActionListener al){
        delay = millis;
        action = al;
    }
    public void start(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                long last = System.currentTimeMillis();
                while(true){
                    long now = System.currentTimeMillis();
                    if(now - last >= delay){
                        action.actionPerformed(null);
                    }
                    System.out.println("Timer code executed");
                }
            }
        }).start();
    }
    public void setDelay(long millis){
        delay = millis;
    }
}
