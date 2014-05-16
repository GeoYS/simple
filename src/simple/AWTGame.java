/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simple;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * A simple template to quickly start making a 2D game,
 * just implement your init, update, and render methods.
 * Uses AWT Canvas to draw.
 *
 * @author George Shen
 */
public abstract class AWTGame extends JFrame{
    
    private long lastRender;
    
    private JPanel _window;
    private Canvas _canvas;
    
    private boolean clearOnPaint = false; // repaints entire graphics context black
    
    /**
     * Create a AWTGame.
     * 
     * @throws IOException 
     */
    public AWTGame(String name, int width, int height){
        super();
        int w = width, h = height;
        this.setSize(w, h);
        this.setName(name);
        this.setResizable(false);
        
        this.rootPane.setDoubleBuffered(true);
        
        _canvas = new Canvas();
        _canvas.setBounds(0, 0, w, h);
        _canvas.setIgnoreRepaint(true);
        
        _window = (JPanel) this.getContentPane();
        _window.setSize(w, h);
        _window.setBackground(Color.black);
        _window.setDoubleBuffered(true);
        _window.add(_canvas);   
                
        lastRender = System.currentTimeMillis();
        
        this.pack();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        this.setIgnoreRepaint(true);
        this.setVisible(true);
        
        _canvas.createBufferStrategy(2);        
    }
    
    // Update method called 60 times per second.
    // Render method called 60 times per second.
    private Timer update = new Timer(1000 / 60, new Updater(this)),
            draw = new Timer(1000 / 60, new Drawer(this));
        
    /**
     * Start running the AWTGame.
     */
    public void start(){        
        init();
        update.start();
        draw.start();
    }
    
    /**
     * Set whether or not to repaint entire graphics context black
     * before each render call. Automatically false.
     * 
     * @param clear 
     */
    public void setClearOnRender(boolean clear){
        this.clearOnPaint = clear;
    }
    
    /**
     * Set how many times per second (roughly) update is called.
     * @param timesPerSecond 
     */
    public void setUpdateInterval(int timesPerSecond){
        update.setDelay(1000 / timesPerSecond);
    }
        
    /**
     * Set how many times per second (roughly) render is called.
     * @param fps 
     */
    public void setFPS(int fps){
        draw.setDelay(1000 / fps);
    }
    
    public Canvas getCanvas(){
        return this._canvas;
    }
    
    /**
     * Method that is called in when start is called; initialize any resources here
     */
    public abstract void init();
    
    /**
     * Method updates everything. Update your game here.
     * Called through a java.awt.Timer.
     */
    public abstract void update(long delta);
    
    /**
     * Method renders.
     * Called through a java.awt.Timer.
     */
    private void render(){
        
        Graphics graphics = _canvas.getBufferStrategy().getDrawGraphics();
        graphics.setColor( Color.BLACK );
        graphics.fillRect( 0, 0, _canvas.getWidth(), _canvas.getHeight());
        render(_canvas.getBufferStrategy().getDrawGraphics());
        _canvas.getBufferStrategy().getDrawGraphics().dispose();
        _canvas.getBufferStrategy().show();
                
        long currentRender = System.currentTimeMillis();
        System.out.println(1000.0 / (currentRender - lastRender));
        lastRender = currentRender;
    }
    
    /**
     * Method renders everything in the SimpleGame through a double buffered canvas.
     * Draw your game to the screen here.
     * 
     * @param g Graphics context contained internally in canvas.
     */
    protected abstract void render(Graphics g);
    
    private static class Updater implements ActionListener{

        private AWTGame _window;
        private long _prev;
        
        public Updater(AWTGame s){
            _prev = System.currentTimeMillis();
            _window = s;
        }
            
        @Override
        public void actionPerformed(ActionEvent ae) {
            long now = System.currentTimeMillis();
            _window.update(now - _prev);
            _prev = now;
        }        
    }
    
    private static class Drawer implements ActionListener{

        private AWTGame _window;
        
        public Drawer(AWTGame s){
            _window = s;
        }
            
        @Override
        public void actionPerformed(ActionEvent ae) {
            _window.render();
            
        }        
    }
}
