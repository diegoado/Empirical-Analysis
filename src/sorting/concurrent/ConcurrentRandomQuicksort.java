package sorting.concurrent;

import sorting.Util;
import sorting.sequential.SequentialRandomQuicksort;

public class ConcurrentRandomQuicksort<T extends Comparable<T>> extends SequentialRandomQuicksort<T> {

    @Override
    protected void sort(T[] array, int leftIndex, int rightIndex) {
        if(leftIndex >= rightIndex) {
            return;
        }
        int pivotIndex = leftIndex + rand.nextInt(rightIndex + 1 - leftIndex);

        Util.swap(array, pivotIndex, rightIndex);
        int partitionIndex = partition(array, leftIndex, rightIndex);

        Thread leftThread = new Thread(
                () -> sort(array, leftIndex, partitionIndex - 1)
        );
        Thread rightThread = new Thread(
                () -> sort(array, partitionIndex + 1, rightIndex)
        );
        leftThread.run();
        rightThread.run();
    }
}
