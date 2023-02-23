import java.util.*;
import java.lang.*;

public class MinMax {
	TreeNode root;

	public MinMax(TreeNode r, int p) {
		root = r;
		root.currPlayer = p;
	}
	//depth must be >= 1
	public void recursiveExpansion(TreeNode node, int depth) {
		if(depth != 0) {
			node.generateChildren(node.boardState.playerToMove);
			for(int i=0; i<node.numChildren; i++) {
				recursiveExpansion(node.children.elementAt(i), depth-1);
			}
		}
		else {
			node.evaluateBoard();
		}
	}
	public void minMaxMove(TreeNode n) {
		if(!n.evaluated) {
			for(int i =0; i < n.numChildren; i++) {
				minMaxMove(n.children.elementAt(i));
			}
		}
		else {
			if(n.parent.boardState.playerToMove == 1) {
				if(n.parent.evaluation < n.evaluation) {
					n.parent.setWeight(n.evaluation);
				}
			}
			else {
				if(n.parent.evaluation > n.evaluation) {
					n.parent.setWeight(n.evaluation);
				}
			}
		}
	}
	public void printTree(TreeNode n) {
		if(n.numChildren != 0) {
			System.out.println("Printing Tree");
			System.out.print("Children: ");
			System.out.print(n.numChildren);
			for(int i=0; i<n.numChildren; i++) {
				System.out.print(n.children.elementAt(i).evaluation);
				System.out.print(" ");
			}
			System.out.print("\n");
			for(int i=0; i<n.numChildren; i++) {
				printTree(n.children.elementAt(i));
			}
		}
	}
	public void printChildBoards(TreeNode n) {
		if(n.numChildren != 0) {
			for(int i=0; i<n.numChildren; i++) {
				System.out.print("Board: ");
				System.out.print(i);
				System.out.print(" | Sequence: ");
				System.out.print(n.children.elementAt(i).moveString);
				System.out.print(" | Eval: ");
				System.out.print(n.children.elementAt(i).evaluation);
				System.out.println();
				n.children.elementAt(i).boardState.printBoard();
			}
		}
	}
	public int depthFromTime(int t) {
		if(t < 6) {
			return t;
		}
		else {
			int logOfT = (int) Math.log(t);
			return (5+logOfT);
		}
	}
	public static void main(String[] args) {
		/*
		Board startBoard = new Board(6,4);
		TreeNode rootNode = new TreeNode(startBoard);
		MinMax gameTree = new MinMax(rootNode, 1);

		gameTree.recursiveExpansion(gameTree.root, 2);
		//System.out.println("Before MinMax");
		//gameTree.printTree(gameTree.root);
		gameTree.printChildBoards(gameTree.root);
		gameTree.minMaxMove(gameTree.root);
		//System.out.println("After MinMax");
		//gameTree.printTree(gameTree.root);

		int eval = gameTree.root.children.elementAt(0).evaluation;
		String moves = gameTree.root.children.elementAt(0).moveString;
		for(int i=1; i < gameTree.root.numChildren; i++) {
			if(gameTree.root.children.elementAt(i).evaluation > eval) {
				eval = gameTree.root.children.elementAt(i).evaluation;
				moves = gameTree.root.children.elementAt(i).moveString;
			}
		}

		System.out.println("Best move is: ");
		System.out.print(moves);
		System.out.print(" with evaluation ");
		System.out.print(eval);
		System.out.print("\n");

		*/

		Board startBoard = new Board(6,4);
		TreeNode rootNode = new TreeNode(startBoard);
		MinMax gameTree = new MinMax(rootNode, 1);
		gameTree.root.nextPlayer = 2;

		//gameTree.root.generateChildren(1);

		gameTree.recursiveExpansion(gameTree.root, 2);
		gameTree.minMaxMove(gameTree.root);

		gameTree.printChildBoards(gameTree.root);
		gameTree.printChildBoards(gameTree.root.children.elementAt(4));



	}
}