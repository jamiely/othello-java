import java.awt.*;
import java.util.*;
import javax.swing.*;

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
public class OthelloApplication extends JApplet {
	Game game;
	GuiBoard board;
	public void init(){
		game = new Game();
		this.setJMenuBar(new OthelloApplicationMenuBar(game));
		
		board = new GuiBoard(game.getInitial(),this);
		board.setBoard(game.getInitial());
		game.setListener(new GuiListener(board, this));
		getContentPane().add(board);
		//pack();
	}
	public void start(){
		Vector players = new Vector();
		//players.add(new RandomPlayer("AI_X","x"));
		players.add(new GuiPlayer(null,"Human","x"));
		players.add(new RandomPlayer("AI_O","o"));
		game.setPlayers(players);
		
		//OthelloApplication app = new OthelloApplication(foo);
		
		((GuiPlayer)players.get(0)).setGuiBoard(getBoard());
		show();
		//JFrame main = new JFrame();
		//main.getContentPane().add(app);
		//main.show();
		//foo.playGame(players);	
	}
	public static void main(String[] args) {
		
		
		
	}
	/**
	 * @return
	 */
	public GuiBoard getBoard() {
		return board;
	}

	/**
	 * @param board
	 */
	public void setBoard(GuiBoard board) {
		this.board = board;
	}

}
