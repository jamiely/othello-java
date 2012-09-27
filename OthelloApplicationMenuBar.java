import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
public class OthelloApplicationMenuBar extends JMenuBar implements ActionListener{
	JMenu file, help;
	JMenuItem itemNew, itemSave, itemLoad, itemAbout;
	//JFileChooser chooser;
	Game game;
	Thread gameInProgress;
	/**
	 * 
	 */

	public OthelloApplicationMenuBar(Game g) {
		super();
		// setting up menu bar
		//chooser = new JFileChooser();
		file = new JMenu("File");
		itemNew = new JMenuItem("New Game");
		itemNew.addActionListener(this);
		itemSave = new JMenuItem("Save");
		itemSave.setActionCommand("Save");
		itemSave.addActionListener(this);
		itemLoad = new JMenuItem("Open");
		itemLoad.setActionCommand("Open");
		itemLoad.addActionListener(this);
		file.add(itemNew);
		/*
		file.add(itemSave);
		file.add(itemLoad);*/
		add(file);
		
		add(Box.createHorizontalGlue());
		
		help = new JMenu("Help");
		itemAbout = new JMenuItem("About");
		help.add(itemAbout);
		add(help);
		game = g; 
	}

	public void actionPerformed(ActionEvent e){
		String cmd = e.getActionCommand();
		Object src = e.getSource();
		/*
		if(cmd.equals("Open")){
			 int returnVal = chooser.showOpenDialog(this);
			 if(returnVal == JFileChooser.APPROVE_OPTION) {
				if(gameInProgress!=null) gameInProgress.stop();
				System.out.println("You chose to open this file: " +
					 chooser.getSelectedFile().getName());
				//game.quit();
				game.setCurrentState(Board.load(chooser.getSelectedFile().getPath()));
				gameInProgress = new Thread(game);
				gameInProgress.start();
			 }
		}
		else */if(src == itemNew){
			if(gameInProgress!=null){
				//gameInProgress.destroy();
				gameInProgress.stop();
				System.out.println("stopping"); 
			}
				
			game.setCurrentState(game.getInitial());
			gameInProgress = new Thread(game);
			gameInProgress.start();
		}/*
		else if(cmd.equals("Save")){
			 int returnVal = chooser.showSaveDialog(this);
			 if(returnVal == JFileChooser.APPROVE_OPTION) {
				System.out.println("You chose to save this file: " +
					 chooser.getSelectedFile().getName());
				System.out.println(chooser.getSelectedFile().getPath());
				Board.save(game.getCurrentState(),chooser.getSelectedFile().getPath());
			 }
		}*/
	}

	public static void main(String[] args) {
		
	}
}
