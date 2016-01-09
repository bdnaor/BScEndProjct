package GUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class LogoPanel extends JPanel
{
	/*	Variables	*/
    private BufferedImage image;
    private String path = "C:\\Users\\Naor\\Desktop\\Data\\Logo.jpg";

    /*	Constructor	*/
    public LogoPanel()
    {       
       try 
       {                
          image = ImageIO.read(new File(path));
       } 
       catch (IOException ex)
       {
            JOptionPane.showMessageDialog(null, "problem with :"+path);
       }
    }

    /*	Methods	*/
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);         
    }

}