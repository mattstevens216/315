//LAYOUT **(6,4)** to user					//LAYOUT **(6,4)** to computer

//		13	12	11	10	09	08				//		12	11	10	09	08	07
//	14							07			//	13							06
//		01	02	03	04	05	06				//		00	01	02	03	04	05

//indexed by 1 								//indexed by 0

import java.util.List;
import java.util.Scanner;


public class Game {
    Board boardState;
    int currPlayer;
    boolean isGameOver;
    int input;
    int turnNumber;
    int p1Moves, p2Moves;
    //h=houses per player, s=seeds per house, p = first player
    public Game(int h, List<Integer> inputList, int p) {
        boardState = new Board(h,inputList);
        currPlayer = p;
        isGameOver = false;
        p1Moves = 0;
        p2Moves = 0;
    }
    
    public boolean isValidMove(int i) {
        if(currPlayer == 1 && i >= 0 && i < boardState.basketIndex(1) && boardState.gamestate[i] != 0) {
            turnNumber++;
            return true;
        }
        else if(currPlayer == 2 && i > boardState.basketIndex(1) && i < boardState.basketIndex(2) && boardState.gamestate[i] != 0) {
            turnNumber++;
            return true;
        }
        else {
            return false;
        }
    }
    
    public void waitForMove() {
        boardState.printBoard();
        System.out.print("Player ");
        System.out.print(currPlayer);
        System.out.print(" please enter a valid move: ");
        Scanner scanner = new Scanner(System.in);
        input = scanner.nextInt();
    }
    
    public void move(int index) {
        if(isValidMove(index)) {
            //make move m
            //switch player control accordingly
            int lastIndex;
            boardState.scoop(index);
            if(currPlayer == 1) {
                boardState.distributeHandSouth(index+1);
                lastIndex = boardState.lastAddedIndex;
                if(lastIndex != boardState.basketIndex(1)) {
                    if(boardState.gamestate[lastIndex] == 1 && lastIndex < boardState.basketIndex(1)) {
                        boardState.capture(lastIndex);
                    }
                    currPlayer = 2;
                }
                p1Moves++;
            }
            else if(currPlayer == 2) {
                boardState.distributeHandNorth(index+1);
                lastIndex = boardState.lastAddedIndex;
                if(lastIndex != boardState.basketIndex(2)) {
                    if(boardState.gamestate[lastIndex] == 1 && lastIndex > boardState.basketIndex(1)) {
                        boardState.capture(lastIndex);
                    }
                    currPlayer = 1;
                }
                p2Moves++;
            }
            if(boardState.checkGameOver()) {
                for(int i = 0; i < boardState.basketIndex(1); i++){
                    boardState.gamestate[boardState.basketIndex(1)] += boardState.gamestate[i];
                }
                for(int i = boardState.basketIndex(1) + 1; i < boardState.basketIndex(2); i++){
                    boardState.gamestate[boardState.basketIndex(2)] += boardState.gamestate[i];
                }
                isGameOver = true;
                //waitForMove();
            }
        }
        else {
            //error, request move again
            System.out.println("Invalid move, try again");
            //waitForMove();
        }
    }
    
    public void result() {
        int score1 = boardState.playerScore();
        int score2 = boardState.getTotalStones() - boardState.playerScore();
        System.out.print("Player 1 Score: ");
        System.out.print(score1);
        System.out.print("\n");
        System.out.print("Player 2 Score: ");
        System.out.print(score2);
        System.out.print("\n");
    }
    
    public void gameFlow() {
        waitForMove();
        while(!isGameOver) {
            move(input-1);
            isGameOver=boardState.checkGameOver();
        }
        System.out.println("Game Over");
        result();
    }
    
    public void pieMove(){
    	if(p1Moves == 0 || p2Moves == 0){
    		boardState.pieMove();
    		if(currPlayer == 1){
    			currPlayer = 2;
    			p1Moves++;
    		}
    		else{
    			currPlayer = 1;
    			p2Moves++;
    		}
    	}
    }
/*
	public static void main(String[] args) {
		Game kalahGame = new Game(6,4,1);
		kalahGame.gameFlow();
	}*/
}


