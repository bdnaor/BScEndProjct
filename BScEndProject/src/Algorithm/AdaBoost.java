package Algorithm;
import java.util.ArrayList;
import java.util.Collections;

import Objects.Item;
import Objects.Node;
import Objects.Rule;
import Objects.SubRule;


public class AdaBoost 
{
	/*	Variables	*/
	private ArrayList<Double> P;	
	private ArrayList<Node> Nodes;
	private ArrayList<Integer> SuccessPrediction; //1 - success  , -1 - not success. 	
	private ArrayList<Item> Learning_data;
	private Rule rule;
	
	/*	Constructor	*/
	public AdaBoost(ArrayList<Item> ALL_Learning_data, Integer boost_Iteration) 
	{
		this.Learning_data = ALL_Learning_data;				
		 P = new ArrayList<Double>();	
		 SuccessPrediction = new ArrayList<Integer>();
		 rule = new Rule();
		 /*For testing only - see the sub learning group attribute	*/
//		 for(int k =0 ; k<Learning_data.size();k++)		 
//			 System.out.println(Learning_data.get(k));
		 
		 
		 
		initWigth();
		SubRule sr = getBestSubRule();
		//System.out.println(sr);
		int Mistakes = 1;
		int iteration = 1;
		while(sr.getErrorPercent()>0 && sr.getAlpa()<1000 && Mistakes!=0)		
		{
			iteration++;			
			if(iteration>boost_Iteration)				
				break;
			updateSuccessPrediction(sr);
			updateP(sr);			
			rule.AddSubRule(sr);	

			sr = getBestSubRule();
			
		}
		/*Number Of Mistakes */
		Mistakes = 0;
		for (Item item : ALL_Learning_data) 
		{
			int res = item.getAttributes()[item.getAttributes().length-1];
			if(rule.getPrediction(item)!=res)
					Mistakes++;
		}
		rule.setSuccessPercent((double) (1-((double)Mistakes/Learning_data.size())));
		System.out.println("Mistakes= "+Mistakes);//
			
		
		
		
		
		
	}
	
