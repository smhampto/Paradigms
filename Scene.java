import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.Dimension;
import java.util.Random;

public class Scene extends JPanel
{
	protected ArrayList<SceneItem> sceneItems;
	private JLabel statusLabel;
	private long randomSeed;
	private Random random;
	
	public Scene(JLabel sl)
	{
		sceneItems = new ArrayList<SceneItem>();
		statusLabel = sl;
		random = new Random();
		randomSeed = 100;
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
		//addSceneItems(30, "Person");
		//addSceneItems(2, "GrowingTree");
		//addSceneItems(5, "Turtle");
		
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


               // if(type.equals("Asteroid")) {
                 //   sceneItems.add(new Asteroid());
                }
				/*if (type.equals("Tree")) {
					sceneItems.add(new Tree(x, y));
				} //  [TODO: as you implement the classes listed below, uncomment out each clause to test
				else if (type.equals("House")) {
					sceneItems.add(new House(x, y));
				} else if (type.equals("Person")) {
					int xs = random.nextInt(21) - 10;
					int ys = random.nextInt(21) - 10;
					sceneItems.add(new Person(x, y, xs, ys));
				} else if (type.equals("GrowingTree")) {
				    sceneItems.add(new GrowingTree(x, y, 1, 2));} 
				  else if (type.equals("Turtle")) {
					sceneItems.add(new Turtle(x, y));
				}*/
			}
		
		repaint();
	}
	
	//  reset() causes a new scene to be created from scratch
	public void reset()
	{
		// [TODO: uncomment out the following few lines once the necessary functionality has been implemented for the Person class
		//Person.setCount(0);
		//Person.setNumHome(0);
		//Person.setNumCrashed(0);
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
}
