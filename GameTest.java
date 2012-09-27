import junit.framework.*;
import java.util.*;
public class GameTest extends TestCase{
	static int row=8, col=8;
	public static Board defaultBoard(){
		Board b = new Board(row,col);
		try{
			b.setMarker(3,3,b.getPlayerName(1));
			b.setMarker(3,4,b.getPlayerName(0));
			b.setMarker(4,3,b.getPlayerName(0));
			b.setMarker(4,4,b.getPlayerName(1));
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		return b;
	}
	public static QueryPlayer getPlayer(){
		return new QueryPlayer("Thomas","x");
	}
	public void testNameMarker(){
		QueryPlayer p = getPlayer();
		assertTrue(p.getName().equals("Thomas"));
		assertTrue(p.getMarker().equals("x"));
	}
	public void testMakeMove(){
		Game g = new Game();
		Board b = defaultBoard();
		QueryPlayer p = getPlayer();
		String move = p.getMove(g,b);
		assertTrue(move.equals("2,3"));
		g.makeMove(move,b);
		assertTrue(b.getMarkerAt("2,3").equals("x"));
	}
	public void testTerminalTest(){
		Game g = new Game();
		assertTrue(!g.terminalTest(defaultBoard()));
	}
	public void testInvalidMove(){
		Game g = new Game();
		Board b = defaultBoard();
		QueryPlayer p = getPlayer();
		g.makeMove("",b);
	}
	public void testPlayGame(){
		Game g = new Game();
		Vector players = new Vector();
		players.add(new RandomPlayer("AI_X","x"));
		//players.add(new QueryPlayer("Human","x"));
		players.add(new RandomPlayer("AI_O","o"));
		
		g.playGame(players);
		assertTrue(players.size()==2);
	}

	public static Test suite() {
		return new TestSuite(GameTest.class);
	}
	public static void main (String[] args) {
		junit.textui.TestRunner.run(suite());
	}

}