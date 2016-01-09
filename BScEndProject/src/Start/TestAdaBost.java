package Start;

import java.util.ArrayList;

import Algorithm.AdaBoost;
import Objects.Item;

public class TestAdaBost {

	public static void main(String[] args) {

		ArrayList<Item> test = new ArrayList<Item>();
		test.add(new Item(new Integer[]{0,1,1}));
		test.add(new Item(new Integer[]{1,1,1}));
		test.add(new Item(new Integer[]{2,1,1}));
		test.add(new Item(new Integer[]{3,1,-1}));
		test.add(new Item(new Integer[]{4,1,-1}));
		test.add(new Item(new Integer[]{5,1,-1}));
		test.add(new Item(new Integer[]{6,1,1}));
		test.add(new Item(new Integer[]{7,1,1}));
		test.add(new Item(new Integer[]{8,1,1}));
		test.add(new Item(new Integer[]{8,0,-1}));
		
		new AdaBoost(test, 9);
	}

}
