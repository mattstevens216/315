/**
 * Created by huaiwu on 3/28/17.
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;

import javax.swing.*;

public class WelcomeScreen extends JFrame{

    ImageIcon image;
    JLabel lableImage;
    JPanel panelImage;
    JButton startButton;

    public  WelcomeScreen() {
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1));
        setTitle("Greeting");

        image = new ImageIcon("welcome.png");
        lableImage = new JLabel("", image, JLabel.CENTER);
        panelImage = new JPanel(new BorderLayout());
        panelImage.add(lableImage, BorderLayout.CENTER );
        add(panelImage);

        startButton = new JButton();
        startButton.setOpaque(false);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        startButton.setIcon(new ImageIcon(this.getClass().getResource("start.png")));
        startButton.addActionListener(new WelcomeScreen.ListenForStart());
        add(startButton);
        setVisible(true);
    }

    private class ListenForStart implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == startButton) {
                dispose();
                new PebbleSelectScreen();
            }
        }
    }
}