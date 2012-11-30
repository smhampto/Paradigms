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
//new
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.IllegalStateException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.*;
import java.io.*;
 
// end new
public class Scene extends JPanel
{
	protected ArrayList<SceneItem> sceneItems;
	private JLabel statusLabel;
	private long randomSeed;
	private Random random;
    private Boolean asteroidsMoving;
	private Boolean bulletsMoving = true; 
    
    //new
    private Scanner input;
    private PrintWriter out;
    private int numParams;
    private String tempPath;
    private boolean tempHidden;
    private int tempX;
    private int tempY;
    private int tempW;
    private int tempH;
    private int tempXS;
    private int tempYS;
    private String temp;
    private String spaceTemp;


	private int shipLocation;
public int level = 1;
public int numAsteroids = 10;
    
    //end new

	ExecutorService threadExecutor = Executors.newCachedThreadPool(); 
	Execute asteroids, asteroid, Asteroid;
	
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
	
	public void pauseGame() {
	   asteroidsMoving = false;
	   bulletsMoving = false; 
	}

	public void resumeGame() {
		asteroidsMoving = true;
		bulletsMoving = true;
		
		Asteroid = new Execute(this, 'a');
		threadExecutor.execute(Asteroid);
	}

    //new to load and save
    public void loadGame() {
    
        openLoadFile();
        readFile();
        closeLoadFile();


		asteroidsMoving = true;

		asteroid = new Execute(this, 'a');
		threadExecutor.execute(asteroid); 

    
    }
    
    public void saveGame() {
        openSaveFile();
        writeFile();
        closeSaveFile();
    }
    
    public void openLoadFile() {
        try {
            input = new Scanner( new File("savedGame.txt"));
        }
        catch (FileNotFoundException fileNotFoundException)
        {
            System.err.println("Error opening file.");
            System.exit(1);
        }
    }
    
    public void openSaveFile() {
        try {
            out = new PrintWriter(new FileWriter("savedGame.txt"));
        }
        catch (FileNotFoundException fileNotFoundException)
        {
            System.err.println("Error opening file.");
            System.exit(1);
        }
        catch (IOException ioexception)
        {}
    }
    
    public void writeFile() {
        try{
            PrintWriter writer = new PrintWriter("savedGame.txt");
            writer.print("");
            writer.close();
        }
        catch (FileNotFoundException fileNotFoundException)
        {
        }
        
		out.println(numAsteroids);
        for (SceneItem si : sceneItems) {
            out.print(si.isHidden() + "\n" + si.getPath() + "\n" + si.getXCoord() + "\n" + si.getYCoord() + "\n" + si.getWidth() + "\n" + si.getHeight() + "\n" + si.getXStep() + "\n" + si.getYStep() + "\n\n");
        }
    }
    
    public void readFile() {
        
        int x=0;
        
		numAsteroids = input.nextInt();
        while(input.hasNext())
        {   
               
            //while(input.has){
                tempHidden = input.nextBoolean();
                tempPath = input.next();
                tempX = input.nextInt();
                tempY = input.nextInt();
                tempW = input.nextInt();
                tempH = input.nextInt();
                
                spaceTemp = input.next();
                if(spaceTemp != "\n")
                {
                    tempXS = Integer.parseInt(spaceTemp);
                    tempYS = input.nextInt();
          
                    if(tempPath.equals("ship1.jpeg") || tempPath.equals("ship2.jpeg") || tempPath.equals("ship3.jpg"))
                    {
                        sceneItems.add(new Ship(tempPath, tempX, tempY, tempW, tempH, tempXS, tempYS));
                    }
                    else if(tempPath.equals("asteroid1.jpeg") || tempPath.equals("asteroid2.jpeg") || tempPath.equals("asteroid3.jpeg"))
                    {
                        sceneItems.add(new Asteroid(tempPath, tempX, tempY, tempW, tempH, tempXS, tempYS));
                    }
                    else if(tempPath.equals("bullet1.jpg"))
                    {
                        sceneItems.add(new Bullet(tempPath, tempX, tempY, tempW, tempH, tempXS, tempYS));
                    }

                }
                else{
                }
                if(tempHidden)
                {
                    sceneItems.get(x).hide();
                }
                
                x++;
            
           // }
        }
    }
    
    public void closeLoadFile() {
        if( input !=null)
            input.close();
    }
    
    public void closeSaveFile() {
        out.close();
    }

    
    protected void addLoadScene()
    {
        repaint(); 




    
    }
    

    

	//  addScene() determines the number of each type of item to add to the scene.
	//  Until a particular object type is implemented, you may want to comment its line out.
	private void addScene()
	{
		addSceneItems(1, "Ship", -1);
		//  [TODO: as you implement the classes listed below, uncomment out each line to test

		addSceneItems(numAsteroids, "Asteroid", -1);
		repaint(); 

			asteroidsMoving = true;

		asteroids = new Execute(this, 'a');
		threadExecutor.execute(asteroids); 
		
		
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
				if(random.nextBoolean())
				    xs *= -1;
				int ys = random.nextInt(10) + 1;
				

                if(type.equals("Asteroid")) {

                    sceneItems.add(new Asteroid("asteroid1.jpeg", x, y, s, s, xs, ys));
                }

				else if(type.equals("Ship")) {
                    sceneItems.add(new Ship("ship3.jpg", (dim.width/2), dim.height-160, 100, 100, 20, 0));
                    for (int z = 0; z < sceneItems.size(); z++) {
                        if(sceneItems.get(z) instanceof Ship) {
                            shipLocation = z;
                        }
                    }   
                }
                if(type.equals("Bullet")) {
                    sceneItems.add(new Bullet("bullet1.jpg", sceneItems.get(shipLocation).getXCoord() + 40 , sceneItems.get(shipLocation).getYCoord() - 25, 15, 15, 0, -10 ));

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

		synchronized(sceneItems)
		{
			for (SceneItem s1 : sceneItems) 
			{
				int i = 0;
				

		for (SceneItem s2 : sceneItems)
			{
				if ( s1.overlaps(s2) && s1.getClass() == Asteroid.class && s2.getClass() == Bullet.class && !s2.isHidden() && !s1.isHidden())
				{
					i = 1;
					s2.hide();
				}

			}

		if (s1.getClass() == Asteroid.class && s1.getYCoord() > dim.height && !s1.isHidden())
			i = 1;

		if (i == 1){	
			s1.hide();
			numAsteroids--;
		}		
}

	}


for (SceneItem s1 : sceneItems)
if (s1.getClass() == Asteroid.class)
{
s1.update(dim.width, dim.height);	

}

repaint();

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
		if (si.getClass() == Bullet.class && si.getXCoord() <= dim.width && si.getYCoord() <= dim.height && bulletsMoving)
         return true; 
		 return false; 
	}



}


class Execute implements Runnable
{
Scene s1;
char threadType;

public Execute(Scene s, char thread)
{
s1 = s;
threadType = thread;
}

synchronized public void run()
{

while (s1.movingAsteroids() || s1.movingBullet())
{
try{

synchronized(s1){

if (s1.movingAsteroids() && threadType == 'a'){
s1.updateAsteroids();
Thread.sleep(100);
}

if (s1.movingBullet())	{
s1.updateBullet();
Thread.sleep(5);
}	
}

}
catch(InterruptedException e)
{
//System.out.println("interrupted exception");
}	
catch (ConcurrentModificationException e)
{
//System.out.println("concurrentModException");
}

}
 




}




}


