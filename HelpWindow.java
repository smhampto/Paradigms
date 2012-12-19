import java.awt.*;
import javax.swing.*;

public class HelpWindow extends JDialog
{
    JLabel label;
    Image background;
    
    public HelpWindow(JFrame frame)
	{
        super(frame, "Instructions", true);
        setLayout(new FlowLayout());
        
        background = new ImageIcon("Asteroids.png").getImage();
            setContentPane(new JPanel() { protected void paintComponent(Graphics g) { g.drawImage(background,0,0,getWidth(), getHeight(), this);}});

		String Instructions = "The Instructions";
        label = new JLabel(Instructions);
        add(label);
    }

}
