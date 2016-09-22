package sorting.sequential;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class TestSequentialMergesort {
	
	private SequentialMergesort<Integer> mergesort;
	
	@Before
	public void createObject() {
		mergesort = new SequentialMergesort<>();
	}

	@Test
	public void testSort() {
		Integer[] array = {40,245,-2,0,15,8,25,-7};
		Integer[] arrayOrdered = {-7,-2,0,8,15,25,40,245};

		mergesort.sort(array);
		assertArrayEquals(arrayOrdered, array);
	}
	
	@Test
	public void testSortIndex() {
		Integer[] array = {40,245,-2,0,15,8,25,-7};
		Integer[] arrayOrdered = {40,245,-2,-7,0,8,15,25};

		mergesort.sort(array, 3, 7);
		assertArrayEquals(arrayOrdered, array);
	}
}
