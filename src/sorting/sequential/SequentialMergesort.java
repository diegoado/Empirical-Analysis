package sorting.sequential;

import java.util.Arrays;
import sorting.SortingImpl;

public class SequentialMergesort<T extends Comparable<T>> extends SortingImpl<T> {

	@Override
	protected void sort(T[] array,int leftIndex, int rightIndex) {
		if(leftIndex == rightIndex) {
			return;
		}
		int pointMid = (rightIndex + 1 - leftIndex) / 2;

		T[] leftArray = Arrays.copyOfRange(array, leftIndex, leftIndex+pointMid);
		T[] rightArray = Arrays.copyOfRange(array, leftIndex+pointMid, rightIndex+1);

		sort(leftArray);
		sort(rightArray);

		T[] arrayTemp = merge(leftArray, rightArray);
		for (int i = 0; i < arrayTemp.length; i++) {
			array[leftIndex + i] = arrayTemp[i];
		}
	}

	@SuppressWarnings("unchecked")
	protected T[] merge(T[] leftArray, T[] rightArray) {
		int tempIndex = 0;
		int leftIndex = 0; int rightIndex = 0;

		Comparable<T>[] auxArray = new Comparable[leftArray.length + rightArray.length];

		while (leftArray.length > leftIndex && rightArray.length > rightIndex) {
			if (leftArray[leftIndex].compareTo(rightArray[rightIndex]) < 0) {
				auxArray[tempIndex] = leftArray[leftIndex];
				leftIndex++;
			} else {
				auxArray[tempIndex] = rightArray[rightIndex];
				rightIndex++;
			}
			tempIndex++;
		}
		while (leftIndex < leftArray.length) {
			auxArray[tempIndex] = leftArray[leftIndex];
			tempIndex++;
			leftIndex++;
		}
		while (rightIndex < rightArray.length) {
			auxArray[tempIndex] = rightArray[rightIndex];
			tempIndex++;
			rightIndex++;
		}
		return (T[]) auxArray;
	}
}
