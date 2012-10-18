import junit.framework.*;
import java.util.*;
public class BoardTest extends TestCase{
	static int row=8, col=8;
	public static Board defaultBoard(){
		Board b = new Board(row,col);
		init(b);
		return b;
	}
	public static void init(Board b){
		try{
			b.setMarker(3,3,b.getPlayerName(1));
			b.setMarker(3,4,b.getPlayerName(0));
			b.setMarker(4,3,b.getPlayerName(0));
			b.setMarker(4,4,b.getPlayerName(1));
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
	public static Test suite() {
		return new TestSuite(BoardTest.class);
	}
	public void testInitial(){
		Board b = new Board(5,6);
		assertEquals(b.getRows(),5);
		assertEquals(b.getCols(),6);
		assertTrue(b.getPlayerName(0).equals("player0"));
		assertTrue(b.getPlayerName(1).equals("player1"));
	}
	public void testSaveLoad(){
		Board b = new Board(7,8);
		Board.save(b,"temp.txt");
		Board a = Board.load("temp.txt");
		assertTrue(a.getB().equals(b.getB()));
	}
	public void testBadSaveLoad(){
		Board b = defaultBoard();
		assertTrue(!Board.save(b,""));
		assertTrue(Board.load("") == null);
	}
	public void testArePositionsEqual(){
		Board b = defaultBoard();
		assertTrue(b.arePositionsEqual("3,3","4,4"));
	}
	public void testCountAdjacentMarkers(){
		Board b = defaultBoard();
		Vector v = new Vector();
		assertTrue(b.countAdjacentMarkers("3,4","x",v)==0);
		assertTrue(b.countAdjacentMarkers("3,4","x",null)==1);
	}
	public void testIsAdjacentEnemy(){
		Board b = defaultBoard();
		assertTrue(b.isAdjacentToEnemy("2,3"));
		assertTrue(!b.isAdjacentToEnemy("7,7"));
	}
	public void testNextPlayer(){
		Board b = defaultBoard();
		b.nextPlayer();
		assertTrue(b.getToMove().equals(b.getPlayerName(1)));
	}
	public void testDisplay(){
		defaultBoard().display();
	}
	public void testClone(){
		Board b = defaultBoard();
		Board a = (Board) b.clone();
		assertTrue(a.getB().equals(b.getB()));
		assertTrue(a.getRows() == b.getRows());
		assertTrue(a.getCols() == b.getCols());
	}
	public void testMakeMove(){
		Board b = defaultBoard();
		try{
			b.makeMove("2,3",null);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		b = defaultBoard();
		try{
			b.makeMove("7,7",null);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	public void testGetMarkers(){
		Board b = defaultBoard();
		assertTrue(b.getMarkers().size()==3);
	}
	public void testIsFull(){
		Board b= defaultBoard();
		assertTrue(!b.isFull());
	}
	public static void main (String[] args) {
		junit.textui.TestRunner.run(suite());
	}

}