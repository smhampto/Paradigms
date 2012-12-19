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
	public void setXStep(int xs) 
	{
		xStep = xs;
	}
	
	@Override
	public void setYStep(int ys) 
	{
	   yStep = ys;
	} 
	
	@Override
	public int getXStep() 
	{
	   return xStep;
	}
	
	@Override
	public int getYStep() 
	{
	   return yStep;
	}
	
	
	@Override
	public void update(int widthPan, int heightPan)
	{
        
        xCoord += xStep;
        yCoord += yStep;
	
	   
	   if(xCoord > (widthPan - getWidth())){
	       xCoord = 0;
	   } else if(xCoord < 0) {
	       xCoord = widthPan - getWidth();
	   } else if(yCoord < 0 && type.equals("Asteroid")) {
	       yCoord = heightPan - getHeight();
	   } else if(yCoord > (heightPan - getHeight()) && type.equals("Bullet")) {
	       yCoord = 0;
	   }    

	}

	public void moveShip(int width, char direction)
	{
		if (direction == 'l' && (xCoord + getWidth() + xStep) < width)
            xCoord += xStep;
        else if(direction == 'l' && (xCoord + getWidth() + xStep) >= width)
            xCoord = 0; 
		if (direction == 'j'&& (xCoord - xStep) >= 0)
            xCoord -= xStep;
        else if(direction == 'j' && (xCoord - xStep) < 0)
            xCoord = width - getWidth();

	}
}
