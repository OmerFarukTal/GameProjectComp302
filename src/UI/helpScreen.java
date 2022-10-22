package UI;

import java.awt.*;

import javax.swing.*;

import java.awt.Graphics;

 
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
class helpScreen extends JFrame {
 
    // frame
    static JFrame f;
 
    // label to display text
    static JLabel l;
    static JLabel a;
    static JLabel b;
    static JLabel c;
    static JLabel d;
    static JLabel e;
    static JLabel t;
    static JLabel g;
    static JLabel z;
    static JLabel x;
    static JLabel v;
    static JLabel n;
    // default constructor
    helpScreen()
    {
    
 
    // main class
   
        // create a new frame to store text field and button
        f = new JFrame("Help Screen");
 
        // create a label to display text
        l = new JLabel();
        a = new JLabel();
        b = new JLabel();
        c = new JLabel();
        d = new JLabel();
        e = new JLabel();
        t = new JLabel();
        g = new JLabel();
        z = new JLabel();
        x = new JLabel();
        v = new JLabel();
        n = new JLabel();
        // add text to label
        l.setText("/////////        Instructions        /////////");
        a.setText("1)Outer Space is an easy to play game that combines fun and challenge.");
        b.setText("2)The player needs to use arrow-left and arrow-right buttons to move paddle.");
        c.setText("3)Player can rotate the paddle up to 45- or 135-degrees using A and D keys respectively. ");
        d.setText("4)Player can press letter W. During the game the player can gather some power-ups ");
        e.setText("5)To make the first shot, the player can press the the letter W. ");
        t.setText("/////////        Game Features        /////////");
        g.setText("1)There are four asteroids types:Simple asteroid,Firm-asteroid,Explosive-asteroids,Gift asteroids.");
        z.setText("2)The aliens:When gift asteroids destroyed,aliens appear and start repairing or protecting the wall.");
        x.setText("3)There are five Power-ups:Taller paddle,Magnet,Destructive Laser Gun,Chance,Wrap,Gang-of-balls ");
        v.setText("4)Building Mode:Game starts with building mode. user can load an existing game or create new one.");
        n.setText("5)Running Mode:In running mode, the user controls the paddle to break the asteroids ");
        // create a panel
        JPanel p = new JPanel();
 
        // add label to panel
        p.add(l);
        p.add(a);
        p.add(b);
        p.add(c);
        p.add(d);
        p.add(e);
        p.add(t);
        p.add(g);
        p.add(z);
        p.add(x);
        p.add(v);
        p.add(n);
        // add panel to frame
        f.add(p);
 
        // set the size of frame
        f.setSize(650,650);
 
        f.show();
    }
}

