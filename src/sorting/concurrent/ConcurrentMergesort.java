package sorting.concurrent;

import sorting.sequential.SequentialMergesort;

import java.util.Arrays;

public class ConcurrentMergesort<T extends Comparable<T>> extends SequentialMergesort<T> {

    @Override
    protected void sort(T[] array, int leftIndex, int rightIndex) {
        if(leftIndex == rightIndex) {
            return;
        }
        int pointMid = (rightIndex + 1 - leftIndex) / 2;

        T[] leftArray = Arrays.copyOfRange(array, leftIndex, leftIndex+pointMid);
        T[] rightArray = Arrays.copyOfRange(array, leftIndex+pointMid, rightIndex + 1);

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                sort(leftArray);
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                sort(rightArray);
            }
        });
        t1.run();
        t2.run();
        T[] arrayTemp = merge(leftArray, rightArray);

        for (int i=0; i<arrayTemp.length; i++) {
            array[leftIndex+i] = arrayTemp[i];
        }
    }
}
