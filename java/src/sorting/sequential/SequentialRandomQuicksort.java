package sorting.sequential;

import sorting.SortingImpl;
import sorting.Util;

import java.util.Random;

public class SequentialRandomQuicksort<T extends Comparable<T>> extends SortingImpl<T> {

    protected Random rand = new Random();

	@Override
	protected void sort(T[] array, int leftIndex, int rightIndex) {
		if(leftIndex >= rightIndex) {
			return;
		}
		int pivotIndex = leftIndex + rand.nextInt(rightIndex - leftIndex + 1);

        Util.swap(array, pivotIndex, rightIndex);
		int partitionIndex = partition(array, leftIndex, rightIndex);

		sort(array, leftIndex, partitionIndex - 1);
		sort(array, partitionIndex + 1, rightIndex);
	}

	protected int partition(T[] array, int leftIndex, int rightIndex) {
		int i = leftIndex;
		int j = rightIndex - 1;
		T pivot = array[rightIndex];

        while (true) {
            while (array[i].compareTo(pivot) < 0)
                i++;
            while (j > 0 && array[j].compareTo(pivot) > 0)
                j--;

            if (i >= j)
				break;
			else
				Util.swap(array, i, j);
		}
		Util.swap(array, i, rightIndex);
		return i;
	}
}
