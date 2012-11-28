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
  
	}

	public void moveShip(int width, char direction)
	{

		if (direction == 'l' && (xCoord + 100 + xStep) < width)
				xCoord += xStep;
		if (direction == 'j'&& (xCoord - xStep) > 0)
				xCoord -= xStep;

	}

	
}
