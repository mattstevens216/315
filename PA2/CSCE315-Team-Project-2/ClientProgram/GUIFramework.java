/**
 * Created by huaiwu on 3/20/17.
 */

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.List;

// Extends JFrame so it can create frames

public class GUIFramework extends JFrame {
    JPanel thePanel;
    JPanel theGamePanel;
    JPanel theNodePanel;
    JButton buttonHint;
    JButton buttonNodes[];
    JButton twoPlayer;
    JButton rules;
    JButton pieButton;
    JLabel invalidMove;
    JButton forfeit;
    JLabel labelCredits[];
    //boolean hintShow;
    int buttonClicked;
    String socketCommand;
    Game game;
    Thread serverThread;
    Socket server;
	PrintWriter out;
	BufferedReader in;

    public void parseCommand(String socketCommand){
        String[] command = socketCommand.split(" ");
        if(command.length == 1){
           if(command[0].equals("WELCOME"))
                System.out.print("WELCOME");
           else if(command[0].equals("READY"))
                System.out.print("READY");
           else if(command[0].equals("OK"))
                System.out.print("OK");
           else if(command[0].equals("ILLEGAL"))
                System.out.print("ILLEGAL MOVE");
           else if(command[0].equals("TIME"))
                System.out.print("TIME ERROR");
           else if(command[0].equals("LOSER"))
                new EndGameScreen(2, this);
           else if(command[0].equals("WINNER"))
                new EndGameScreen(1, this);
           else if(command[0].equals("TIE"))
                new EndGameScreen(3, this);
           else
        	   System.out.println("Unrecognized command");
           
        }
        else if(command[0].equals("INFO")){
        	ArrayList<Integer> inputList = new ArrayList<Integer>();
        	int houses, seeds, player;
        	if(command[5].equals("S")){
        		houses = Integer.parseInt(command[1]);
        		seeds = Integer.parseInt(command[2]);
        		for(int i = 0; i < houses*2; i++){
        			inputList.add(seeds);
        		}
        	}
        	else if(command[5].equals("R")){
        		houses = Integer.parseInt(command[1]);
        		seeds = Integer.parseInt(command[2]);
        		for(int i = 0; i < houses; i++){
        			inputList.add(Integer.parseInt(command[i+6]));
        		}
        		inputList.addAll(inputList);
        	}
        	else{
        		
        	}
        	if(command[4].equals("F")){
        		player = 1;
        	}
        	else{
        		player = 2;
        	}
        	game = new Game(Integer.parseInt(command[1]), inputList, player);
        }
        else{
        	if(command[0].equals("P")){
        		//run pie rule switch
        	}
        	else{
        		for(int i = 0; i < command.length; i++){
        			game.move(Integer.parseInt(command[i]));
        		}
        	}
        }
        
        updateNodes();
    }
    public void updateNodes(){
        for(int i = 0; i < game.boardState.gamestate.length; i++){
            buttonNodes[i].setText(Integer.toString(game.boardState.gamestate[i]));
            //System.out.print(game.boardState.gamestate[i]);
        }

        for(int i = 0; i < game.boardState.basketIndex(1); i++){
            buttonNodes[i].setEnabled(game.currPlayer == 1);
        }
        for(int i = game.boardState.basketIndex(1) + 1; i < game.boardState.basketIndex(2); i++){
            buttonNodes[i].setEnabled(game.currPlayer == 2);
        }
        labelCredits[0].setText("Player 1: " + game.p1Moves);
        labelCredits[1].setText("Player 2: " + game.p2Moves);
        pieButton.setEnabled(game.p1Moves == 0 || game.p2Moves == 0);
        //System.out.println();
        revalidate();

    }

    public void endGame(){
        if(game.boardState.gamestate[game.boardState.basketIndex(1)] > game.boardState.gamestate[game.boardState.basketIndex(2)]){
            new EndGameScreen(1, this);
        }
        else{
            new EndGameScreen(2, this);
        }
        game.isGameOver = false;
    }

    public void forfeitGame(){
        new EndGameScreen(0, this);
        game.isGameOver = false;
    }


