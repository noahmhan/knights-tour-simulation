import javax.imageio.*;
import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

public class Chessboard extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private Square[][] squares;
	private int[][] accessibilityTable;
	final static int WH = 400;
	private int moveNumber;
	
	public Chessboard() {
		this.moveNumber = 0;
		this.setDoubleBuffered(true);
		this.setBounds(8, 8, WH+1, WH+1);
		this.squares = new Square[8][8];
		for (int r=0; r<8; r++) {
			for (int c=0; c<8; c++) {
				this.squares[r][c] = new Square(r, c);
			}
		}
		this.accessibilityTable = this.getAccessibilityTable();
		for (int[] row : this.accessibilityTable) {
			System.out.println(Arrays.toString(row));
		}
		
	}//constructor
	
	public void firstMove(Point pt) {
		if (this.moveNumber > 0) return;
		int row = pt.y / 50;
		int col = pt.x / 50;
		System.out.println("[R=" + row + ",C=" + col + "]");
		Square sqClicked = this.squares[row][col];
		sqClicked.setSquareState(SquareState.OCCUPIED);
		this.moveNumber = 1;
		sqClicked.setMoveNumber(this.moveNumber);
		this.setAvailableMoves(sqClicked);
		this.repaint();
	}
	
	public Square getOccupied() {
		for (Square[] row : this.squares) {
			for (Square sq : row) {
				if (sq.getSquareState() == SquareState.OCCUPIED) {
					return sq;
				}
			}
		}
		return null;
	}
	
	public void nextMove() {
		this.adjustBoardState();
		Square sqOccupied = this.getOccupied();
		sqOccupied.setMoveNumber(++this.moveNumber);
		this.setAvailableMoves(sqOccupied);
		this.repaint();
		for (int r=0; r<this.squares.length; r++) {
			for (int c=0; c<this.squares[0].length; c++) {
				System.out.println(this.squares[r][c]);
			}
		}
	}
	
	public void adjustBoardState() {
		for (int r=0; r<this.squares.length; r++) {
			for (int c=0; c<this.squares[0].length; c++) {
				Square sq = this.squares[r][c];
				if (sq.getSquareState() == SquareState.POTENTIAL_MOVE) {
					sq.setSquareState(SquareState.UNVISITED);
				}
				if (sq.getSquareState() == SquareState.OCCUPIED) {
					sq.setSquareState(SquareState.VISITED);
				}
				if (sq.getSquareState() == SquareState.NEXT_MOVE) {
					sq.setSquareState(SquareState.OCCUPIED);
				}
			}
		}
	}
	
	public boolean isValidSquare(int r, int c) {
		return (r>=0 && r<8 && c>=0 && c<8);
	}
	
	public ArrayList<Square> getAllTheoreticalMoves(int row, int col) {
		ArrayList<Square> arr = new ArrayList<Square>();
		int[] horz = {1, 2, 2, 1, -1, -2, -2, -1};
		int[] vert = {-2, -1, 1, 2, 2, 1, -1, -2};
		for (int i=0; i<horz.length; i++) {
			int r = row + vert[i];
			int c = col + horz[i];
			if (isValidSquare(r, c)) {
				arr.add(this.squares[r][c]);
			}
		}
		return arr;
	}
	
	public int[][] getAccessibilityTable() {
		int[][] table = new int[8][8];
		for (int r=0; r<8; r++) {
			for (int c=0; c<8; c++) {
				ArrayList<Square> list = getAllTheoreticalMoves(r, c);
				table[r][c] = list.size();
			}
		}
		return table;
	}
	
	public ArrayList<Square> getAvailableMoves(Square square) {
		int r = square.getRow();
		int c = square.getCol();
		ArrayList<Square> list = getAllTheoreticalMoves(r, c);
		for (int i=list.size()-1; i>=0; i--) {
			Square sq = list.get(i);
			if (sq.getSquareState() == SquareState.VISITED) {
				list.remove(i);
			}
		}
		return list;
	}
	
	public void setAvailableMoves(Square sq) {
		ArrayList<Square> availMoves = this.getAvailableMoves(sq);
		for (Square sqA : availMoves) {
			sqA.setSquareState(SquareState.POTENTIAL_MOVE);
		}
		Square sqNM = this.getNextMove(availMoves);
		sqNM.setSquareState(SquareState.NEXT_MOVE);
	}
	
	//first rough algorithm
	//choose the square with the smallest number
	//of possible moves (from the accessibility table)
	public Square getNextMove(ArrayList<Square> availMoves) {
		int smallestNumberMoves = 8;
		Square SqNM = null;
		
		for (Square sq : availMoves) {
			int r = sq.getRow();
			int c = sq.getCol();
			int nPosMoves = this.accessibilityTable[r][c];
			if (nPosMoves < smallestNumberMoves) {
				SqNM = sq;
				smallestNumberMoves = nPosMoves;
			}
		}
		return SqNM;
	}
	public void paintComponent(Graphics g) {
		this.drawBoard(g);
	}
	
	public void drawBoard(Graphics g) {
		g.setColor(new Color(238, 238, 210));
		g.fillRect(0, 0, WH, WH);
		for (Square[] sqArr : this.squares) {
			for (Square sq : sqArr) {
				sq.display(g);
			}
		}
//		g.setColor(Color.BLACK);
//		g.drawRect(0, 0, 400, 400);
		
		int x = 400;
		g.setFont(new Font("Arial", Font.BOLD, 11));
		for (int i=0; i<8; i++) {
			if (i % 2 == 1) {
				g.setColor(new Color(238, 238, 210));
			}
			else {
				g.setColor(new Color(118, 150, 86));
			}
			g.drawString("" + (8-i), 3, i*(x/8)+12);
		}
		
		for (int i=0; i<8; i++) {
			if (i % 2 == 0) {
				g.setColor(new Color(238, 238, 210));
			}
			else {
				g.setColor(new Color(118, 150, 86));
			}
			g.drawString("" + (char)(97+i), (i+1)*(x/8)-8, x-3);
		}
		
	}
	
}
