import java.util.*;

public class Board {
    int houses;
    int seeds; //****
    int[] gamestate;    //pebbles of each house
    int hand;
    int lastAddedIndex;
    List<Integer> inputList;
    int playerToMove;
    int totalMovesMade;

    //Board constructor
    public Board(int h, List<Integer> list) {
        inputList = new ArrayList<>(list);
        houses = h;
        hand = 0;
        lastAddedIndex = -1;
        totalMovesMade = 0;

        gamestate = new int[2*h+2];
        for(int i = 0; i < h; i++) {
            gamestate[i] = inputList.get(i);
        }

        for(int i = h+1; i < 2*h + 1; i++) {
            gamestate[i] = inputList.get(i-h-1);
        }
        gamestate[h] = 0;
        gamestate[2*h+1] = 0;
    }
    //Constructor for sprint2 AI ****
    public Board(int h, int s) {
		houses = h;
		seeds = s;
		hand = 0;
		lastAddedIndex = -1;
		playerToMove = 1;
		totalMovesMade = 0;

		gamestate = new int[2*h+2];
		for(int i = 0; i < gamestate.length; i++) {
			gamestate[i] = s;
		}
		gamestate[h] = 0;
		gamestate[2*h+1] = 0;
	}
    //Copy constructor
    public Board(Board b) {
        houses = b.houses;
        inputList = new ArrayList<>(b.inputList);
        gamestate = b.gamestate;
        hand = b.hand;
        lastAddedIndex = b.lastAddedIndex;
        totalMovesMade = b.totalMovesMade;
    }
    //Copy constructor for sprint2 AI
	public Board(Board b, int garb) {
		this.houses = b.houses;
		this.seeds = b.seeds;
		this.hand = b.hand;
		this.lastAddedIndex = b.lastAddedIndex;
		this.gamestate = new int[2*b.houses+2];
		for(int i = 0; i < b.gamestate.length; i++) {
			this.gamestate[i] = b.gamestate[i];
		}
		this.playerToMove = b.playerToMove;
		totalMovesMade = b.totalMovesMade;
	}
	
    public int getTotalStones() {
        int sum = 0;
        for(int i = 0; i < inputList.size(); i++) {
            sum += inputList.get(i);
        }
        return sum;
    }
    
    public void printBoard() {
        System.out.print(' ');
        for(int i = 2*houses; i > houses; i--) {
            System.out.print(' ');
            System.out.print(gamestate[i]);
        }
        System.out.print('\n');
        System.out.print(gamestate[2*houses+1]);
        System.out.print("		");
        System.out.print(gamestate[houses]);
        System.out.print('\n');
        System.out.print(' ');
        for(int i = 0; i < houses; i++) {
            System.out.print(' ');
            System.out.print(gamestate[i]);
        }
        System.out.print('\n');
    }
    
    public void scoop(int index) {
        hand += gamestate[index];
        gamestate[index] = 0;
        totalMovesMade++;
    }
    
    public void distributeHandSouth(int index) {
        int currHouse = index;
        while(hand != 0 && currHouse < basketIndex(2)) {
            lastAddedIndex = currHouse;
            gamestate[currHouse] += 1;
            hand--;
            currHouse++;
        }
        if(hand != 0) {
            distributeHandSouth(0);
        }
    }
    
    public void distributeHandNorth(int index) {
        int currHouse = index;
        while(hand != 0 && currHouse <= basketIndex(2)) {
            lastAddedIndex = currHouse;
            gamestate[currHouse] += 1;
            hand--;
            currHouse++;
        }
        if(hand != 0) {
            currHouse = 0;
            while(hand != 0 && currHouse != basketIndex(1)) {
                lastAddedIndex = currHouse;
                gamestate[currHouse] += 1;
                hand--;
                currHouse++;
            }
            if(hand != 0) {
                distributeHandNorth(basketIndex(1)+1);
            }
        }
    }
    
    public void capture(int i) {
        int opposite = basketIndex(2) - i - 1;
        if(gamestate[opposite] != 0) {
            scoop(opposite);
            scoop(i);
            if(i < basketIndex(1)) {
                gamestate[basketIndex(1)]+=hand;
                hand = 0;
            }
            else {
                gamestate[basketIndex(2)]+=hand;
                hand = 0;
            }
        }
    }
    
    public void resetBoard() {
        for(int i = 0; i < houses; i++) {
            gamestate[i] = inputList.get(i);
        }

        for(int i = houses+1; i < 2*houses + 1; i++) {
            gamestate[i] = inputList.get(i-houses-1);
        }
        gamestate[houses] = 0;
        gamestate[2*houses+1] = 0;
        lastAddedIndex = -1;
        totalMovesMade = 0;
    }
    
    public int basketIndex(int p) {
        if(p == 1) {
            return houses;
        }
        else if(p == 2) {
            return 2*houses+1;
        }
        return -1;
    }
    
    public int sideSum(int p) {
        int sum = 0;
        if(p==1) {
            for(int i=0; i<houses; i++) {
                sum += gamestate[i];
            }
        }
        else {
            for(int i=0; i<houses; i++) {
                sum += gamestate[houses+1+i];
            }
        }
        return sum;
    }
    
    public boolean checkGameOver() {
        int sum1 = 0;
        int sum2 = 0;
        for(int i=0; i < houses; i++) {
            sum1 += gamestate[i];
            sum2 += gamestate[houses+1+i];
        }
        if(sum1 == 0 || sum2 == 0) {
            return true;
        }
        return false;
    }
    
    public boolean isNotEmpty(int i) {
        if(gamestate[i] != 0) {
        	return true;
        }
        else {
        	return false;
        }
    }
    
    public int playerScore() {
        int sum = 0;
        for(int i=0; i <= houses; i++) {
            sum += gamestate[i];
        }
        return sum;
    }
    
    public int evaluatePosition() {
        return ((gamestate[basketIndex(1)] - gamestate[basketIndex(2)]) + (sideSum(1) - sideSum(2)));
    }
    
    public void pieMove(){
    	int[] temp = new int[gamestate.length];
    	for(int i = 0; i <= houses; i++){
    		temp[i] = gamestate[i+houses + 1];
    	}
    	for(int i = houses+1; i < gamestate.length; i++){
    		temp[i] = gamestate[i-houses-1];
    	}
    	gamestate = temp;
    	totalMovesMade++;
    }
}
