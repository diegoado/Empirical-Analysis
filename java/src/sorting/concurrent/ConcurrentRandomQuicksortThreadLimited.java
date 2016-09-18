package sorting.concurrent;

import sorting.Util;
import sorting.sequential.SequentialRandomQuicksort;

public class ConcurrentRandomQuicksortThreadLimited<T extends Comparable<T>> extends SequentialRandomQuicksort<T> {

    private static final int MIN_THREAD_LEN = 1024;

    @Override
    protected void sort(T[] array, int leftIndex, int rightIndex) {
        sort(array, leftIndex, rightIndex, Runtime.getRuntime().availableProcessors());
    }

    private void sort(T[] array, int leftIndex, int rightIndex, int cores) {
        if (rightIndex + 1 - leftIndex  < MIN_THREAD_LEN || cores < 2) {
            super.sort(array, leftIndex, rightIndex);
            return;
        }
        int pivotIndex = leftIndex + rand.nextInt(rightIndex + 1 - leftIndex);

        Util.swap(array, pivotIndex, rightIndex);
        int partitionIndex = partition(array, leftIndex, rightIndex);

        Thread leftThread = new Thread(
                () -> sort(array, leftIndex, partitionIndex - 1, cores / 2)
        );
        Thread rightThread = new Thread(
                () -> sort(array, partitionIndex + 1, rightIndex, cores - cores / 2)
        );
        leftThread.run();
        rightThread.run();
    }
}
