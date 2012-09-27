/*
 * Jamie Ly
 * jal39@drexel.edu
 * CS ##:TITLE
 * Assignment ## Program ##
 * 
 * Created on Jun 1, 2004
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
import java.util.*;
public class GuiPlayer extends Player {
	GuiBoard board;
	/**
	 * @param name
	 * @param marker
	 */
	public GuiPlayer(GuiBoard g, String name, String marker) {
		super(name, marker);
		// TODO Auto-generated constructor stub
	}
	public void setGuiBoard(GuiBoard b){
		board = b;
	}
	public synchronized String getMove(Game g, Board b){
		System.out.println("Geting gui player move");
		boolean isValidMove = false;
		String move="";

		//Hashtable successors = g.successors(b);
		Vector legal = g.legalMoves(b);
		while(!isValidMove){
			while(!board.selectedCoordinatesAreSet()){
				try{wait(500);}
				catch(InterruptedException e){
				}
			}
			move = board.getSelectedRow()+","+board.getSelectedColumn();
			board.resetSelectedCoordinates();
			for(int i=0;i<legal.size();i++)
				isValidMove = isValidMove || ((String)legal.get(i)).equals(move);
		}
		System.out.println("move:"+move);				
		return move;
	}	

	public static void main(String[] args) {
	}
}
