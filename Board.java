import java.io.*;
import java.util.*;

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

public class Board implements Serializable, Cloneable{
	Hashtable b;
	int rows, cols;
	Hashtable markers;
	String toMove;
	String[] pnames = new String[2];
	
	public Board(int rows, int cols) {
		super();
		for(int i = 0; i<pnames.length;i++){
			pnames[i] = "player" + i;
		}
		toMove = pnames[0];
		this.rows = rows;
		this.cols =cols;
		b = new Hashtable();
		markers = new Hashtable();
		markers.put("default",".");
		markers.put(pnames[0],"x");
		markers.put(pnames[1],"o");
		for(int i = 0; i<rows;i++){
			for(int j=0;j<cols;j++){
				b.put(makeIndex(i,j),".");
			}
		}
	}
	public static boolean save(Board b, String filename){
		try{	
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
			oos.writeObject(b);
			oos.close();
		}catch(IOException e){
			System.out.println("Could not save board.");
			return false;
		}
		return true;
	}
	public static Board load(String board){
		Board b = null;
		try{
			ObjectInputStream ois = new ObjectInputStream( new FileInputStream(board));
			b = (Board) ois.readObject();
			ois.close();
			System.out.println("To move:"+b.getToMove() + " Marker:" + b.getMarker(b.getToMove()));
			System.out.println("Not to move:" + b.getNotToMove() + " Marker:" + b.getMarker(b.getNotToMove()));
		}catch(Exception e){
			System.out.println("Could not load board. Reason:" + e.getMessage());
		}
		return b;
	}
	public boolean isAdjacentToEnemy(String pos){
		int r=getRow(pos),c=getCol(pos);
		String enemy= getMarker(getNotToMove());
		for(int i=-1;i<=1;i++){
			for(int j=-1;j<=1;j++){
				if(i==0&&j==0) continue;
				if(getMarkerAt(r+i,c+j).equals(enemy))
					return true;
			}
		}
		return false;
	}
	public void makeMove(String pos, Vector flPieces) throws Exception{
		//System.out.println("Move:"+pos);
		String move="";
		if(flPieces == null)
			flPieces = flankingPositions(pos,getMarker(toMove),getMarker(getNotToMove()));
		int slope = 0, c = this.getCol(pos), r = this.getRow(pos);
		for(int i = 0; i < flPieces.size(); i++){
			move = (String) flPieces.get(i);
			flipPieces(pos,move);			
		}
		setMarker(r,c,toMove);
	}
	public void flipPieces(String p1, String p2)throws Exception{
		//System.out.println("Pieces:"+p1+":"+p2);
		int x0 = getCol(p1), y0 = getRow(p1), x1 = getCol(p2), y1 = getRow(p2),
			slope = (x0-x1 != 0)?(y1-y0)/(x1-x0):0, lgX, lgY, smX, smY;
		String larger, smaller;
		if (x0-x1 == 0){ // vertical line
			if(y1 > y0){
				larger = p2; smaller = p1; lgX = x1; lgY = y1; smX = x0; smY = y0;
			}
			else{
				larger = p1; smaller = p2; lgX = x0; lgY=y0; smX = x1; smY = y1;
			}
			for(int i=smY+1;i<lgY;i++){
				flip(makeIndex(i,x1)); // doesn't matter if x1 or x0, since they are the same
			}
		}else{
			if(x0>x1){
				larger = p1; smaller = p2; lgX = x0; lgY=y0; smX = x1; smY = y1;
			}
			else{
				larger = p2; smaller = p1; lgX = x1; lgY = y1; smX = x0; smY = y0;
			}
			
			if(y1-y0==0){ // horizontal line
				for(int i = smX+1; i < lgX; i++){
					flip(makeIndex(i*slope+smY,i));
				}				
			}else{//diagonal
				int b = lgY - slope * lgX;
				for(int i = smX+1; i < lgX; i++){
					flip(makeIndex(i*slope+b,i));
				}
			}
		}
	}
	public void flip(String m) throws Exception{
		//System.out.println("Flip:"+m);
		if(isFree(m)) throw new Exception("Cannot flip free square:"+m);
		else if(!isWithinBounds(m)) throw new Exception("Cannot flip unbounded square.");
		setMarker(getRow(m),getCol(m),
			(getMarkerAt(m)==getMarker(pnames[0]))?pnames[1]:pnames[0]);		  
	}
	/**
	 * Will return a vector containing all pieces that will be used to 
	 * flank enemy pieces given the string move
	 * @param pos    index of the form "row,col"
	 * @param marker marker to check for
	 * @return a vector containing pieces
	 */
	public Vector flankingPositions(String pos, String marker, String enemy){
		Vector v = new Vector();
		int r = getRow(pos), c = getCol(pos);
		
		//System.out.println("Enemy:"+enemy);
		
		//diagonal searching
		for(int i=1;i<rows;i++) if (!isWithinBounds(r+i,c+i) || (i==1 && !areMarkersEqual(enemy,r+i,c+i)) || flankingPositionsHelper(r+i,c+i,marker,v) || !enemy.equals(getMarkerAt(r+i,c+i))) break;
		for(int i=1;i<rows;i++) if (!isWithinBounds(r-i,c-i) || (i==1 && !areMarkersEqual(enemy,r-i,c-i)) || flankingPositionsHelper(r-i,c-i,marker,v) || !enemy.equals(getMarkerAt(r-i,c-i))) break;
		for(int i=1;i<rows;i++) if (!isWithinBounds(r+i,c-i) || (i==1 && !areMarkersEqual(enemy,r+i,c-i)) || flankingPositionsHelper(r+i,c-i,marker,v) || !enemy.equals(getMarkerAt(r+i,c-i))) break;
		for(int i=1;i<rows;i++) if (!isWithinBounds(r-i,c+i) || (i==1 && !areMarkersEqual(enemy,r-i,c+i)) || flankingPositionsHelper(r-i,c+i,marker,v) || !enemy.equals(getMarkerAt(r-i,c+i))) break;

		for(int i=1;i<cols;i++)	if (!isWithinBounds(r,c-i) || (i==1 && !areMarkersEqual(enemy,r,c-i)) || flankingPositionsHelper(r,c-i,marker,v) || !enemy.equals(getMarkerAt(r,c-i))) break; // check pieces to left of r,c
		for(int i=1;i<cols;i++) if (!isWithinBounds(r,c+i) || (i==1 && !areMarkersEqual(enemy,r,c+i)) || flankingPositionsHelper(r,c+i,marker,v) || !enemy.equals(getMarkerAt(r,c+i))) break; // check pieces to left of r,c
		for(int i=1;i<rows;i++) if(!isWithinBounds(r-i,c) || (i==1 && !areMarkersEqual(enemy,r-i,c)) || flankingPositionsHelper(r-i,c,marker,v) || !enemy.equals(getMarkerAt(r-i,c))) break; // beneath move
		for(int i=1;i<rows;i++) if(!isWithinBounds(r+i,c) || (i==1 && !areMarkersEqual(enemy,r+i,c)) || flankingPositionsHelper(r+i,c,marker,v) || !enemy.equals(getMarkerAt(r+i,c))) break; // above move

		//System.out.println("Pos:"+pos+"Flank:"+v);		
		return v;
	}
	public boolean areMarkersEqual(String marker, int r, int c){
		return marker.equals(getMarkerAt(r,c));
	}
	/**
	 * Helper function for flankingPositions function
	 * @param r      takes a row index
	 * @param c      takes a column index
	 * @param marker takes a marker 
	 * @param v      takes a vector--info will be pushed onto this vector
	 * @return true if the marker at position r,c matches the param marker
	 * @see   flankingPositions
	 */
	public boolean flankingPositionsHelper(int r,int c, String marker, Vector v){
		String index = makeIndex(r,c); 
		boolean retval = getMarkerAt(index).equals(marker);
		if(retval)	v.add(index);
		return retval;
	}
	public String getMarkerAt(int r, int c){
		return getMarkerAt(makeIndex(r,c));
	}
	public boolean arePositionsEqual(String pos1, String pos2){
		return getMarkerAt(pos1)==getMarkerAt(pos2);
	}
	/**
	 * This function will take move, which supplies a row and column,
	 * and look in adjacent squares for markers that match the marker
	 * parameter. The specific indicies of the found markers will be
	 * added to the moves vector. This function is used to validate a
	 * move.
	 * 
	 * @param move   a string of the form "row,col", ie "3,4"
	 * @param marker a string containing a marker to check against the adjacet spaces
	 * @param moves  vector that will be modified with the locations of markers adjacent to move
	 * @return 	     will return the number of adjacent pieces found
	 */
	public int countAdjacentMarkers(String move, String marker, Vector moves){
		int row = getRow(move), col=getCol(move), count=0;
		String newIndex = "", myMarker="";
		for(int i=-1;i<=1;i++){ 
			for(int j=-1;j<=1;j++){
				if(i==0 && j==0) // we do not want to inspect the pos of move itself
					continue;
				newIndex = makeIndex(row+i,col+j); // a string "row,col"
				if(isWithinBounds(newIndex)){ // checks if string corresponds to board pos
					myMarker = getMarkerAt(newIndex); // gets marker at that pos
					if(marker.equals(myMarker)){
						count++;
						if(moves!=null)
							moves.add(newIndex);
					}
				}
			}
		}
		return count;
	}
	/**
	 * Will return the marker located in the specified index.
	 * @param move index of the form "row,col"
	 * @return the marker value at the index
	 */
	public String getMarkerAt(String move){
		String k;
		Enumeration e = b.keys();
		while(e.hasMoreElements()){
			k = (String) e.nextElement();
			//System.out.println(k + "=" + makeIndex(r,c));
			if(k.equals(move))
				return (String) b.get(k);
		}
		return "blah";
	}
	/**
	 * Checks to see if position designated by move is available
	 * @param move index of the form "row,col"
	 * @return     true if the position is available
	 */
	public boolean isFree(String move){
		//System.out.println(getMarkerAt(move) + "=" + getMarker("default"));
		return this.isWithinBounds(move) && 
			getMarkerAt(move).equals(getMarker("default"));
	}
	/**
	 * Alters the value of toMove to the next player in line
	 */
	public void nextPlayer(){
		toMove = (toMove.equals(pnames[0])) ? pnames[1] : pnames[0];
	}
	/**
	 * Will construct a hash index from a row col combination
	 * @param row row position
	 * @param col columns position
	 * @return    String of the form "row,col"
	 */
	public String makeIndex(int row, int col){
		return makeIndex(String.valueOf(row),String.valueOf(col));
	}
	public String makeIndex(String row, String col){
		return row + "," + col;
	}
	public int getRow(String move){
		return Integer.parseInt(move.substring(0,move.indexOf(",")));
	}
	public int getCol(String move){ // move = "row,col"
		int comma = move.indexOf(",");
		//System.out.println("Comma:" + comma + " Move:" + move.substring(comma+1,move.length()-comma));
		return Integer.parseInt(move.substring(comma+1,move.length()));
	}
	public boolean isWithinBounds(String move){
		return isWithinBounds(getRow(move),getCol(move));
	}
	public boolean isWithinBounds(int r, int c){
		String k;
		Enumeration e = b.keys();
		while(e.hasMoreElements()){
			k = (String) e.nextElement();
			//System.out.println(k + "=" + makeIndex(r,c));
			if(k.equals(makeIndex(r,c))){
				//System.out.println("IsWithinBounds:"+makeIndex(r,c));
				return true;
			}
				
		}
		return false;
	}
	public void restoreDefault(int r, int c) throws Exception{
		setMarker(r,c,"default");
	}
	public void setMarker(int r, int c, String key) throws Exception{
		//System.out.println("SetMarker");
		String index = makeIndex(r,c);
		//System.out.println("Index:"+index);
		if(!isWithinBounds(index)){
			System.out.println("Index unbounded: "+index);
			throw new Exception("Unbounded.");
		}
		b.put(index,markers.get(key));
	}
	public void display(){
		System.out.println("Board - To Move:" + toMove);
		String boardline;
		System.out.print("  ");
		for(int j=0;j<cols;j++){
			System.out.print(" " + j );
		}
		System.out.println("");
		for(int i=rows-1;i>=0;i--){
			boardline=" ";
			for(int j=0;j<cols;j++){
				boardline += getMarkerAt(makeIndex(i,j)) + " ";
			}
			System.out.println((i) + " " + boardline);
		}
	}
	public Object clone(){
		Board b = new Board(this.rows, this.cols);
		b.setB((Hashtable) this.getB().clone()); // copy hash
		b.setRows(this.rows); // rows
		b.setCols(this.cols); // cols
		b.setMarkers(this.markers); // markers
		b.setToMove(this.toMove);
		return b;
	}
	/**
	 * @return
	 */
	public Hashtable getB() {
		return b;
	}

