/*
 * Jamie Ly

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
public class GuiListener extends OthelloAdapter {
	GuiBoard board; 
	OthelloApplication application;
	public GuiListener(GuiBoard b,OthelloApplication a){
		board = b;
		application = a;
	}
	public void moveRetrieved(Player p, String move){
		board.update();
	}
	public void newGame(){
		board.update();
	}
	public void newState(Board b){
		board.setBoard(b);
	}
	public void gettingMove(Player p){
		//board.update();
	}
	public static void main(String[] args) {
	}
}
