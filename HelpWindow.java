import java.awt.*;
import javax.swing.*;

public class HelpWindow extends JDialog {

    JLabel label;
    
    public HelpWindow(JFrame frame) {
        super(frame, "Instructions", true);
        setLayout(new FlowLayout());
        
        label = new JLabel("These are the instructions");
        add(label);
    }

}