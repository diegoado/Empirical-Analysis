package sorting.concurrent;

import sorting.sequential.SequentialMergesort;

import java.util.Arrays;

public class ConcurrentMergesort<T extends Comparable<T>> extends SequentialMergesort<T> {

    @Override
    protected void sort(T[] array, int leftIndex, int rightIndex) {
        if (leftIndex == rightIndex) {
            return;
        }
        int pointMid = (rightIndex + 1 - leftIndex) / 2;

        T[] leftArray = Arrays.copyOfRange(array, leftIndex, leftIndex+pointMid);
        T[] rightArray = Arrays.copyOfRange(array, leftIndex+pointMid, rightIndex + 1);

        Thread leftThread = new Thread(
                () -> sort(leftArray)
        );
        Thread rightThread = new Thread(
                () -> sort(rightArray)
        );
        leftThread.run();
        rightThread.run();
        T[] arrayTemp = merge(leftArray, rightArray);

        System.arraycopy(arrayTemp, 0, array, leftIndex, arrayTemp.length);
    }
}
