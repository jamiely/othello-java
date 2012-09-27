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
 
public abstract class Player {
	String marker ="", name="";
	int pieceCount = 0;
	public Player(String name, String marker) {
		super();
		this.marker= marker;
		this.name= name;
	}
	public String getMove(Game g, Board b){
		return "";
	}
	public String toString(){
		return name + " ("+marker+")";
	}
	public void clearPieceCount(){
		pieceCount=0;
	}
	public void incPiece(){
		pieceCount++;
	}
	/**
	 * @return
	 */
	public String getMarker() {
		return marker;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public int getPieceCount() {
		return pieceCount;
	}

	/**
	 * @param string
	 */
	public void setMarker(String string) {
		marker = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param i
	 */
	public void setPieceCount(int i) {
		pieceCount = i;
	}

}