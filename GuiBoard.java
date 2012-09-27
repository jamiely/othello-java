import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
/*
 * Jamie Ly
 * jal39@drexel.edu
 * CS ##:TITLE
 * Assignment ## Program ##
 * 
 * Created on May 31, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author DaAznAngel
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GuiBoard extends JPanel implements ActionListener{
	Board board;
	GuiBoardPanel[][] panels;
	int selectedRow = -1, selectedColumn = -1;
	/**
	 * 
	 */
	
	public GuiBoard(Board b, JApplet a) {
		super();
		setLayout(new GridLayout(0,b.getCols()));
		setBorder(BorderFactory.createLineBorder(Color.WHITE));
		board = b;
		panels = new GuiBoardPanel[b.getRows()][b.getCols()];
		for(int r=0;r<b.getRows();r++){
			for(int c=0;c<b.getCols();c++){
				panels[r][c] = new GuiBoardPanel(a);
				panels[r][c].setCoordinates(b.getRows()-1-r,c);
				panels[r][c].addActionListener(this);
				add(panels[r][c]);
			}
		}
		show();
	}
	public void update(){
		for(int r=0;r<board.getRows();r++){
			for(int c=0;c<board.getCols();c++){
				if(!board.getMarkerAt(r,c).equals("."))
					panels[board.getRows()-r-1][c].setState(board.getMarkerAt(r,c).equals("x")?
						GuiBoardPanel.BLACK : GuiBoardPanel.WHITE);
				else
					panels[board.getRows()-r-1][c].setState(GuiBoardPanel.NONE);
			}
		}
		String temp;
		String coords[];
		Vector v = Game.legalMoves(board);
		for(int i=0;i<v.size();i++){
			temp =(String) v.get(i);
			coords = temp.split(",");
			panels[board.getRows()-1-Integer.parseInt(coords[0])][Integer.parseInt(coords[1])].
				setState(GuiBoardPanel.LEGAL);
		}
	}
	public void setBoard(Board b){
		board = b;
		update();
	}
	public void actionPerformed(ActionEvent e){
		GuiBoardPanel g = (GuiBoardPanel) e.getSource();
		setSelectedRow(g.getRow()); setSelectedColumn(g.getCol());
		//System.out.println("Row:"+g.getRow() + " Col:" + g.getCol());
	}
	/**
	 * @return
	 */
	public int getSelectedColumn() {
		return selectedColumn;
	}

	/**
	 * @return
	 */
	public int getSelectedRow() {
		return selectedRow;
	}

	/**
	 * @param i
	 */
	public void setSelectedColumn(int i) {
		selectedColumn = i;
	}

	/**
	 * @param i
	 */
	public void setSelectedRow(int i) {
		selectedRow = i;
	}
	public void resetSelectedCoordinates(){
		setSelectedRow(-1); setSelectedColumn(-1);
	}
	public boolean selectedCoordinatesAreSet(){
		return getSelectedRow() >= 0 && getSelectedColumn() >= 0;
	}
}
