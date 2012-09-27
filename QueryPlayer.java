//import java.awt.Point;
import java.io.*;
import java.util.Vector;

/*
 * Jamie Ly
 
 * 
 * Created on May 6, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class QueryPlayer extends Player {
	public QueryPlayer(String name, String marker) {
		super(name,marker);
	}
	public String getMove(Game g, Board b){
		boolean isValidMove = false;
		String move;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		//Hashtable successors = g.successors(b);
		Vector legal = g.legalMoves(b);
		g.display(b);
		System.out.println("Please make your move " + name + "(" + marker + ")" +  
			" or enter 'quit' to quit. Here is a list of available moves:"+legal);
		try{			
			move = in.readLine();
			if(move.equals("quit")){
				String filename;
				System.out.println("Enter filename to save as:");
				filename = in.readLine();

				if(Board.save(b,filename)){
					System.out.println("Goodbye");
					System.exit(1); // quit			
				}
				else{
					System.out.println("Could not save, game will continue.");
				}
			}
			else{
				for(int i=0;i<legal.size();i++)
					isValidMove = isValidMove || ((String)legal.get(i)).equals(move);
				
				if(!isValidMove){
					System.out.println("Move unacceptable, try again.");				
					move = getMove(g,b);	
				}
			}
		}
		catch(IOException e){
			System.out.println("Move unacceptable, try again.");
			move = getMove(g,b);
		}

		return move;	
	}	
}
