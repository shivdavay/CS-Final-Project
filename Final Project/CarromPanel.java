import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

public class CarromPanel extends JPanel
{
    
    private JFrame cur;
    public CarromPanel(JFrame f, int p)
    {
    
    cur = f;
    DisplayCarrom board = new DisplayCarrom(p);
    board.addMouseListener(new Mouse());
    board.setPreferredSize(new Dimension(750,750));
    add(board);


    //buf.drawImage( i.getImage() , 0 , 0 , 750 , 750 , null );
    } 
    //ranges where striker can be
    /**************
    X: 162   Y: 610
    X: 589   Y: 631

    X: 114   Y: 170
    X: 141   Y: 582

    X: 161   Y: 123
    X: 584   Y: 145

    X: 612   Y: 171
    X: 635   Y: 587
    **************/

    private class Mouse extends MouseAdapter{
        public void mouseClicked(MouseEvent e){  
            int x = e.getX();
            int y = e.getY();
            if(x>160&&x<590&&y>610&&y<630){
                System.out.println("WORKING");
            }else{
                System.out.println(x + " " + y);
            }
        }
     }
    
}
