import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class KnightsTour extends JFrame implements ActionListener, MouseListener {

	private Chessboard chessboard;
	private JButton nextMoveButton;
	final String strNEXTMOVE = "Next Move";
	
	public KnightsTour() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(430, 490);
		this.setLocationRelativeTo(null);
		this.setTitle("Knight's Tour 2022B");
		
		Container contentPane = this.getContentPane();
		contentPane.setLayout(null);
		contentPane.setBackground(Color.WHITE);
		this.chessboard = new Chessboard();
		this.chessboard.addMouseListener(this);
		
		contentPane.add(this.chessboard);
		
		this.nextMoveButton = new JButton(strNEXTMOVE);
		this.nextMoveButton.setBounds(6, 420, 93, 24);
		this.nextMoveButton.addActionListener(this);
		this.nextMoveButton.setEnabled(false);
		contentPane.add(this.nextMoveButton);
		this.setVisible(true);
	}//constructor
	
	public static void main(String[] args) {
		new KnightsTour();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Point pt = e.getPoint();
		String s = "(" + pt.x + "," + pt.y + ")";
		System.out.println(s);
		this.chessboard.firstMove(pt);
		this.nextMoveButton.setEnabled(true);
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		String sCommand = e.getActionCommand();
		if (sCommand.equalsIgnoreCase(strNEXTMOVE)) {
			this.chessboard.nextMove();
		}
	}

}
