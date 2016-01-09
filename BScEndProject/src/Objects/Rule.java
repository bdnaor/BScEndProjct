package Objects;

import java.util.ArrayList;

public class Rule 
{
	/*	Variables	*/
	private ArrayList<SubRule> subrulers;
	private Double SuccessPercent; 

	/*	Constructor	*/
	public Rule()
	{
		subrulers = new ArrayList<SubRule>();
		SuccessPercent = new Double(0);
	}

	/*	Methods	*/	
	public void AddSubRule(SubRule sr)
	{
		subrulers.add(sr);
	}
	public Integer getPrediction(Item item) 
	{
		double sum = 0;
		for (SubRule subRule : subrulers) 		
			sum+=subRule.getPrediction(item)*subRule.getAlpa();
		
		if(sum>0)
			return 1;
		else
			return -1;
	}
	@Override
	public String toString() {
		String str = new String();
		for(int i =0; i < subrulers.size();i++) 
		{
			if(i<subrulers.size()-1)
				str+=subrulers.get(i).toString()+"+";
			else
				str+=subrulers.get(i).toString();
			
		}
		return str;
	}

	
	/*	Getters	*/
	public ArrayList<SubRule> getSubRules() {		
		return subrulers;
	}
	public Double getSuccessPercent() {
		return SuccessPercent;
	}

	/*	Setters	*/
	public void setSuccessPercent(Double SuccessPercent) {
		this.SuccessPercent = SuccessPercent;
	}

}
