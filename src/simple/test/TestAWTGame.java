/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple.test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import simple.AWTGame;

/**
 *
 * @author GeoYS_2
 */
public class TestAWTGame extends AWTGame{

    public TestAWTGame(){
        super("Test Game", 500, 500);
        this.setClearOnRender(true);
    }
    
    Circle player;
    
    @Override
    public void init() {
        player = new Circle();
    }

    @Override
    public void update(long delta) {
        player.update(delta);
    }

    @Override
    protected void render(Graphics g) {
        player.render(g);
    }
    
    class Circle{
        private double x, y;
        
        public Circle(){
        }

        public void update(long delta) {
            x = MouseInfo.getPointerInfo().getLocation().x;
            y = MouseInfo.getPointerInfo().getLocation().y;
        }

        protected void render(Graphics g) {
            g.setColor(Color.yellow);
            g.drawOval((int)x, (int)y, 10, 10);
            g.dispose();
        }
    }
    
    public static void main(String[] args){
        new TestAWTGame().start();
    }
}
