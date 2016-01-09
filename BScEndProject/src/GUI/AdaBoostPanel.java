package GUI;

import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import Start.Start;

@SuppressWarnings("serial")
public class AdaBoostPanel extends JPanel implements ActionListener 
{
	/*	Variables	*/
	private JTextField DataPath;
	private JTextField LearningSize;
	private JTextField AdaIteration;
	private JTextField GroupSize;
	private JTextField BoostIteration;
	private JButton load;
	private JButton Evolution_Prediction;
	private JButton btnDifferntIteration;
	private JLabel TotLabel;
	private JLabel lblPoslab;
	private JLabel lblNeglab;
	private Start S;		
	private JLabel lblNumberOfIterations;	
	private JPanel panel;
	private JTextField IterationNumber;

	/*	Constructor	*/
 	public AdaBoostPanel() 
	{
		SpringLayout springLayout = new SpringLayout();
		setLayout(springLayout);
		
		JLabel lblData = new JLabel("Data");
		springLayout.putConstraint(SpringLayout.NORTH, lblData, 10, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.WEST, lblData, 10, SpringLayout.WEST, this);
		add(lblData);
		
		DataPath = new JTextField(30);
		DataPath.setColumns(30);
		springLayout.putConstraint(SpringLayout.NORTH, DataPath, 6, SpringLayout.SOUTH, lblData);
		springLayout.putConstraint(SpringLayout.WEST, DataPath, 10, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.EAST, DataPath, -124, SpringLayout.EAST, this);
		add(DataPath);
		
		load = new JButton("Choose File");
		springLayout.putConstraint(SpringLayout.NORTH, load, -1, SpringLayout.NORTH, DataPath);
		springLayout.putConstraint(SpringLayout.WEST, load, 6, SpringLayout.EAST, DataPath);
		load.addActionListener(this);
		add(load);
		
		JLabel lblLearningSize = new JLabel("Learning Size:");
		springLayout.putConstraint(SpringLayout.NORTH, lblLearningSize, 22, SpringLayout.SOUTH, DataPath);
		springLayout.putConstraint(SpringLayout.WEST, lblLearningSize, 0, SpringLayout.WEST, lblData);
		add(lblLearningSize);
		
		LearningSize = new JTextField(30);
		springLayout.putConstraint(SpringLayout.NORTH, LearningSize, -3, SpringLayout.NORTH, lblLearningSize);
		springLayout.putConstraint(SpringLayout.WEST, LearningSize, 116, SpringLayout.EAST, lblLearningSize);
		add(LearningSize);
		
		JLabel label = new JLabel("%");
		springLayout.putConstraint(SpringLayout.EAST, LearningSize, -6, SpringLayout.WEST, label);
		springLayout.putConstraint(SpringLayout.WEST, label, 241, SpringLayout.WEST, this);
		springLayout.putConstraint(SpringLayout.NORTH, label, 0, SpringLayout.NORTH, lblLearningSize);
		add(label);
		
		
		JLabel lblGroupSize = new JLabel("Group Size");
		springLayout.putConstraint(SpringLayout.NORTH, lblGroupSize, 50, SpringLayout.SOUTH, lblLearningSize);
		springLayout.putConstraint(SpringLayout.WEST, lblGroupSize, 10, SpringLayout.WEST, this);
		add(lblGroupSize);
		
		GroupSize = new JTextField(10);
		springLayout.putConstraint(SpringLayout.NORTH, GroupSize, -3, SpringLayout.NORTH, lblGroupSize);
		springLayout.putConstraint(SpringLayout.WEST, GroupSize, 132, SpringLayout.EAST, lblGroupSize);
		springLayout.putConstraint(SpringLayout.EAST, GroupSize, -166, SpringLayout.EAST, this);
		add(GroupSize);
		
		
		JLabel lblBoostIterationNumber = new JLabel("Max Boost Iteration Number");
		springLayout.putConstraint(SpringLayout.NORTH, lblBoostIterationNumber, 20, SpringLayout.SOUTH, lblGroupSize);
		springLayout.putConstraint(SpringLayout.WEST, lblBoostIterationNumber, 0, SpringLayout.WEST, lblData);
		add(lblBoostIterationNumber);
		
		BoostIteration = new JTextField(10);
		springLayout.putConstraint(SpringLayout.NORTH, BoostIteration, -3, SpringLayout.NORTH, lblBoostIterationNumber);
		springLayout.putConstraint(SpringLayout.WEST, BoostIteration, 48, SpringLayout.EAST, lblBoostIterationNumber);
		springLayout.putConstraint(SpringLayout.EAST, BoostIteration, -166, SpringLayout.EAST, this);
		add(BoostIteration);
		
		DataPath.setText("C:\\Users\\Naor\\Desktop\\Data\\Airlines_avio.csv");
		LearningSize.setText("50");
		GroupSize.setText("5");;
		BoostIteration.setText("3");
		
		TotLabel = new JLabel();
		TotLabel.setText("");
		springLayout.putConstraint(SpringLayout.NORTH, TotLabel, 6, SpringLayout.SOUTH, load);
		springLayout.putConstraint(SpringLayout.WEST, TotLabel, 0, SpringLayout.WEST, load);
		springLayout.putConstraint(SpringLayout.SOUTH, TotLabel, -14, SpringLayout.SOUTH, lblLearningSize);
		springLayout.putConstraint(SpringLayout.EAST, TotLabel, 87, SpringLayout.WEST, load);
		add(TotLabel);
		
		lblPoslab = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lblPoslab, 3, SpringLayout.SOUTH, TotLabel);
		springLayout.putConstraint(SpringLayout.WEST, lblPoslab, 0, SpringLayout.WEST, load);
		springLayout.putConstraint(SpringLayout.EAST, lblPoslab, 0, SpringLayout.EAST, load);
		add(lblPoslab);
		
