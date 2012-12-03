import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Container;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.border.BevelBorder;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.awt.Color;


public class Game
{
    JLabel statusLabel;
    
    public static void main(String args[])
    {
        int sleepDuration = 100;
        if(args.length >0)
        {
            sleepDuration = Integer.parseInt(args[0]);
        }
        SceneFrame frame = new SceneFrame(sleepDuration);
        frame.setVisible(true);
        frame.createScene();
    
    }
}

class SceneFrame  extends JFrame {
    
    JLabel statusLabel;
    Scene panel;
    JMenuBar menubar;
    JMenu help;
    JMenu newGame;
    JMenuItem startNewGame;
    JMenuItem loadGame;
    JMenuItem saveGame;
    JMenuItem instructions;
    private boolean firstPlay = true;
    private boolean atMenu = true;
    
    public SceneFrame(int sleepDuration)
    {

    statusLabel = new JLabel("Asteroids:     Lives:      ");
        panel = new Scene(statusLabel);

        setTitle("The Game");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

        
        menubar = new JMenuBar();
        add(menubar);
        newGame = new JMenu("Game");
        menubar.add(newGame);
        startNewGame = new JMenuItem("New Game");
        newGame.add(startNewGame);
        loadGame = new JMenuItem("Load Game");
        newGame.add(loadGame);
        saveGame = new JMenuItem("Save Game");
        newGame.add(saveGame);
        
        help = new JMenu("Help");
        menubar.add(help);
        instructions = new JMenuItem("Instructions");
        help.add(instructions);
        setJMenuBar(menubar);
                
        event e = new event();
        instructions.addActionListener(e);
        
        newGames n = new newGames();
        startNewGame.addActionListener(n);
        
        loadGames l = new loadGames();
        loadGame.addActionListener(l);
        
        saveGames s = new saveGames();
        saveGame.addActionListener(s);

        
        panel.setBackground(Color.black);
        add(panel, BorderLayout.CENTER);
        
        JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(getWidth(), 20));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);
		
		if(atMenu){
            
            HelpWindow helpy = new HelpWindow(SceneFrame.this);
                helpy.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);   
                helpy.setSize(1000, 1000);
                helpy.setLocation(0, 0);
                helpy.setVisible(true);
                
        }

		addKeyListener(new MyKeyListener());
    
    }
    
    public void createScene()
	{
        KeyEvent ke = new KeyEvent(this, KeyEvent.KEY_TYPED, 0, 0, 0, 'r');
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(ke);
    }
    
    public class event implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        
        System.out.println("instruction");
            
            HelpWindow helpy = new HelpWindow(SceneFrame.this);
            helpy.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);   
            helpy.setSize(300, 100);
            helpy.setLocation(300, 300);
            helpy.setVisible(true);

        
        }
        
    }


    public class newGames implements ActionListener {
        public void actionPerformed(ActionEvent n) {

        atMenu = false;	
		while(firstPlay) {
            panel.setRandomSeed(-1);
			panel.numAsteroids = 10;
			panel.reset();
			firstPlay = false;
		}
        
        }
        
    }

    public class loadGames implements ActionListener {
        public void actionPerformed(ActionEvent l) {
            atMenu = false;
            panel.loadGame();
            panel.addLoadScene();
        }   
    }
    
    public class saveGames implements ActionListener {
        public void actionPerformed(ActionEvent s) {
            
            panel.saveGame();
            //panel.addSaveScene();        
        }   
    }

    
   private class MyKeyListener extends KeyAdapter
	{
	
		@Override public void keyTyped(KeyEvent e)
		{

            if (e.getKeyChar() == 'i') {
				HelpWindow helpy = new HelpWindow(SceneFrame.this);
                helpy.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);   
                helpy.setSize(300, 100);
                helpy.setLocation(300, 300);
                helpy.setVisible(true);
			} 
			else if (e.getKeyChar() == 'l') {
				panel.updateShip('l');
				panel.repaint();
			}
			else if(e.getKeyChar() == 'p') {
			     panel.pauseGame();
			}
			else if(e.getKeyChar() == 'r') {
				panel.resumeGame();
			}
			else if(e.getKeyChar() == 'i') {
			 HelpWindow helpy = new HelpWindow(SceneFrame.this);
                helpy.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);   
                helpy.setSize(300, 100);
                helpy.setLocation(300, 300);
                helpy.setVisible(true);
			
			} 
			else if (e.getKeyChar() == 'j') {
				panel.updateShip('j');
				panel.repaint();
			} 
			else if(e.getKeyChar() == ' ' && panel.numAsteroids != 0) {
			     panel.addSceneItems(1, "Bullet", -1);

		} /*else if (e.getKeyChar() == 'n' && firstPlay) {
				panel.setRandomSeed(-1);
				panel.reset();
				firstPlay = false; 
			}*/

			else if (e.getKeyChar() == 'n' && panel.numAsteroids == 0){
				panel.deleteSceneItems(); 
				panel.numAsteroids = 10 + 5 *panel.level;
				panel.level = panel.level + 1;
				panel.reset();
			}
        
        }
        
    }
    
    public static void main(String args[]) {
        
        MainWindow helpy = new MainWindow();
        helpy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        helpy.setSize(300, 100);
        helpy.setVisible(true);
        helpy.setTitle("Main Window");
    
    }


}
