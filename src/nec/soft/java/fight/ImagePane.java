package nec.soft.java.fight;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImagePane extends JPanel {

    private Image image;
    
    public ImagePane(String url) {
        try {
            image = ImageIO.read(new File(url));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width=this.getWidth();
        int heigth=this.getHeight();
        g.drawImage(image, 0, 0,width,heigth, this);
    }
    
}