package sorting.concurrent;

import org.junit.Before;
import org.junit.Test;
import sorting.sequential.SequentialRandomQuicksort;

import static org.junit.Assert.assertArrayEquals;

public class TestConcurrentRandomQuicksort {
	
	private ConcurrentRandomQuicksort<Integer> quicksort;
	
	@Before
	public void createObject() {
		quicksort = new ConcurrentRandomQuicksort<>();
	}

	@Test
	public void testSort() {
		Integer[] array = {40,245,-2,0,15,8,25,-7};
		Integer[] arrayOrdered = {-7,-2,0,8,15,25,40,245};

		quicksort.sort(array);
		assertArrayEquals(arrayOrdered, array);
	}
	
	@Test
	public void testSortIndex() {
		Integer[] array = {40,245,-2,0,15,8,25,-7};
		Integer[] arrayOrdered = {40,245,-2,-7,0,8,15,25};

		quicksort.sort(array, 3, 7);
		assertArrayEquals(arrayOrdered, array);
	}
}
