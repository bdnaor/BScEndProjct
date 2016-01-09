package Start;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import Algorithm.AdaBoost;
import Objects.Item;
import Objects.Rule;

public class Start {
	
	/*	Variables	*/	
	private String 				InputPath;	
	private Integer 			AttributeNumber;							/*	The number of attribute in any single Item.						*/
	private Integer 			TrainGroupSize = 25;						/*	The group size for create single sub rule.						*/
	private Integer 			Iteration = 50;								/*	Number of AdaBoost Iteration.									*/
	private Integer				Learning_Percent_Size = 50;					/*	The percent of learning from the all data.						*/
	private Integer 			Boost_Iteration = 25;
	private Integer				PosCount = 0;
	private Integer 			NegCount = 0;
	
	private Double 				Accuracy_Percent;
	private Double 				Sensitivity_Percnt;
	private Double 				Specificity_Percent;
	
	private ArrayList<Item> 	data;// = new ArrayList<Item>();				/* 	All the data after we read the ".csv" file.						*/
	private ArrayList<Item> 	Learning_data;//  = new ArrayList<Item>();		/*	The Learning set that AdaBoost train on.						*/
	private ArrayList<Item> 	Testing_data;//  = new ArrayList<Item>();		/* 	The testing set that we test the rules we grt from AdaBoost. 	*/	
	private ArrayList<Item> 	Sub_data_for_ada;//  = new ArrayList<Item>();	/*	The chosen data for sending to AdaBoost algorithm.				*/
	private ArrayList<Rule> 	RulesArr;//  = new ArrayList<Rule>();			/*	The Set of Rule that AdaBoost return.							*/
	private ArrayList<Double>	Item_Weight;//  = new ArrayList<Double>();		/*	The weight for every item in the learning set.					*/
	
	/*	Constructors	*/
	public Start(String path,Integer Learning_Percent_Size,Integer Iteration,Integer TrainGroupSize ,Integer Boost_Iteration) 
	{
		this.setPathInput(path);
		this.setLearning_Percent_Size(Learning_Percent_Size);
		this.setIteration(Iteration);
		this.setTrainGroupSize(TrainGroupSize);
		this.setBoost_Iteration(Boost_Iteration);	
		
		Learning_data = new ArrayList<Item>();
		Testing_data = new ArrayList<Item>();
		Sub_data_for_ada = new ArrayList<Item>();
		RulesArr = new ArrayList<Rule>();
		Item_Weight = new ArrayList<Double>();	
		
		Go();
	}
	public Start(String path) 
	{
		this.setPathInput(path);
		readData();
	}
	
	/*	Methods	*/
	public void AdaBoostIteration()
	{
		buildLearning();
		Rule r = new AdaBoost(Sub_data_for_ada,Boost_Iteration).getRule();				
		RulesArr.add(r);
		UpdateWeight();
	}
	public void UpdateResultPredection()
	{
		int Accuracy = 0;
		int Sensitivity = 0;
		int NumberOfPosInTesting = 0;
		for(int i=0;i<Testing_data.size();i++)
		{
			Item x = Testing_data.get(i);
			if(GoodPrediction(x))
			{				
				Accuracy++;
				if(x.getAttributes()[AttributeNumber-1]==1)
					Sensitivity++;
			}			
			if(x.getAttributes()[AttributeNumber-1]==1)
				NumberOfPosInTesting++;
		}
		
		
		Accuracy_Percent = new Double((double)Accuracy/Testing_data.size())*100;
		Sensitivity_Percnt = new Double((double)Sensitivity/NumberOfPosInTesting)*100;
		Specificity_Percent = new Double((double)(Accuracy-Sensitivity)/(Testing_data.size()-NumberOfPosInTesting))*100;
	}

