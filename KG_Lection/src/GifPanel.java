import javax.swing.*;
import java.awt.*;

public class GifPanel extends JLabel {
    public GifPanel() {
        Image image = Toolkit.getDefaultToolkit().createImage("./Gif/ForKirill.gif");
        ImageIcon imageIcon = new ImageIcon(image);
        imageIcon.setImageObserver(this);
        this.setIcon(imageIcon);
    }
}
