import java.util.Vector;

/*
 * Jamie Ly
 * jal39@drexel.edu
 * CS 451: Software Engineering
 * Assignment 2 
 *  
 * Created on May 7, 2004
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
public class RandomPlayer extends Player {

	/**
	 * @param name
	 * @param marker
	 */
	public RandomPlayer(String name, String marker) {
		super(name, marker);
	}
	public String getMove(Game g, Board b){
		b.display();
		Vector v = g.legalMoves(b);
		int index = (int) Math.floor(Math.random() * v.size());
		return (String) v.get(index); 
	}
}
