import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Dimension;
import java.util.Random;
import java.lang.Runnable;
import java.util.*; 
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
	
	Execute asteroids, bullet; 

	private int shipLocation;


	
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
		addSceneItems(1, "Ship", -1);
		//  [TODO: as you implement the classes listed below, uncomment out each line to test

		addSceneItems(10, "Asteroid", -1);
		repaint(); 

		asteroidsMoving = true; 

		asteroids = new Execute(this);
		threadExecutor.execute(asteroids); 

		bullet = new Execute(this);
		
		
	}
	
	//  addSceneItems() adds an item of a given type to a random location on the
	//  screen.  Until each type of object is implemented, you may want to comment
	//  out the sections which create some of the objects.
	protected void addSceneItems(int num, String type, int prevIndex)
	{
		Dimension dim = getSize();
		dim.setSize(dim.getWidth()-30, dim.getHeight()-30);

		synchronized(sceneItems)
		{
			for (int i=0; i<num; i++) {
				int x = random.nextInt(dim.width);
				int y = random.nextInt(dim.height) - dim.height;
				int s = random.nextInt(50);
				if (s < 20)
					s = s + 20; 
				int xs = random.nextInt(5) + 1;
				int ys = random.nextInt(10) + 1;
				

                if(type.equals("Asteroid")) {

                    sceneItems.add(new Asteroid(x, y, s, s, xs, ys));
                }

				else if(type.equals("Ship")) {
                    sceneItems.add(new Ship((dim.width/2), dim.height-160, 5));
                    for (int z = 0; z < sceneItems.size(); z++) {
                        if(sceneItems.get(z) instanceof Ship) {
                            shipLocation = z;
                        }
                    }   
                }
                if(type.equals("Bullet")) {
                    sceneItems.add(new Bullet(sceneItems.get(shipLocation).getXCoord() + 40 , sceneItems.get(shipLocation).getYCoord() - 25 ));
					threadExecutor.execute(bullet); 
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

	public void updateShip( char direction )
	{
		Dimension dim = getSize();

		synchronized(sceneItems)
		{
			for (SceneItem si : sceneItems) {
				if (si.getClass() == Ship.class)
				((MovingSceneItem)si).moveShip(dim.width, direction);
			}
		}
		repaint();
	}


	public void updateAsteroids()
	{

	    Dimension dim = getSize();
	
		int [] s = new int[sceneItems.size()];  
	   	int location = 0; 
        
		Iterator<SceneItem> itr = sceneItems.iterator();

		while (itr.hasNext())
		{
		   int i = 0; 
		   SceneItem s1 = itr.next(); 
		   
		   for (SceneItem s2 : sceneItems)
		   {
				if (s1.overlaps(s2)  && s1.getClass() == Asteroid.class && s2.getClass() == Bullet.class && !s2.isHidden())
				{
					i = 1;
					s2.hide();
				} 
			}

		  if (i == 1)			
			itr.remove(); 	
					  
		}
		
		
		for (SceneItem s1 : sceneItems)
					if (s1.getClass() == Asteroid.class)
				{
				    s1.update(dim.width, dim.height);	
					 repaint();
				}

	}


	public void updateBullet()
	{
		Dimension dim = getSize();

		synchronized(sceneItems)
		{
			for (SceneItem si : sceneItems) {
				if (si.getClass() == Bullet.class)
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

	public Boolean movingBullet()
	{

		Dimension dim = getSize();
			

	 for (SceneItem si : sceneItems) 
		if (si.getClass() == Bullet.class && si.getXCoord() <= dim.width && si.getYCoord() <= dim.height)
         return true; 
		 return false; 
	}

}


class Execute implements Runnable 
{
	Scene s1;

    public Execute(Scene s)
	{
	   s1 = s; 

	}

	public void run()
	{

			while (s1.movingAsteroids() || s1.movingBullet()) 
			{  
				try{

					if (s1.movingAsteroids()){
					  s1.updateAsteroids(); 
					  Thread.sleep(100); 
					  }

					if (s1.movingBullet())
					  s1.updateBullet(); 
				   }

	 			catch(InterruptedException e)
				{}	
				catch (ConcurrentModificationException e)
				{}

		    }
 	   



		  
	}
		
		
		
		
}
	





