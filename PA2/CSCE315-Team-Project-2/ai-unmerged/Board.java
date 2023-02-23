public class Board {
	int houses;
	int seeds;
	int[] gamestate;
	int hand;
	int lastAddedIndex;
	int playerToMove;

	//Board constructor
	public Board(int h, int s) {
		houses = h;
		seeds = s;
		hand = 0;
		lastAddedIndex = -1;
		playerToMove = 1;

		gamestate = new int[2*h+2];
		for(int i = 0; i < gamestate.length; i++) {
			gamestate[i] = s;
		}
		gamestate[h] = 0;
		gamestate[2*h+1] = 0;
	}
	//Copy constructor
	public Board(Board b) {
		this.houses = b.houses;
		this.seeds = b.seeds;
		this.hand = b.hand;
		this.lastAddedIndex = b.lastAddedIndex;
		this.gamestate = new int[2*b.houses+2];
		for(int i = 0; i < b.gamestate.length; i++) {
			this.gamestate[i] = b.gamestate[i];
		}
		this.playerToMove = b.playerToMove;
	}
	public int getTotalStones() {
		return (2*houses*seeds);
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
		this.hand += this.gamestate[index];
		this.gamestate[index] = 0;
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
		for(int i = 0; i < gamestate.length; i++) {
			gamestate[i] = seeds;
		}
		gamestate[houses] = 0;
		gamestate[2*houses+1] = 0;
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
}