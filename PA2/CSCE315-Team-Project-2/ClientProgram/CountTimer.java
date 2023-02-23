import java.awt.*;
import java.text.SimpleDateFormat; 
import java.util.*; 
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
public class CountTimer extends JPanel
{ 
    private int time;
    
    public CountTimer(int t) {
        time = t;
    }
    public void paint(Graphics g) { 
                
        Timer timer = new Timer();
        timer.schedule(new timertask(),  1000); //run this every 1 second
        super.paint(g); 
        g.drawString("Left time\n" + time + " S", 10, 10); } 
                
    public void refresh(int t){
        time = t;
    }
        
        
    class timertask extends TimerTask {
        public void run() {
            time = time - 1;  //decrease 1 second;
            repaint();
            if(time <= 0) {
                refresh(5);
            }
        } 
    } 
                
} 