	private void Go(){
		
		readData();
		SplitData(Learning_Percent_Size);
		initWigth();
		for(int i=0; i < Iteration ; i++ )
			AdaBoostIteration();
		
		
		UpdateResultPredection();	

		/*
		System.out.println("\t\tpositive|\tNegative|\tTotal");		
		System.out.println("Learning Size:\t"+PosAtLearning()+"\t\t"+(Learning_data.size()-PosAtLearning())+"\t\t"+Learning_data.size());
		System.out.println("Testing Size:\t"+NumberOfPosInTesting+"\t\t"+(Testing_data.size()-NumberOfPosInTesting)+"\t\t"+Testing_data.size());
		System.out.println("");
		System.out.println("Accuracy = "+Accuracy_Percent+"%  = "+Accuracy+"/"+Testing_data.size());
		System.out.println("Sensitivity = "+Sensitivity_Percnt+"%  = "+Sensitivity+"/"+NumberOfPosInTesting);
		System.out.println("Nigative Prediction = "+Specificity_Percent+"%  = "+(Accuracy-Sensitivity)+"/"+(Testing_data.size()-NumberOfPosInTesting));
		*/
		//System.out.println("\nRule:\n"+rules);
	}
	private void UpdateWeight() 
	{
		int failed = 0 ;
		double WeightTake = 0 ;
		ArrayList<Integer> failedArr = new ArrayList<Integer>();
		for(int i=0;i<Learning_data.size();i++)
		{
			if(!GoodPrediction(Learning_data.get(i)))
			{
				failed++;
				failedArr.add(i);
			}
		
		}
		
		int success = Learning_data.size()- failed;
		for(int i=0;i<Learning_data.size();i++)		
			if(GoodPrediction(Learning_data.get(i)))
			{
				WeightTake+=Item_Weight.get(i)/success;
				Item_Weight.set(i, Item_Weight.get(i)-Item_Weight.get(i)/success);
			}
				
		double 	inc = WeightTake/failed;
		for(int i =0 ; i< failedArr.size();i++)
			Item_Weight.set(failedArr.get(i), Item_Weight.get(failedArr.get(i))+inc);
	}
	private void initWigth() 
	{
		double Wigth =1/(double)Learning_data.size();
		 for(int i =0 ; i < Learning_data.size(); i++)
				Item_Weight.add(Wigth);
		
	}
	private boolean GoodPrediction(Item testInput) 
	{
		double score = 0;		
		for(int i =0 ; i<RulesArr.size() ; i++)
			//if(RulesArr.get(i).getSuccessPercent()>0.65)
				score+=RulesArr.get(i).getSuccessPercent()*RulesArr.get(i).getPrediction(testInput);

		if(score>0)
			if(testInput.getAttributes()[AttributeNumber-1]==1)
				return true;
			else
				return false;
		else
			if(testInput.getAttributes()[AttributeNumber-1]==-1)
				return true;
			else
				return false;
		
	}
	private void readData() 
	{	
		data = new ArrayList<Item>();				
		try {			
				File f = new File(getInputData());
				BufferedReader br = new BufferedReader(new FileReader(f));
				String str = br.readLine();
				Integer[] att = null;
				
				while(str!=null && str!="")
				{					
					String[] sample= str.split(",");
					att = new Integer[sample.length];
					if(AttributeNumber==null)
						AttributeNumber=att.length;
					for(int i = 0 ; i < sample.length ; i++)					
						att[i]= new Integer(sample[i]);
					
					if(att[AttributeNumber-1]==0)NegCount++;
					else if(att[AttributeNumber-1]==1)PosCount++;
					else JOptionPane.showMessageDialog(null, "Bad Result Column Line : "+ data.size());
					
					data.add(new Item(att));
					str = br.readLine();					
				}
				br.close();
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void SplitData(int Learning_percent ) 
	{		
		double x = (double)Learning_percent/100;
		Collections.shuffle(data);
		
		for(int i = 0 ; i < data.size() ; i++)
			if(i<data.size()*x)
				Learning_data.add(data.get(i));
			else
				Testing_data.add(data.get(i));
			
	}		
	private void buildLearning()
	{
		ArrayList<Integer> ChosenIndexs = new ArrayList<Integer>();
		Sub_data_for_ada = new ArrayList<Item>();
		for(int i=0;i<TrainGroupSize; i++)
		{
			Integer x = chooseItem();
			while(ChosenIndexs.contains(x))
				x = chooseItem();
			if(x==-1)
			{
				JOptionPane.showMessageDialog(null, "Can't success Building Learnig set for Ada.");
				
			}
			ChosenIndexs.add(x);
			Sub_data_for_ada.add(Learning_data.get(x));
		}
		
		//System.out.println("ChosenIndexs: "+ChosenIndexs);
			
	}
	private int chooseItem()
	{
		double s = Math.random();
		for(int i = 0 ; i < Item_Weight.size();i++)
		{
			s-=Item_Weight.get(i);
			if(s<=0)
				return i;
		}
		return -1;
	}

	
	/*	Setters	*/
	public void setTrainGroupSize(Integer trainGroupSize) {
		TrainGroupSize = trainGroupSize;
	}
	public void setIteration(Integer iteration) {
		Iteration = iteration;
	}
	public void setLearning_Percent_Size(Integer learning_Percent_Size) {
		Learning_Percent_Size = learning_Percent_Size;
	}
	public void setBoost_Iteration(Integer boost_Iteration) {
		Boost_Iteration = boost_Iteration;
	}	
	public void setPathInput(String inputData) {
		InputPath = inputData;
	}
	
	/*	Getters	*/
	public String  getInputData() {
		return InputPath;
	}
	public Integer getTrainGroupSize() {
		return TrainGroupSize;
	}
	public Integer getIteration() {
		return Iteration;
	}
	public Integer getLearning_Percent_Size() {
		return Learning_Percent_Size;
	}
	public Integer getBoost_Iteration() {
		return Boost_Iteration;
	}
	public Integer getPosCount() {
		return PosCount;
	}
	public Integer getNegCount() {
		return NegCount;
	}
	public Double getAccuracy_Percent() {
		return Accuracy_Percent;
	}
	public Double getSensitivity_Percnt() {
		return Sensitivity_Percnt;
	}
	public Double getSpecificity_Percent() {
		return Specificity_Percent;
	}

}

/*	Not in use */
	/*
	private void SplitPosNeg()
	{
		ArrayList<Item> Positive_data = new ArrayList<Item>();
		ArrayList<Item> Negative_data = new ArrayList<Item>();			


		for(int i =0 ; i < data.size(); i++)
		{
			Integer res = data.get(i).getAttributes()[AttributeNumber-1];
			if(res == -1 || res == 0)
				Positive_data.add(data.get(i));
			else 
				Negative_data.add(data.get(i));
		}
		
		int X = Math.min(Positive_data.size(), Negative_data.size());
	}
	private int PosAtLearning() 
	{
		int count=0;
		for (Item testInput : Learning_data) {
			if(testInput.getAttributes()[AttributeNumber-1]==1)
				count++;
		}
		return count;
	}
	 */