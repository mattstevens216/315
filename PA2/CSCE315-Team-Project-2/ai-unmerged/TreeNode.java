import java.util.*;

public class TreeNode {
	int evaluation;
	boolean evaluated;
	Board boardState;
	Vector<TreeNode> children;
	TreeNode parent;
	int currPlayer; //1 or 2 //will determine min/max
	int nextPlayer;
	int numChildren;
	String moveString;

	public TreeNode(Board b) {
		evaluation = 0;
		evaluated = false;
		boardState = b;
		children = new Vector<TreeNode>();
		numChildren = 0;
		moveString = "";
	}
	public void addChild(TreeNode c) {
		children.add(c);
		c.parent = this;
	}
	public int getWeight() {
		return evaluation;
	}
	public void setWeight(int d) {
		evaluation = d;
		evaluated = true;
	}
	public Vector getChildren() {
		return children;
	}
	public TreeNode getParent() {
		return parent;
	}
	public boolean isValidMove(int i) {
		if(currPlayer == 1 && i >= 0 && i < boardState.basketIndex(1) && boardState.gamestate[i] != 0) {
			return true;
		}
		else if(currPlayer == 2 && i > boardState.basketIndex(1) && i < boardState.basketIndex(2) && boardState.gamestate[i] != 0) {
			return true;
		}
		else {
			return false;
		}
	}
	public Board move(int index, Board parentBoard) {
		if(isValidMove(index)) {
			//make move m
			//switch player control accordingly
			Board childBoard = new Board(parentBoard);
			int lastIndex;
			childBoard.scoop(index);
			if(parentBoard.playerToMove == 1) {
				childBoard.distributeHandSouth(index+1);
				lastIndex = childBoard.lastAddedIndex;
				if(lastIndex != childBoard.basketIndex(1)) {
					if(childBoard.gamestate[lastIndex] == 1 && lastIndex < childBoard.basketIndex(1)) {
						childBoard.capture(lastIndex);
					}
					nextPlayer = 2;
					childBoard.playerToMove = 2;
				}
				else {
					nextPlayer = 1;
					childBoard.playerToMove = 1;
				}
			}
			else if(parentBoard.playerToMove == 2) {
				childBoard.distributeHandNorth(index+1);
				lastIndex = childBoard.lastAddedIndex;
				if(lastIndex != childBoard.basketIndex(2)) {
					if(childBoard.gamestate[lastIndex] == 1 && lastIndex > childBoard.basketIndex(1)) {
						childBoard.capture(lastIndex);
					}
					nextPlayer = 1;
					childBoard.playerToMove = 1;
				}
				else {
					nextPlayer = 2;
					childBoard.playerToMove = 2;
				}
			}
			if(!childBoard.checkGameOver()) {
				//??
			}
			System.out.print("Move made: ");
			System.out.print(index);
			System.out.print("\nCurrent: ");
			System.out.print(parentBoard.playerToMove);
			System.out.print("\nNext: ");
			System.out.print(childBoard.playerToMove);
			return childBoard;
		}
		else {
			return null;
		}
	}
	public void evaluateBoard() {
		evaluation = boardState.evaluatePosition();
		evaluated = true;
		//System.out.println(evaluation);
	}
	public void generateChildren(int p) {
		//current player p generates valid moves
		int modifier;
		if(p == 1) {
			modifier = 0;
		}
		else {
			modifier = boardState.houses + 1;
		}
		for(int i=0; i<boardState.houses; i++) {
			Board result = move(i+modifier,boardState);
			if(result != null && result.playerToMove != boardState.playerToMove) {
				//boardState.printBoard();
				TreeNode newNode = new TreeNode(result);
				newNode.currPlayer = nextPlayer;
				newNode.moveString = "" + (i+1+modifier);
				this.addChild(newNode);
				numChildren+=1;
			}
			else if(result != null) {
				String m = "" + (i+1+modifier);
				turnCollapse(result, m, modifier);
			}
		}
	}
	public void turnCollapse(Board currState, String moves, int mod) {
		for(int i=0; i<currState.houses; i++) {
			if(currState.isNotEmpty(i+mod)) {
				Board afterState = move(i+mod,currState);
				String newMoves = moves + " " + (i+1+mod);
				if(afterState.playerToMove == currState.playerToMove) {
					turnCollapse(afterState, newMoves, mod);
				}
				else {
					TreeNode newNode = new TreeNode(afterState);
					newNode.currPlayer = nextPlayer;
					newNode.moveString = newMoves;
					this.addChild(newNode);
					numChildren+=1;
				}
			}
		}
	}
}





//refactor generateChildren()
/*
at each level it must expand until it is the other players turn
thus the first level would look like

[1,2,3-1,3-2,3-4,3-5,3-6,4,5,6]

new variable called moveSqnc (string) "3 1"
if game over evaluate position immediately 
update tree once each level is explored

*/


