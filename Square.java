import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.awt.geom.*;

public class Square {
	
	private static final int SIZE = 50;
	private int row, col;
	private Color clr;
	BufferedImage whiteHorse;
	BufferedImage blackHorse;
	BufferedImage horse;
	private SquareState state;
	private int moveNumber;
	
	public Square(int row, int col) {
		this.row = row;
		this.col = col;
		this.loadImages();
		this.clr = new Color(238, 238, 210);
		this.horse = blackHorse;
		if ((row+col) % 2 == 1) {
			this.clr = new Color(118, 150, 86);
			this.horse = whiteHorse;
		}
		this.state = SquareState.UNVISITED;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public SquareState getSquareState() {
		return this.state;
	}
	
	public void setSquareState(SquareState state) {
		this.state = state;
	}
	
	public void setMoveNumber(int moveNumber) {
		this.moveNumber = moveNumber;
	}
	
	public void loadImages() {
		try {
			this.blackHorse = ImageIO.read(new File("C:\\Users\\n" + //
					"oahm\\Downloads\\drive-download-20230117T220027Z-001\\BlackHorse2.png"));
			this.whiteHorse = ImageIO.read(new File("C:\\Users\\n" + //
					"oahm\\Downloads\\drive-download-20230117T220027Z-001\\WhiteHorse2.png"));
		} 
		catch (IOException e) {
		}
	}
	
	public String toString() {
		String s = String.format("%3s", "-");
		if (this.state == SquareState.NEXT_MOVE) {
			s = String.format("%3s", "N");
		}
		if (this.state == SquareState.OCCUPIED) {
			s = String.format("%3s", "O");
		}
		if (this.state == SquareState.POTENTIAL_MOVE) {
			s = String.format("%3s", "P");
		}
		if (this.state == SquareState.VISITED) {
			s = String.format("%3s", this.moveNumber);
		}
		return s;
	}
	
	public void display(Graphics g) {
		int left = SIZE * this.col;
		int top = SIZE * this.row;
		g.setColor(this.clr);
		
		if (this.state == SquareState.POTENTIAL_MOVE) {
			g.setColor(Color.RED);
		}
		else if (this.state == SquareState.NEXT_MOVE) {
			g.setColor(Color.YELLOW);
		}
		
		g.fillRect(left, top, SIZE, SIZE);
//		g.setColor(Color.BLACK);
//		g.drawRect(left, top, SIZE, SIZE);
		
		if (this.state == SquareState.OCCUPIED) {
			int x = left + (SIZE - this.horse.getWidth()) / 2;
			int y = top + (SIZE - this.horse.getHeight()) / 2;
			g.drawImage(this.horse, x, y, null );
		}
		if (this.state == SquareState.VISITED) {
			g.setColor( Color.WHITE );
			if (this.clr == Color.WHITE) {
				g.setColor( Color.BLACK );
			}
			String str = "" + this.moveNumber;
			Font font = new Font("Times New Roman", Font.BOLD, 24);
			g.setFont(font);
			FontMetrics fm = g.getFontMetrics();
			Rectangle2D rect = fm.getStringBounds(str, g);
			int x = left + (SIZE - (int)rect.getWidth() ) / 2;
			int y = top + fm.getAscent() + (SIZE - (int)rect.getHeight() ) / 2;
			g.drawString(str, x, y);
		}
	}

}
