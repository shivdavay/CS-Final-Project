import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class DisplayCarrom extends JPanel
{
    private ImageIcon i = new ImageIcon("carrom.jpg");

    private BufferedImage img= new BufferedImage(500,500,BufferedImage.TYPE_INT_RGB);

    private Graphics buf = img.getGraphics();


    public DisplayCarrom()
    {
    int w = img.getWidth();
    int h = img.getHeight();
    //
    buf.drawImage( i.getImage() , 0 , 0 , w , h , null );
    } 
}
