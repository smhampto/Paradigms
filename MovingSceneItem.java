public class MovingSceneItem extends SceneItem
{
	private int xStep;
	private int yStep;
	
	
	public MovingSceneItem(String path, int x, int y, int w, int h, int xs, int ys)
	{
	   super(path, x, y, w, h);

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
	   } else if(yCoord < 0) {
	       yCoord = heightPan - 30;
	   } else if(yCoord > (heightPan - 30)) {
	       yCoord = 0;
	   }    
	   
	   
	}
	
}