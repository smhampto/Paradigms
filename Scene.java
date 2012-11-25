import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Dimension;
import java.util.Random;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService; 

public class Scene extends JPanel
{
	protected ArrayList<SceneItem> sceneItems;
	private JLabel statusLabel;
	private long randomSeed;
	private Random random;
    private Boolean asteroidsMoving;

	ExecutorService threadExecutor = Executors.newCachedThreadPool(); 
	
	Execute asteroids; 
	
	public Scene(JLabel sl)
	{
		this.sceneItems = new ArrayList<SceneItem>();
		this.statusLabel = sl;
		this.random = new Random();
		this.randomSeed = 100;
	}
	
	public void setRandomSeed(long rs)
	{
		randomSeed = rs;
	}

	//  addScene() determines the number of each type of item to add to the scene.
	//  Until a particular object type is implemented, you may want to comment its line out.
	private void addScene()
	{
		addSceneItems(1, "Ship");
		//  [TODO: as you implement the classes listed below, uncomment out each line to test
		addSceneItems(10, "Asteroid");
		repaint(); 

		asteroidsMoving = true; 
		
		asteroids = new Execute("Asteroid", asteroidsMoving, statusLabel, this);
		threadExecutor.execute(asteroids); 


	}
	
	//  addSceneItems() adds an item of a given type to a random location on the
	//  screen.  Until each type of object is implemented, you may want to comment
	//  out the sections which create some of the objects.
	private void addSceneItems(int num, String type)
	{
		Dimension dim = getSize();
		dim.setSize(dim.getWidth()-30, dim.getHeight()-30);

		synchronized(sceneItems)
		{
			for (int i=0; i<num; i++) {
				int x = random.nextInt(dim.width);
				int y = random.nextInt(dim.height);
				int s = random.nextInt(50);
				int xs = random.nextInt(5);
				int ys = random.nextInt(10);
				

                if(type.equals("Asteroid")) {
                    sceneItems.add(new Asteroid(x, y, s, s, xs, ys));
                }

				else if(type.equals("Ship")) {
                    sceneItems.add(new Ship(x, y, xs));
                }
			}
		}
	}
	
	//  reset() causes a new scene to be created from scratch
	public void reset()
	{
		// [TODO: uncomment out the following few lines once the necessary functionality has been implemented for the Person class
		sceneItems.clear();
		
		if (randomSeed < 0) {
			randomSeed = System.currentTimeMillis();
		}
		random.setSeed(randomSeed);

		addScene();
		updateStatusBar();
	}
	
	//  updateScene() is the function which causes every scene item to get updated
	//  at each time step.  It just calls the update() function on each item.
	public void updateScene()
	{
		Dimension dim = getSize();

		synchronized(sceneItems)
		{
			for (SceneItem si : sceneItems) {
				si.update(dim.width, dim.height);
			}
		}
		repaint();
	}


	public void updateAsteroids()
	{
		Dimension dim = getSize();

		synchronized(sceneItems)
		{
			for (SceneItem si : sceneItems) {
				if (si.getClass() == Asteroid.class)
				si.update(dim.width, dim.height);
			}
		}
		repaint();
	}

	//  paingComponent() is the function used by the windowing system to draw the contents
	//  of the window and is called by the system itself when some portion of the window
	//  needs to be drawn or re-drawn
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		synchronized(sceneItems)
		{
			for (SceneItem si : sceneItems) {
				si.draw(g);
			}
		}
	}
	
	//  updateStatusText() updates the text in the status bar at the bottom of the frame 
	//  [TODO: in CrashScene this should be overridden to give stats about the people in the scene.]
	protected String getStatusBarText()
	{
		return "";
	}
		
	public void updateStatusBar()
	{
		statusLabel.setText(getStatusBarText());
	}

	public Boolean movingAsteroids()
	{
		return asteroidsMoving; 
	}

}


class Execute implements Runnable 
{
	private String taskName;
	private int sleepyTime; 
	private boolean movement = false; 
	private JLabel statusLabel;

	
	Scene s1;

  
    public Execute(String str, boolean b, JLabel sl, Scene s)
	{
	   taskName =  str;
	   movement = b; 
	   statusLabel = sl; 
	   
	   s1 = s; 
	
	}

	public void run()
	{

			while (s1.movingAsteroids()) 
			{  
				try{
					s1.updateAsteroids(); 
					Thread.sleep(100); 
				   }
	 			catch(InterruptedException e)
				{}	

		    }
 	  
		  
	}
		
		
		
		
}
	