	/*	Methods	*/	
	private SubRule Boost(Integer AttNum)
	{
		ArrayList<Double> errArr = new ArrayList<Double>();
		ArrayList<Double> trasholdArr = new ArrayList<Double>();
		ArrayList<String> operation = new ArrayList<String>();
		
		Double TP = 0.0; //total sum of positive sample weights
		Double TN = 0.0; //total sum of negative sample weights
		Double SP = 0.0; // : sum of positive sample weights below the threshold
		Double SN = 0.0; // : sum of negative sample weights below the threshold
		
		Collections.sort(Nodes);
		for (int i = 0 ; i < Nodes.size() ; i++) 
		{
			if(Nodes.get(i).getItemResult()==1)
				TP+=P.get(i);
			else 
				TN+=P.get(i);
		}
		/*Because 0.1 + 0.2 -> 0.30000000000000004 */
		if(TN==0.30000000000000004)TN=0.3;
		if(TN==0.79999999999999999)TN=0.8;
		if(TN==0.89999999999999999)TN=0.9;
		if(TN==0.99999999999999999)TN=1.0;
		if(TP==0.30000000000000004)TP=0.3;
		if(TP==0.79999999999999999)TP=0.8;
		if(TP==0.89999999999999999)TP=0.9;
		if(TP==0.99999999999999999)TP=1.0;
//		
		int min = Nodes.get(0).getAttValue();
		int max = Nodes.get(Nodes.size()-1).getAttValue();
		int i = 0;
		
		
		for (double trashold = min-0.5 ; trashold < max ; trashold+=1) 
		{
			
			while(i<Nodes.size()-1 && Nodes.get(i).getAttValue()<trashold)
			{
				if(Nodes.get(i).getItemResult()==1)SP+=P.get(i);
				else SN+=P.get(i);
				i++;
			}
			trasholdArr.add(trashold);
			if(i<Nodes.size())
				trashold = Nodes.get(i).getAttValue() - 0.5;
			double x = (SP*100000+TN*100000-SN*100000)/100000;
			double y = (SN*100000+TP*100000-SP*100000)/100000;	
			if(x>y)operation.add("<");//it's mean that the positive side <X
			else operation.add(">");				//positive side > X
			errArr.add((double) Math.min(x,y));
		}
		
		/*	Find minErr Index */		
		double minerr = 1;
		int minIndex = 0;
		for(i=0;i<errArr.size();i++)
		{
			if(errArr.get(i)<minerr)
			{
				minerr = errArr.get(i);
				minIndex = i ;				
			}
		}
		if(errArr.size()>0)
			return new SubRule(minerr,operation.get(minIndex),trasholdArr.get(minIndex),AttNum);
		else
			return null;
		
	}
	private void initWigth() 
	{
		double Wigth =1/(double)Learning_data.size();
		/* initialize  */
		for(int i =0 ; i < Learning_data.size(); i++)
			P.add(Wigth);				
	}
	private void updateP(SubRule SR) 
	{		
		Double Alpa = SR.getAlpa();
		ArrayList<Double> q = new ArrayList<Double>();
		Double Z = new Double("0");
		for(int i = 0 ; i < P.size() ; i++)
		{
			Double H = Math.pow(Math.E, SuccessPrediction.get(i)*Alpa*-1);
			//Double H = SR.getErrorPercent()/(1-SR.getErrorPercent()); 
			q.add(P.get(i)*H);
			Z+=P.get(i)*H;
		}
		for(int i = 0 ; i < P.size() ; i++)
			P.set(i, q.get(i)/Z);
		
	}
	private void updateSuccessPrediction(SubRule SR)
	{
		SuccessPrediction = new ArrayList<Integer>();
		for(int i=0;i<Nodes.size();i++)
			if(ChackTerm(i,SR.getOperation(),SR.getThreshold()))
				SuccessPrediction.add(1);
			else
				SuccessPrediction.add(-1);
		
	}
	private boolean ChackTerm(int index, String cond , Double trashold)
	{
		if(cond.equals("<"))//it's mean that the positive side <X
		{		
			if(Nodes.get(index).getAttValue()<trashold)
				if(Nodes.get(index).getItemResult()==1)
					return true;
				else
					return false;
			else
				if(Nodes.get(index).getItemResult()==-1)
					return true;
				else
					return false;
		}		
		else
		{
			if(Nodes.get(index).getAttValue()<trashold)
				if(Nodes.get(index).getItemResult()==-1)
					return true;
				else
					return false;
			else
				if(Nodes.get(index).getItemResult()==1)
					return true;
				else
					return false;
		}
		
		//JOptionPane.showMessageDialog(null, "Error at ChackTerm Function!!!");
		//return false;
	}	
	private SubRule getBestSubRule()
	{
		int attnum = Learning_data.get(0).getAttributes().length;
		ArrayList<SubRule> SubRuleArr = new ArrayList<SubRule>();
		for(int i=0 ;i< attnum-1 ; i++)
		{
			Nodes = new ArrayList<Node>();
			for(int j=0; j <Learning_data.size() ; j++)
				Nodes.add(new Node(Learning_data.get(j).getAttributes()[i], Learning_data.get(j).getAttributes()[attnum-1]));
			
			
			SubRuleArr.add(Boost(i));
		}
		
		/* Select And Return the Sub Rule with the minimum error */
		double minerr = 1;
		int minerrIndex = 0;
		for(int i=0 ; i< SubRuleArr.size() ; i++)
		{
			if(SubRuleArr.get(i)!=null && SubRuleArr.get(i).getErrorPercent()<minerr)
			{
				minerr = SubRuleArr.get(i).getErrorPercent();
				minerrIndex = i;
			}
		}
		/*Becouse we update acording to the Nodes*/
		Nodes = new ArrayList<Node>();
		for(int j=0; j <Learning_data.size() ; j++)
			Nodes.add(new Node(Learning_data.get(j).getAttributes()[minerrIndex], Learning_data.get(j).getAttributes()[attnum-1]));
		
		return SubRuleArr.get(minerrIndex);
	}
	/*	Getters	*/
	public Rule getRule() {
		return rule;
	}

}
/*	End	AdaBoost */



	/*	Not in Use	*/	
	/*private int numberOfmistaks()
	{
		int mistaks = 0;
		for (int i = 0 ; i < att.size() ; i++)			
		{
			double f = 0 ;			
			for (SubRule sr : rule.getSubRules()) 
			{
				if(ChackTerm(i,sr.getOperation(),sr.getThreshold()))// return true if prediction right. 
					f+=sr.getAlpa();
				else
					f+=sr.getAlpa()*-1;
			}
			if(f<0) 						
				mistaks++;
			
			
		}
		
		return mistaks;
	}*/