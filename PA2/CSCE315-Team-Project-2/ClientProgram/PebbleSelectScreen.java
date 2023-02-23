/**
 * Created by huaiwu on 3/29/17.
 */
import java.lang.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import  java.util.List;
import  java.util.ArrayList;

public class PebbleSelectScreen extends JFrame {

    JPanel inputPanel;
    JPanel userDefinePanel;
    JButton userRandomButton;
    JButton userEvenlyButton;
    JLabel pebbleLabel;
    JLabel houseLabel;
    JLabel twoPlayerLabel;
    JTextField pebbleField;
    JTextField houseField;
    JCheckBox twoPlayerCheck;

    public  PebbleSelectScreen() {
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));
        setTitle("Pebble Select");

        userDefinePanel = new JPanel();
        inputPanel = new JPanel();
        pebbleLabel = new JLabel("Total number of pebbles for each player: ");
        inputPanel.add(pebbleLabel);
        pebbleField = new JTextField(3);
        inputPanel.add(pebbleField);

        houseLabel = new JLabel("Number of houses for each player: ");
        inputPanel.add(houseLabel);
        houseField = new JTextField(3);
        inputPanel.add(houseField);

        twoPlayerLabel = new JLabel("Would you like to play with two players?: ");
        inputPanel.add(twoPlayerLabel);
        twoPlayerCheck = new JCheckBox();
        inputPanel.add(twoPlayerCheck);
 
        add(inputPanel);

        userRandomButton = new JButton("Random distribution");
        userRandomButton.addActionListener(new PebbleSelectScreen.ListenForSelect());
        userEvenlyButton = new JButton("Evenly distribution");
        userEvenlyButton.addActionListener(new PebbleSelectScreen.ListenForSelect());
        userDefinePanel.setLayout(new FlowLayout());
        userDefinePanel.add(userEvenlyButton);
        userDefinePanel.add(userRandomButton);
        add(userDefinePanel);

        setVisible(true);
    }

    private class ListenForSelect implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int pebbleInput;
            int houseInput;
            float eachHouse;

            //make sure the number of pebbles in each house is integer
            try{
                pebbleInput = Integer.parseInt(pebbleField.getText());
                houseInput = Integer.parseInt(houseField.getText());
                eachHouse = (float) pebbleInput / houseInput;
                if(eachHouse != (int) eachHouse) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException exception) {
                JOptionPane optionPane = new JOptionPane("Please make sure that the number of pebbles in each house is an integer!", JOptionPane.ERROR_MESSAGE);
                JDialog dialog = optionPane.createDialog("Input Error");
                dialog.setAlwaysOnTop(true);
                dialog.setVisible(true);
                return;
            }

            if(e.getSource() == userEvenlyButton) {
                dispose();
                int pebble = (int) eachHouse;
                List<Integer> defaultList= new ArrayList<>();
                for(int i = 0; i < houseInput; i++) {
                    defaultList.add(pebble);
                }
                new GUIFramework(defaultList);
            } else if (e.getSource() == userRandomButton) {
                dispose();
                List<Integer> randomList= RandomPebbles.RandomPebbles(pebbleInput,houseInput);
                new GUIFramework(randomList);
            }
        }
    }
}
