package sorting.concurrent;

import sorting.sequential.SequentialMergesort;

import java.util.Arrays;

public class ConcurrentMergesortThreadLimited<T extends Comparable<T>> extends SequentialMergesort<T> {

    private static final int MIN_THREAD_LEN = 1024;

    @Override
    protected void sort(T[] array, int leftIndex, int rightIndex) {
        sort(array, leftIndex, rightIndex, Runtime.getRuntime().availableProcessors());
    }

    private void sort(T[] array, int leftIndex, int rightIndex, int cores) {
        if (rightIndex - leftIndex + 1 <= MIN_THREAD_LEN || cores < 2) {
            super.sort(array, leftIndex, rightIndex);
        }
        int pointMid = (rightIndex - leftIndex + 1) / 2;

        T[] leftArray = Arrays.copyOfRange(array, leftIndex, leftIndex + pointMid);
        T[] rightArray = Arrays.copyOfRange(array, leftIndex + pointMid, rightIndex + 1);

        Thread leftThread = new Thread(
                () -> sort(leftArray, 0, leftArray.length-1, cores/2)
        );
        Thread rightThread = new Thread(
                () -> sort(rightArray, 0, rightArray.length-1, cores - cores/2)
        );
        leftThread.run();
        rightThread.run();
        T[] arrayTemp = merge(leftArray, rightArray);

        System.arraycopy(arrayTemp, 0, array, leftIndex, arrayTemp.length);
    }
}
