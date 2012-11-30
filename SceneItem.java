import javax.imageio.ImageIO;
import java.io.File;
import java.awt.Image;
import java.awt.Graphics;

//  SceneItem is the base class for all objects which will be added to a scene.
public class SceneItem
{
	private Image img;
	protected int xCoord;
	protected int yCoord;
	protected int width;
	protected int height;
	protected String path;

	public SceneItem(String p, int x, int y, int w, int h) {
		xCoord = x;
		yCoord = y;
		path = p;
		setImage(p, w, h);
	}
	
	public void setImage(String p, int w, int h)
	{
		width = w;
		height = h;
		try {
			img = ImageIO.read(new File(p));
		}
		catch (java.io.IOException e) {
			System.out.println("Can't load image file '" + p + "'");
		}
	}

	//  update() is an empty function which will need to be overridden by the
	//  MovingSceneItem class, but for basic items nothing needs to be done
	public void update(int width, int height) {
	

	}
	
	//  Keyword "final" prevents this method from being overridden
	public final void draw(Graphics g)
	{
		if (getImage() != null) {
			g.drawImage(getImage(), getXCoord(), getYCoord(), getWidth(), getHeight(), null);
		}
	}
	
	//  overlaps() takes another sceneItem as a parameter and return true
	//  if the current object overlaps with it
	public boolean overlaps(SceneItem si)
	{
		if (isHidden() || si.isHidden()) {
			return false;
		}
		int iStartX = getXCoord();
		int iEndX = iStartX + getWidth();
		int iStartY = getYCoord();
		int iEndY = iStartY + getHeight();
		
		int jStartX = si.getXCoord();
		int jEndX = jStartX + si.getWidth();
		int jStartY = si.getYCoord();
		int jEndY = jStartY + si.getHeight();
		
		if (((iStartX <= jStartX && jStartX <= iEndX) || (iStartX <= jEndX && jEndX <= iEndX)) &&
			((iStartY <= jStartY && jStartY <= iEndY) || (iStartY <= jEndY && jEndY <= iEndY)))
		{
			return true;
		}
		return false;
	}



	public Image getImage() {
		return img;
	}

	public void hide()
	{
		img = null;
	}
	
	public boolean isHidden()
	{
		return (img == null);
	}


    //overide in movingsceneitem
    public void setXStep(int xs) {

	}
	//override in movingscenitem
	public void setYStep(int ys) {

	} 
	
	//overide in movingsceneitem
    public int getXStep() {
        return 0;
	}
	//override in movingscenitem
	public int getYStep() {
        return 0;
	} 

	public int getXCoord() {
		return xCoord;
	}
	
	public int getYCoord() {
		return yCoord;
	}
	
	public void setXCoord(int x) {
		xCoord = x;
	}
	
	public void setYCoord(int y) {
		yCoord = y;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String p) {
		path = p;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setWidth(int w) {
		width = w;
	}
	
	public void setHeight(int h) {
		height = h;
	}	
}