    //constructor
    public GUIFramework(java.util.List<Integer> inputList) {
        setSize(1000, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("GUIFramework");
        setLayout(new GridLayout(3, 1));
        thePanel = new JPanel();
        game = new Game(inputList.size(),inputList,1);
        serverThread = new Thread(new Server());
        serverThread.start();
        try {
			server = new Socket(InetAddress.getLocalHost() , 7777);
			out = new PrintWriter(server.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(server.getInputStream()));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

        invalidMove = new JLabel("Click a Node"); //Keep width correct for GUI when moves are valid;
        //invalidMove.setIcon(new ImageIcon(this.getClass().getResource("invalid.png")));
        thePanel.add(invalidMove);
        
        forfeit = new JButton("Forfeit Game");
        forfeit.addActionListener(new ListenForButton());
        thePanel.add(forfeit);

        buttonHint = new JButton();
        buttonHint.setIcon(new ImageIcon(this.getClass().getResource("hint.png")));
        buttonHint.addActionListener(new ListenForButton());
        thePanel.add(buttonHint);

        twoPlayer = new JButton();
        twoPlayer.setIcon(new ImageIcon(this.getClass().getResource("2player.png")));
        twoPlayer.addActionListener(new ListenForButton());
        thePanel.add(twoPlayer); //two player gui

        rules = new JButton("Rules");
        rules.addActionListener(new ListenForButton());
        thePanel.add(rules);
        
        pieButton = new JButton("Pie Move");
        pieButton.setEnabled(false);
        pieButton.addActionListener(new ListenForButton());
        thePanel.add(pieButton);

        labelCredits = new JLabel[2];
        labelCredits[0] = new JLabel();
        labelCredits[1] = new JLabel();
        labelCredits[0].setText("Player1: 0");
        labelCredits[1].setText("Player2: 0");
        thePanel.add(labelCredits[0]);
        thePanel.add(labelCredits[1]);


        add(thePanel);
        setVisible(true);

        //the Panel of 14 nodes
        theGamePanel = new JPanel();
        theNodePanel = new JPanel();
        theGamePanel.setLayout(new FlowLayout(1, 15, 0));
        theNodePanel.setLayout(new GridLayout(2, 6, 15, 15));

        /*-----------------------------------------------------------------------------------------------------

        Panel layout:

            buttonNodes[12] buttonNodes[11] buttonNodes[10] buttonNodes[9] buttonNodes[8] buttonNodes[7]
        buttonNodes[13]                                                                          buttonNodes[6]
            buttonNodes[0] buttonNodes[1] buttonNodes[2] buttonNodes[3] buttonNodes[4] buttonNodes[5]

         ------------------------------------------------------------------------------------------------------*/

        buttonNodes = new JButton[game.boardState.gamestate.length];
        for(int i = game.boardState.basketIndex(2)-1; i > game.boardState.basketIndex(1); i--) {
            buttonNodes[i] = new JButton(Integer.toString(game.boardState.gamestate[i]));
            buttonNodes[i].setName(Integer.toString(i));
            buttonNodes[i].addActionListener(new ListenForButton());
            theNodePanel.add(buttonNodes[i]);
        }

        for(int i = 0; i < game.boardState.basketIndex(1); i++) {
            buttonNodes[i] = new JButton(Integer.toString(game.boardState.gamestate[i]));
            buttonNodes[i].setName(Integer.toString(i));
            buttonNodes[i].addActionListener(new ListenForButton());
            theNodePanel.add(buttonNodes[i]);
        }

        //Buttons to represent the stores, no ActionListener since pressing shouldn't do anything
        buttonNodes[game.boardState.basketIndex(2)] = new JButton(Integer.toString(game.boardState.gamestate[game.boardState.basketIndex(2)]));
        buttonNodes[game.boardState.basketIndex(2)].setName(Integer.toString(game.boardState.basketIndex(2)));
        buttonNodes[game.boardState.basketIndex(2)].setEnabled(false);
        theGamePanel.add(buttonNodes[game.boardState.basketIndex(2)]);
        theGamePanel.add(theNodePanel);
        buttonNodes[game.boardState.basketIndex(1)] = new JButton(Integer.toString(game.boardState.gamestate[game.boardState.basketIndex(1)]));
        buttonNodes[game.boardState.basketIndex(1)].setName(Integer.toString(game.boardState.basketIndex(1)));
        theGamePanel.add(buttonNodes[game.boardState.basketIndex(1)]);
        buttonNodes[game.boardState.basketIndex(1)].setEnabled(false);


        add(theGamePanel);

        CountTimer counter=new CountTimer(5);
        add(counter);
        setVisible(true);
    }

    private class EndGameScreen extends JFrame implements ActionListener{
        JButton restartGame;
        JButton quit;
        JTextPane results;
        JPanel buttons;
        GUIFramework gameFrame;

        public EndGameScreen(int playerWon, GUIFramework parent){
            gameFrame = parent;

            restartGame = new JButton("Restart Game");
            restartGame.addActionListener(this);

            quit = new JButton("Quit");
            quit.addActionListener(this);

            results = new JTextPane();
            if(playerWon > 0){
                results.setText("Player " + playerWon + " won!\nScore: " + game.boardState.gamestate[game.boardState.basketIndex(1)] + " - " + game.boardState.gamestate[game.boardState.basketIndex(2)]);
            }
            else{
                results.setText("Game Forfeited\nStart a new game or quit the game");
            }
            results.setEditable(false);
            results.setBackground(this.getBackground());
            results.setFont(new Font("Arial", Font.BOLD, 30));

            StyledDocument doc = results.getStyledDocument();
            SimpleAttributeSet center = new SimpleAttributeSet();
            StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
            doc.setParagraphAttributes(0, doc.getLength(), center, false);

            buttons = new JPanel();
            buttons.setLayout(new GridLayout(2,1));
            buttons.add(restartGame);
            buttons.add(quit);

            setLayout(new BorderLayout());
            add(results, BorderLayout.CENTER);
            add(buttons, BorderLayout.SOUTH);

            setSize(800, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setTitle("Game Over");
            setVisible(true);

        }

        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == quit){
                System.exit(0);
            }
            else if(e.getSource() == restartGame){
                game.boardState.resetBoard();
                game.p1Moves = 0;
                game.p2Moves = 0;
                game.isGameOver = false;
                game.turnNumber = 0;
                setVisible(false);
                gameFrame.updateNodes();
            }
        }
    }



    /*-----------------------------------------------------------------------------------------------------

     Implements ActionListener so it can react to events on components

    ------------------------------------------------------------------------------------------------------*/
    private class ListenForButton implements ActionListener {

        // This method is called when an event occurs

        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == buttonHint){
                //If the button is clicked, then change the turn
                if(game.currPlayer == 1) {
                    JOptionPane.showMessageDialog(null, "Player's turn", "Hint", 1);
                } else {
                    JOptionPane.showMessageDialog(null, "AI's turn", "Hint", 1);
                }

            }
            else if(e.getSource() == rules){
                JOptionPane.showMessageDialog(null, "Four piecesmarbles or stonesare placed in each of the 12 holes."+
                        "\nEach player has a store to the right side of the Mancala board." +
                        "\nThe game begins with one player picking up all of the pieces in any one of the holes on his side." +
                        "\nMoving counter-clockwise, the player deposits one of the stones in each hole until the stones run out." +
                        "\nIf you run into your own store, deposit one piece in it. If you run into your opponent's store, skip it." +
                        "\nIf the last piece you drop is in your own store, you get a free turn." +
                        "\nIf the last piece you drop is in an empty hole on your side, you capture that piece and any pieces in the hole directly opposite. " +
                        "\nAlways place all captured pieces in your store." +
                        "\nThe game ends when all six spaces on one side of the Mancala board are empty." +
                        "\nThe player who still has pieces on his side of the board when the game ends captures all of those pieces." +
                        "\nCount all the pieces in each store. The winner is the player with the most pieces.");
            }
            else if(e.getSource() == forfeit){
                forfeitGame();
            }
            else if(e.getSource() == twoPlayer){
                	
            }
            else if(e.getSource() == pieButton){
            	game.pieMove();
            	updateNodes();
            	pieButton.setEnabled(false);
            }
            else {
                //Check which node is clicked(node 1--6 and 8--13)

                JButton j= (JButton) e.getSource();
                int move = Integer.parseInt(j.getName());
                //System.out.println("" + move + " is before sending to move()");
                if(game.isValidMove(move)){
                    //System.out.println("Valid move: " + (move));
                    game.move(move);
                    invalidMove.setText("Click a Node");
                }
                else{
                	invalidMove.setText("Invalid Move");
                }
                updateNodes();
                if(game.isGameOver){
                    endGame();
                }
            }
        }
    }
}