		lblNeglab = new JLabel("");
		springLayout.putConstraint(SpringLayout.NORTH, lblNeglab, 90, SpringLayout.NORTH, this);
		springLayout.putConstraint(SpringLayout.SOUTH, lblPoslab, -1, SpringLayout.NORTH, lblNeglab);
		springLayout.putConstraint(SpringLayout.WEST, lblNeglab, 0, SpringLayout.WEST, load);
		springLayout.putConstraint(SpringLayout.EAST, lblNeglab, 0, SpringLayout.EAST, load);
		add(lblNeglab);

		
		panel = new JPanel();
		springLayout.putConstraint(SpringLayout.NORTH, panel, 24, SpringLayout.SOUTH, BoostIteration);
		springLayout.putConstraint(SpringLayout.WEST, panel, 0, SpringLayout.WEST, lblData);
		springLayout.putConstraint(SpringLayout.SOUTH, panel, -10, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, panel, -73, SpringLayout.EAST, DataPath);
		panel.setBackground(SystemColor.textHighlight);
		add(panel);
		
		btnDifferntIteration = new JButton("Differnt Iteration");
		btnDifferntIteration.addActionListener(this);			
		SpringLayout sl_panel = new SpringLayout();
		sl_panel.putConstraint(SpringLayout.NORTH, btnDifferntIteration, 49, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, btnDifferntIteration, 41, SpringLayout.WEST, panel);
		panel.setLayout(sl_panel);
		
		JLabel lblNumberOfAdaboost = new JLabel("Number Of AdaBoost Iteration");
		sl_panel.putConstraint(SpringLayout.NORTH, lblNumberOfAdaboost, 5, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, lblNumberOfAdaboost, 25, SpringLayout.WEST, panel);
		panel.add(lblNumberOfAdaboost);
		springLayout.putConstraint(SpringLayout.NORTH, lblNumberOfAdaboost, 18, SpringLayout.SOUTH, lblLearningSize);
		springLayout.putConstraint(SpringLayout.WEST, lblNumberOfAdaboost, 10, SpringLayout.WEST, this);

		
		AdaIteration = new JTextField(10);
		sl_panel.putConstraint(SpringLayout.NORTH, AdaIteration, 24, SpringLayout.NORTH, panel);
		sl_panel.putConstraint(SpringLayout.WEST, AdaIteration, 55, SpringLayout.WEST, panel);
		panel.add(AdaIteration);
		AdaIteration.setText("10");
		panel.add(btnDifferntIteration);
		springLayout.putConstraint(SpringLayout.SOUTH, btnDifferntIteration, -20, SpringLayout.SOUTH, this);
		springLayout.putConstraint(SpringLayout.EAST, btnDifferntIteration, -275, SpringLayout.EAST, this);
		
