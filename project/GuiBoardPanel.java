import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.net.*;

/*
 * Jamie Ly

 * 
 * Created on May 31, 2004
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
public class GuiBoardPanel extends JButton {
	int row = 0;
	int col = 0;
	static boolean imagesInitialized = false;
	static ImageIcon[] images = new ImageIcon[4];
	public static final int NONE = 0, BLACK = 1, WHITE = 2, LEGAL=3;
	public int state = NONE;
	
	public GuiBoardPanel(JApplet parent) {
		super();
		if(!imagesInitialized){
			try{
			
			images[NONE] = new ImageIcon(
				//Toolkit.getDefaultToolkit().getImage("./images/empty.gif")
				parent.getImage(new URL(parent.getDocumentBase(), "./images/empty.gif"))
				);
			images[BLACK] = new ImageIcon(
				//Toolkit.getDefaultToolkit().getImage("./images/black.gif"));
				parent.getImage(new URL(parent.getDocumentBase(), "./images/black.gif")));
			images[WHITE] = new ImageIcon(
				//Toolkit.getDefaultToolkit().getImage("./images/white.gif"));
				parent.getImage(new URL(parent.getDocumentBase(), "./images/white.gif")));
			images[LEGAL] = new ImageIcon(
				//Toolkit.getDefaultToolkit().getImage("./images/legal.gif"));
				parent.getImage(new URL(parent.getDocumentBase(), "./images/legal.gif")));
			}catch(Exception e){
								
			}
		}
		setIcon(images[NONE]);
		//icon = new JButton(images[NONE]);
		//icon = new JLabel(images[NONE]);
		setBackground(Color.GREEN);
		//setSize(new Dimension(40,40));
		//this.setPreferredSize(new Dimension(40,40));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		//this.add(Box.createRigidArea(new Dimension(40,40)));
		
	}
	public void setState(int i){
		state = i;
		setIcon(images[state]);
	}
	public static void main(String[] args) {
		/*
		JFrame main = new JFrame("GUIBoardPanel Test");
		JPanel content = (JPanel) main.getContentPane();
		content.setLayout(new GridLayout(0,4));
		GuiBoardPanel p;
		double choice;
		for(int i=0;i<20;i++){
			p = new GuiBoardPanel();
			content.add(p);
			choice = Math.random();
			if(choice > .8){
				p.setState(GuiBoardPanel.BLACK);
			}else if(choice >.5){
				p.setState(GuiBoardPanel.WHITE);
			}
		}
		main.setSize(new Dimension(100,200));
		main.pack();
		main.show();
		*/
	}
	public void setCoordinates(int r, int c){
		row = r;
		col = c;
	}
	public int getRow(){
		return row;
	}
	public int getCol(){
		return col;
	}
}
