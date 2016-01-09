package Objects;

import javax.swing.JOptionPane;


public class Item 
{
	/*	Variables	*/
	private Integer[] Attributes;
	
	/*	Constructor	*/
	public Item(Integer[] Attributes) //, boolean result
	{		
		setAttributes(Attributes);		
	}

	/*	Getters	*/	
	public Integer[] getAttributes() {
		return Attributes;
	}
	public void setAttributes(Integer[] Attributes) 
	{		
		this.Attributes = Attributes;		
		if(Attributes[Attributes.length-1]==0)
			Attributes[Attributes.length-1]=-1;
		if(Attributes[Attributes.length-1]!=1 && Attributes[Attributes.length-1]!=-1)
			JOptionPane.showMessageDialog(null, "Result diffrent from 1/-1!");		
	}

	/*	equals Method*/
	@Override
    public boolean equals(Object obj) 
	{
        if(obj != null && obj instanceof Item) 
        {
        	Item s = (Item)obj;
        	for(int i= 0 ; i<Attributes.length ; i++)
        	{
        		if(Attributes[i]!=s.getAttributes()[i])
        			return false;
        	}
        		
        }
        return true;
    }
	
	/*	toString Method*/
	@Override
    public String toString()
    {
		String str = new String();
		for(int i=0;i< this.getAttributes().length-2 ; i++)
		{
			str+=this.getAttributes()[i]+",";
		}
		str+=this.getAttributes()[this.getAttributes().length-1];
    	
		return str;
    }
}