		Evolution_Prediction = new JButton("Evolution Of Prediction");
		springLayout.putConstraint(SpringLayout.NORTH, Evolution_Prediction, 0, SpringLayout.NORTH, panel);
		springLayout.putConstraint(SpringLayout.WEST, Evolution_Prediction, 18, SpringLayout.EAST, panel);
		springLayout.putConstraint(SpringLayout.SOUTH, Evolution_Prediction, 0, SpringLayout.SOUTH, panel);
		springLayout.putConstraint(SpringLayout.EAST, Evolution_Prediction, -10, SpringLayout.EAST, this);
		add(Evolution_Prediction);
		Evolution_Prediction.addActionListener(this);
		
		IterationNumber = new JTextField();
		springLayout.putConstraint(SpringLayout.WEST, IterationNumber, 0, SpringLayout.WEST, LearningSize);
		springLayout.putConstraint(SpringLayout.SOUTH, IterationNumber, -6, SpringLayout.NORTH, GroupSize);
		springLayout.putConstraint(SpringLayout.EAST, IterationNumber, 0, SpringLayout.EAST, LearningSize);
		add(IterationNumber);
		IterationNumber.setText("3");
		IterationNumber.setColumns(10);
		
		lblNumberOfIterations = new JLabel("Number of  iterations");
		springLayout.putConstraint(SpringLayout.NORTH, lblNumberOfIterations, 18, SpringLayout.SOUTH, lblLearningSize);
		springLayout.putConstraint(SpringLayout.WEST, lblNumberOfIterations, 0, SpringLayout.WEST, lblData);
		add(lblNumberOfIterations);
		
		
	
		
		
		
		
	
	}

	/*	ActionListener	*/	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		if(e.getSource()==load)
		{
			
			FileDialog FileDialog = new FileDialog(new JFrame());//
			FileDialog.setVisible(true);
			//FileDialog.show();
			String path = FileDialog.getDirectory()+FileDialog.getFile();
			if(fileOK(path))
			{
				DataPath.setText(path);
				
			}
			
		}
		if(e.getSource()==Evolution_Prediction || e.getSource()==btnDifferntIteration)
		{	
			/*	Check the path	*/
			String path = DataPath.getText();
			if(!fileOK(path))
				return;
			
			/*	Check the Learning Size	Text Filed	*/
			Integer LZ;
			if((LZ = isPositiveInteger(LearningSize.getText()))==-1)return;
			if(LZ>100)
			{
				JOptionPane.showMessageDialog(null,"please select integer less than 100.");
				return;
			}
			
			/*	Check the Different Iteration Text Filed	*/
			Integer DI;
			if((DI = isPositiveInteger(AdaIteration.getText()))==-1)return;
			if(DI>100000)
			{
				JOptionPane.showMessageDialog(null,"please select integer less than 100000.");
				return;
			}	
			
			
			/*	Check the Group size Text Filed	*/
			Integer GS;
			if((GS = isPositiveInteger(GroupSize.getText()))==-1)return;
			if(GS>100000)
			{
				JOptionPane.showMessageDialog(null,"please select integer less than 100000.");
				return;
			}	
			
			
			/*	Check the Boost Iteration Text Filed	*/
			Integer BI;
			if((BI = isPositiveInteger(BoostIteration.getText()))==-1)return;
			if(BI>1000)
			{
				JOptionPane.showMessageDialog(null,"please select integer less than 1000.");
				return;
			}
			
			/*	X Iteration Text Filed	*/
			Integer X;
			if((X = isPositiveInteger(IterationNumber.getText()))==-1)return;
			if(X>100000)
			{
				JOptionPane.showMessageDialog(null,"please select integer less than 100000 in the Number of iteration Text Filed.");
				return;
			} 
			
			if(e.getSource()==Evolution_Prediction)
				FindBestIterationNumber(path,LZ,GS,BI,X);
			else
				ShowXdifferntIteration(X,path,LZ,DI,GS,BI);
			
		}
	}	

	/*	Methods	*/	
	public boolean fileOK(String path)
	{
		
			File f = new File(path);
			if(!f.exists())
			{
				JOptionPane.showMessageDialog(null,"File Not Exist please chose anuder");
				return false;
			}
			String Type = path.substring(path.lastIndexOf('.')+1);
			if(!Type.equalsIgnoreCase("csv"))
			{
				JOptionPane.showMessageDialog(null,"File Type need to be \".csv\"");
				return false;
			}
			S = new Start(path);
			int tot = S.getPosCount()+S.getNegCount();
			TotLabel.setText("Tot: "+tot);
			lblPoslab.setText("Pos: "+S.getPosCount());
			lblNeglab.setText("Neg: "+S.getNegCount());
		return true;
	}
	public Integer isPositiveInteger(String str)  
	{  Integer d ;
	  try  
	  {  
	     d = Integer.parseInt(str); 
	     if(d>0)return d;
	     else return -1;
	  }  
	  catch(NumberFormatException nfe)  
	  { 
		JOptionPane.showMessageDialog(null,"Expected to Positive Integer");
	    return -1;  
	  }  
	   
	}	

	private void ShowHistogram(ArrayList<Double> a, ArrayList<Double> sE, ArrayList<Double> sP,String title)
	{
			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for(int i =0 ; i< a.size();i++)
			{
				dataset.addValue(sE.get(i), "Sensitivity", i+"");
				dataset.addValue(a.get(i), "Accuracy", i+"");
				dataset.addValue(sP.get(i), "Specificity", i+"");
			}
			String histoName = a.size()+" Different AdaBoost Iteratoion";
		 	final Histogram demo = new Histogram(title,dataset,histoName,"Iteraion Number","percent of Success prediction");
	        demo.pack();
	        RefineryUtilities.centerFrameOnScreen(demo);
	        demo.setVisible(true);
	}
	private void FindBestIterationNumber(String path, Integer lZ, Integer gS, Integer bI, Integer x2)
	{
			//Start S = new Start(path,LZ,AI,GS,BI);
			ArrayList<Integer> X = new ArrayList<Integer>();
			ArrayList<Double> A = new ArrayList<Double>();
			ArrayList<Double> SE = new ArrayList<Double>();
			ArrayList<Double> SP = new ArrayList<Double>();

			
//			JFrame f = new JFrame();
//			JProgressBar jpb = new JProgressBar(0, x2);
//			jpb.setStringPainted(true);
//			jpb.setValue(0);	
//			f.add(jpb);
//			f.setLocationRelativeTo(null);
//			f.setSize(100, 60);			
//			f.setVisible(true);
			
			Start S = new Start(path,lZ,1,gS,bI);
			for(int i=1;i<x2;i++)
			{
//				if((((double)i/x2)*100)%5==0 || i==1 || i==x2-1)
//				{
					S.UpdateResultPredection();	
					X.add(i);
					A.add(S.getAccuracy_Percent());
					SE.add(S.getSensitivity_Percnt());
					SP.add(S.getSpecificity_Percent());
					System.out.println(i+","+S.getAccuracy_Percent()+","+S.getSensitivity_Percnt()+","+S.getSpecificity_Percent());
//				}
				S.AdaBoostIteration();				
				
				if(i==x2-1)
					x2=x2;
//				f.validate();
//				f.repaint();
//				jpb.setValue(x2);
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			}		
			
			AccuracyImprovementGraph a = new AccuracyImprovementGraph("",X,A,SE,SP,x2+1);
//			f.dispose();
			a.setVisible(true);
		
	}
	private void ShowXdifferntIteration(int X ,String path, Integer lZ, Integer dI, Integer gS, Integer bI)
	{
		Start S;		
		ArrayList<Double> A = new ArrayList<Double>();
		ArrayList<Double> SE = new ArrayList<Double>();
		ArrayList<Double> SP = new ArrayList<Double>();		
				
		for(int i=0;i<dI;i++)
		{
			S = new Start(path,lZ,X,gS,bI);
			A.add(S.getAccuracy_Percent());
			SE.add(S.getSensitivity_Percnt());
			SP.add(S.getSpecificity_Percent());		
		}	
		ShowHistogram(A,SE,SP,path);
	
	

	}


}
