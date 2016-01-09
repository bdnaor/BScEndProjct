package Objects;

import javax.swing.JOptionPane;

public class SubRule {

	/*	Variables	*/
	private Double ErrorPercent  ;
	private String operation;	//can be "<" or ">"
	private Double threshold;
	private Integer AttributeNumber;
	
	/*	Constructor	*/
	public SubRule(Double errPerc, String operation, Double threshold,Integer AttNum) 
	{
		super();
		this.ErrorPercent = errPerc;
		if(operation.equals("<") || operation.equals(">"))
			this.operation = operation;
		else
			JOptionPane.showMessageDialog(null, "SubRule operation "+operation+" not legal!!");
		
		this.threshold = threshold;
		this.AttributeNumber = AttNum;
	}

	/*	Getters	*/
	public Double getErrorPercent() {
		return ErrorPercent;
	}
	public String getOperation() {
		return operation;
	}
	public Double getThreshold() {
		return threshold;
	}
	public Double getAlpa()
	{
		Double e = getErrorPercent();
		if(e==0)
			return 1.0;
		Double Alpa = 0.5*Math.log((1-e)/e);
		return Alpa;
	}
	public Integer getPrediction(Item item)	
	{
		Integer val = item.getAttributes()[AttributeNumber];
		if(operation.equals("<"))
		{
			if(val<threshold)
				return 1;
			else
				return -1;
		}
		else
		{
			if(val>threshold)
				return 1;
			else
				return -1;
		}
		
	}
	
	/*	Setters	*/
	public void setErrorPercent(Double errorPercent) {
		ErrorPercent = errorPercent;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public void setThreshold(Double threshold) {
		this.threshold = threshold;
	}

	/*	toString Method*/
	@Override
	public String toString() {		
		Double Alpa = getAlpa();
		return Alpa+"I(Att"+AttributeNumber+ operation+threshold+")";				
	}
	
}
