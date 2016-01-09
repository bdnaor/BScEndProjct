package Objects;

public class Node  implements Comparable<Node>
{
	/*	Variables	*/
	private Integer attValue;
	private Integer ItemResult;

	/*	Constructor	*/
	public Node(Integer attVal, Integer itemResult)
	{
		this.attValue = attVal;
		this.ItemResult = itemResult;
	}

	/*	Getters	*/	
	public Integer getAttValue() {
		return attValue;
	}
	public Integer getItemResult() {
		return ItemResult;
	}

	/*	Setters	*/
	public void setAttValue(Integer attValue) {
		this.attValue = attValue;
	}
	public void setItemResult(Integer itemResult) {
		ItemResult = itemResult;
	}
	
	/*compareTo	Method	*/	
	public int compareTo(Node o1) {
		
		if(o1.getAttValue()<this.getAttValue())return 1;
		if(o1.getAttValue()>this.getAttValue())return -1;
		else return 0;
	}
}


//	int compareTo(Node o1,Node o2)
//	{
//		if(o1.getAttValue()<o1.getAttValue())return -1;
//		if(o1.getAttValue()>o1.getAttValue())return 1;
//		else return 0;
//	}
//
//	@Override