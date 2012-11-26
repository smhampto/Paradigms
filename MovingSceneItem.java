public class MovingSceneItem extends SceneItem
{
	private int xStep;
	private int yStep;
	private String type;
	
	
	public MovingSceneItem(String path, int x, int y, int w, int h, int xs, int ys)
	{
	   super(path, x, y, w, h);

        type = path;
        xStep = xs;
        yStep = ys;
		
	}
	
    @Override
	public void setXStep(int xs) {
		xStep = xs;
	}
	
	@Override
	public void setYStep(int ys) {
	   yStep = ys;
	} 
	
	
	
	@Override
	public void update(int widthPan, int heightPan)
	{
        
        xCoord += xStep;
        yCoord += yStep;
	
	   if(xCoord > (widthPan - 30)){
	       xCoord = 0;
	   } else if(xCoord < 0) {
	       xCoord = widthPan - 30;
	   } else if(yCoord < 0 && type.equals("Asteroid")) {
	       yCoord = heightPan - 30;
	   } else if(yCoord > (heightPan - 30) && type.equals("Bullet")) {
	       yCoord = 0;
	   }    
	   
	   
	}
	
}
