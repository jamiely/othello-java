import java.util.Hashtable;
import java.util.Vector;
import java.io.*;

/*
 * Jamie Ly
 * jal39@drexel.edu
 * CS 451: Software Engineering
 * Assignment 2 
 * 
 * Created on May 6, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class Game implements Runnable {
	Board initial;
	OthelloListener listener;
	Board state;
	boolean forceQuit = false;//, playersSet=false;
	boolean playersSet=false;
	Board toLoad = null;
	Vector players;
	/**
	 * 
	 */
	public Game() {
		super();
		listener = new OthelloAdapter();
		initial = new Board(8,8);
		try{
			initial.setMarker(3,3,initial.getPlayerName(1));
			initial.setMarker(3,4,initial.getPlayerName(0));
			initial.setMarker(4,3,initial.getPlayerName(0));
			initial.setMarker(4,4,initial.getPlayerName(1));
			//initial.nextPlayer();
			//initial.setToMove("o");
		}catch(Exception e){
			System.out.println("Could not initialize board. Error:"+e.getMessage());
		}
		setCurrentState((Board)getInitial().clone());
	}
	/**
	 * Gets a list of the legal moves
	 * @param b board, state information
	 * @return  a list of legal moves 
	 */
	public static Vector legalMoves(Board b){
		Vector v = new Vector();
		String index;
		for(int r = 0;r<b.getRows();r++){
			for(int c=0;c<b.getCols();c++){
				index = b.makeIndex(r,c);
				//System.out.println(index);
				if(!b.isFree(index)){
					//System.out.println(index+" Not free");
				}
				if(b.isFree(index)&&b.isAdjacentToEnemy(index)
					&& b.flankingPositions(index,b.getMarker(b.getToMove()),
						b.getMarker(b.getNotToMove())).size() > 0){
					v.add(index);
				}
			}
		}
		return v;	
	}
	/**
	 * Applies move to the board
	 * @param move a string of the form "row,col"
	 * @param b    the current board state
	 * @return     the board that was passed in, having been altered by move
	 */
	public Board makeMove(String move,Board b){
		try{
			b.makeMove(move,null);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return b; // return board after move
	}
	/**
	 * Here in case I want to implement adverserial search later
	 * @param b
	 * @param player
	 * @return
	 */
	public double utility(Board b, String player){
		return 0;
	}
	/**
	 * returns true if game is over
	 * @param b board state
	 * @return  true if game is over
	 */
	public boolean terminalTest(Board b){
		Board bcopy = (Board) b.clone();
		bcopy.nextPlayer();
		return b.isFull() ||
			(this.legalMoves(b).size() == 0 && 
			this.legalMoves(bcopy).size() == 0);
	}
	public String toMove(Board b){
		return b.getToMove();
	}
	public void display(Board b){
		b.display();
	}
	/**
	 * Here to implement adverserial search
	 * @param b board state
	 * @return
	 */
	public Hashtable successors(Board b){
		Hashtable h = new Hashtable();
		Vector legal = legalMoves(b);
		String move;
		while(legal.size() > 0){
			move = (String) legal.remove(0);
			h.put(move,makeMove(move,b)); 
		}
		return h;
	}
	public Board getInitial(){
		return (Board) this.initial.clone();
	}
	/**
	 * Begins the game playing process
	 * @param players
	 */
	public void playGame(Vector players){
		//Board state;
		//if (state == null) state= getInitial();
		
		//boolean newGame = true; 
	//	boolean fileIsValid = false;
	//	int response=0;
		
		/*
		String fileName="";
		System.out.println("Start New Game? (Y/N)");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		try{
			response = in.read();
		}catch(IOException e){
			System.out.println("Improper input.");
		}
			
			
		if(response == 'y' || response == 'Y')
			state= getInitial();
		else
			while(!fileIsValid){
				// check input
				// maybe display menu of available games...
				System.out.println("Type 'menu' to display available games or type filename of saved game?");
				try{
					fileName = in.readLine();
				}catch(IOException e){
					//
				}
				
				if(fileName.equals("menu")){
					// display menu
					// accept numeric input
				}
				else{
					try{
						state = Board.load(fileName);
						fileIsValid = state != null;
					}catch(Exception e){
						System.out.println("File does not exists. Enter filename: ");
					}
				}
			}
*/
		String move;
		Vector moves;
		int [] count = new int[2];
		count[0] = count[1] = 0;
		listener.newGame();
		while (true){
			for(int i=0;i<players.size() && !forceQuit;i++){
				moves  = legalMoves(state);
				System.out.println("Legal moves:"+moves);
				if(moves.size() == 0){
					System.out.println("-------Your turn is forfeited.");
					state.display();
					System.out.println("------------------------------");
					state.nextPlayer();
					continue;
				}
				listener.gettingMove((Player) players.get(i));
				move = ((Player) players.get(i)).getMove(this,state);
				state = makeMove(move,state);
				//currentState = state;
				listener.moveRetrieved((Player) players.get(i),move);
				state.nextPlayer();
				listener.newState(state);
				if(terminalTest(state)){
					listener.gameEnded();
					//System.out.println(((Player)players.get(i+1)) + " has lost");
					state.display();
					for(int r=0;r<state.getRows();r++)
						for(int c=0;c<state.getCols();c++)
							if(state.getMarkerAt(r,c)!=state.getMarker("default"))
								count[state.getMarkerAt(r,c).equals("x")?0:1]++;
					for(int j=0;j<players.size();j++){
						Player p = (Player) players.get(j);
						System.out.println(p.name + " (" + p.marker + ") " + 
							":" + count[j] + " pieces");
					}
					if(count[0]==count[1])
						System.out.println("The game is a tie.");
					else
						System.out.println("Player " + (count[0]>count[1]?1:2) + " has won");
					return; //utility(state, players.get(i));
				}
				
			}
		}
		
		//state = forceQuit ? toLoad : null;
		//state.display();
		//forceQuit = false;
		//if(state!= null){
			//playGame(players);
		//	listener.newState(state);
		//}
	}
	public static void main(String args[]){
		Game foo = new Game();
		Vector players = new Vector();
		//players.add(new RandomPlayer("AI_X","x"));
		players.add(new QueryPlayer("Human","x"));
		players.add(new RandomPlayer("AI_O","o"));
		foo.playGame(players);
	}
	/**
	 * @return
	 */
	public OthelloListener getListener() {
		return listener;
	}

	/**
	 * @param listener
	 */
	public void setListener(OthelloListener listener) {
		this.listener = listener;
	}
	public Board getCurrentState(){
		return state;
	}
	public void setCurrentState(Board c){
		state = c;
		listener.newState(c);
	}
	public void setToLoad(Board b){
		toLoad = b;
	}
	public void quit(){
		forceQuit = true;
	}
	public boolean willQuit(){
		return forceQuit;
	}
	public void setPlayers(Vector p){
		players = p;
		playersSet = true;
	}
	public boolean playersAreSet(){
		return playersSet;
	}
	public void run(){
		if(playersAreSet())
			this.playGame(players);
	}
}