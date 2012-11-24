import javax.swing.JLabel;
import java.awt.Dimension;

public class CrashScene extends Scene
{
    //private boolean doesOverlap = false;

	public CrashScene(JLabel lab)
	{
	   super(lab);
	}
	
	@Override
	public void updateScene()
	{
	   Dimension dim = getSize();

		synchronized(sceneItems)
		{
			for (int x = 0; x < sceneItems.size(); x++) {
			  for(int y=0; y < sceneItems.size(); y++) {
			    if(sceneItems.get(x).overlaps(sceneItems.get(y)) && x!=y && !sceneItems.get(x).isHidden() && !sceneItems.get(y).isHidden()) {
			       if((sceneItems.get(x) instanceof Asteroid) && (sceneItems.get(y) instanceof Bullet)) {
			         //Person.numHome++;
			         System.out.println("si should disappear");
			         sceneItems.get(y).hide();
			         // should update to create new asteroids
			         //sceneItems.get(y).setImage("images/smile.jpg", sceneItems.get(y).getWidth(), sceneItems.get(y).getHeight());     
			       } 
			       else if((sceneItems.get(y) instanceof Asteroid) && (sceneItems.get(x) instanceof Bullet)) {
			         //Person.numHome++;
			         System.out.println("si2 should disappear");
			         sceneItems.get(x).hide();
			         // should update to create new asteroids
			         //sceneItems.get(x).setImage("images/smile.jpg", sceneItems.get(x).getWidth(), sceneItems.get(x).getHeight());
			       }
			       
			       else if((sceneItems.get(x) instanceof Ship) && (sceneItems.get(y) instanceof SceneItem)) {
			         //if(sceneItems.get(x) instanceof MovingSceneItem) {
			             sceneItems.get(x).hide();
			             sceneItems.get(y).setImage("images/fire.jpg", sceneItems.get(y).getWidth(), sceneItems.get(y).getHeight());
/* 		             if(sceneItems.get(x) instanceof Person) {
		                 Person.numCrashed++;
			             }
			             
			             if(sceneItems.get(y) instanceof Person) {
			                 Person.numCrashed++;
			             }
			             
			             if((sceneItems.get(y) instanceof MovingSceneItem)) {
			                 sceneItems.get(y).setXStep(0);//sceneItems.get(y).setYStep(0);
			                 sceneItems.get(y).setYStep(0);
			             }
*/			             
			             
			         /*}
			         
			         else if(sceneItems.get(x) instanceof SceneItem) {
			             sceneItems.get(y).hide();
			             sceneItems.get(x).setImage("images/fire.jpg", sceneItems.get(x).getWidth(), sceneItems.get(y).getHeight());
			         }*/
			       
			       }
			       
			    }
			    
				
              }
              
              sceneItems.get(x).update(dim.width, dim.height);
            }
//            System.out.println("num persons home: " + Person.numHome);
//            System.out.println("num persons crashed: "+ Person.numCrashed);
		}

	}
}
