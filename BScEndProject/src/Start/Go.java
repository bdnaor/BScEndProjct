package Start;

import javax.swing.JFrame;

import GUI.mainPanel;



public class Go {

	public static void main(String[] args) 
	{
		JFrame mf 		= new JFrame();
		mainPanel mp 	= new mainPanel();
		mp.setVisible(true);
		mf.add(mp);
		
		
		mf.setSize(490,500);
		mf.setLocationRelativeTo(null);
		mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		mf.setVisible(true);
		mf.setResizable(false);
	}

}
