import junit.framework.*;

public class QueryPlayerTest extends TestCase{
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
	public void testGetMove(){
		Game g = new Game();
		QueryPlayer p = getPlayer();
		assertTrue(p.getMove(g,defaultBoard()).equals("2,3"));
		System.out.println("HELLO!");
	}
	public static Test suite() {
		return new TestSuite(QueryPlayerTest.class);
	}
	public static void main (String[] args) {
		junit.textui.TestRunner.run(suite());
	}
	public void testPieceCount(){
		Player p = getPlayer();
		p.clearPieceCount();
		assertTrue(p.getPieceCount()==0);
		p.setPieceCount(5);
		assertTrue(p.getPieceCount()==5);
		p.incPiece();
		assertTrue(p.getPieceCount()==6);
		assertTrue(p.toString().equals("Thomas (x)"));
		p.setName("Randal");
		assertTrue(p.getName().equals("Randal"));
		p.setMarker("o");
		assertTrue(p.getMarker().equals("o"));
	}
}