	/**
	 * @return
	 */
	public int getCols() {
		return cols;
	}

	/**
	 * @return
	 */
	public Hashtable getMarkers() {
		return markers;
	}

	/**
	 * @return
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * @return
	 */
	public String getToMove() {
		return toMove;
	}

	/**
	 * @param hashtable
	 */
	public void setB(Hashtable hashtable) {
		b = hashtable;
	}

	/**
	 * @param i
	 */
	public void setCols(int i) {
		cols = i;
	}

	/**
	 * @param hashtable
	 */
	public void setMarkers(Hashtable hashtable) {
		markers = hashtable;
	}

	/**
	 * @param i
	 */
	public void setRows(int i) {
		rows = i;
	}

	/**
	 * @param string
	 */
	public void setToMove(String string) {
		toMove = string;
	}
	public boolean isFull(){
		for(int r=0;r<rows;r++)
			for(int c=0;c<cols;c++)
				if(getMarkerAt(r,c).equals(getMarker("default")))
					return false;
		return true;
	}

	public String getMarker(String index){
		String k;
		Enumeration e = markers.keys();
		while(e.hasMoreElements()){
			k = (String) e.nextElement();
			//System.out.println(k + "=" + index);
			if(k.equals(index)){
				//System.out.println("marker match");
				return (String) markers.get(k);
			}
				
		}
		System.out.println("Enumeration: "+e);
		return "blah";
	}
	public String getNotToMove(){
		return (toMove != pnames[0])?pnames[0]:pnames[1];
	}
	public String getPlayerName(int i){
		return pnames[i];
	}
}