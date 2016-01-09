package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SpringLayout;


@SuppressWarnings("serial")
public class mainPanel extends JPanel implements ActionListener
{
	/*	Variables	*/
	private JButton load;
	private JButton Go;
	private JLabel picLabel;
	
	/*	Constructor	*/
	public mainPanel()
	{
		setBackground(new Color(0, 51, 153));
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		BufferedImage myPicture;
		try {
			myPicture = ImageIO.read(this.getClass().getResource("Logo.jpg"));//C:\\Users\\Naor\\Desktop\\Data\\
			picLabel = new JLabel(new ImageIcon(myPicture));
			add(picLabel);		
		} catch (IOException e) 
		{		
			e.printStackTrace();
		}
		
		JTabbedPane jt = new JTabbedPane();
		springLayout.putConstraint(SpringLayout.SOUTH, jt, 0, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, jt, 0, SpringLayout.EAST, picLabel);
		jt.addTab("Ada - Boost",new AdaBoostPanel());
		//jt.addTab("summary",new JPanel());
		springLayout.putConstraint(SpringLayout.NORTH, jt, 6, SpringLayout.SOUTH, picLabel);
		springLayout.putConstraint(SpringLayout.WEST, jt, 0, SpringLayout.WEST, picLabel);
		add(jt);

		
	}
	 
	/*	Methods	*/
	@Override
	public void actionPerformed(ActionEvent e) 
	{
			if(e.getSource()==load)
				JOptionPane.showMessageDialog(null,"load");
			if(e.getSource()==Go)
				JOptionPane.showMessageDialog(null,"Go");
	}
}
