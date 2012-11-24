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
        if (args.length >0)
        {
            sleepDuration = Integer.parseInt(args[0]);
        }
        SceneFrame frame = new SceneFrame(sleepDuration);
        frame.setVisible(true);
        frame.createScene();
    }
}

class SceneFrame extends JFrame
{
    JLabel statusLabel;
    Scene panel;
    
    public SceneFrame(int sleepDuration)
    {
        setTitle("The Game");
        setLayout(new BorderLayout());
        setBackground(Color.brown);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        
        statusLabel = newJLabel("This is the menu");
        panel = new Scene(statusLabel, sleepDuration);
        
        add(panel, BorderLayout.CENTER);
        
        // create the status bar panel and shove it down the bottom of the frame
		JPanel statusPanel = new JPanel();
		statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		add(statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize(new Dimension(getWidth(), 20));
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
		statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
		statusPanel.add(statusLabel);
		
		addKeyListener(new MyKeyListener());
	}
	
	public void createScene()
	{
        KeyEvent ke = new KeyEvent(this, KeyEvent.KEY_TYPED, 0, 0, 0, 'r');
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(ke);
    }
    
    private class MyKeyListener extends KeyAdapter
	{
		@Override public void keyTyped(KeyEvent e)
		{
		  //need menu etc. so could put all of this in a while loop
		  /* such as:
		      while(inGame != true)
		      
		  */
		
			if (e.getKeyChar() == 'f') {
				panel.updateScene();
				panel.repaint();
			} else if (e.getKeyChar() == 'r') {
				panel.reset();
			} else if (e.getKeyChar() == 'n') {
				panel.setRandomSeed(-1);
				panel.reset();
			} else if (e.getKeyChar() == 't') {
				panel.toggleTurtles();
			} else if (e.getKeyChar() == 'p') {
				panel.togglePeople();
			}
		}
	}
}
=
    
    
    
    
    
    