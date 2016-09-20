package sorting.concurrent;

import sorting.Util;
import sorting.sequential.SequentialRandomQuicksort;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ConcurrentRandomQuicksortThreadLimited<T extends Comparable<T>> extends SequentialRandomQuicksort<T> {

    private ForkJoinPool executor;

    public ConcurrentRandomQuicksortThreadLimited() {
        executor = new ForkJoinPool();
    }

    public ConcurrentRandomQuicksortThreadLimited(int nThreads) {
        executor = new ForkJoinPool(nThreads);
    }

    @Override
    protected void sort(T[] array, int leftIndex, int rightIndex) {
        executor.invoke(new RandomQuicksortTask(array, leftIndex, rightIndex));
    }

    @SuppressWarnings("unchecked")
    private class RandomQuicksortTask extends RecursiveAction {
        private T[] array;
        private int leftIndex, rightIndex;

        private RandomQuicksortTask(T[] array, int leftIndex, int rightIndex) {
            this.array = array;
            this.leftIndex = leftIndex;
            this.rightIndex = rightIndex;
        }

        @Override
        protected void compute() {
            if(leftIndex >= rightIndex) {
                return;
            }
            int pivotIndex = leftIndex + rand.nextInt(rightIndex + 1 - leftIndex);

            Util.swap(array, pivotIndex, rightIndex);
            int partitionIndex = partition(array, leftIndex, rightIndex);

            RandomQuicksortTask lTask = new RandomQuicksortTask(array, leftIndex, partitionIndex - 1);
            RandomQuicksortTask rTask = new RandomQuicksortTask(array, partitionIndex + 1, rightIndex);

            invokeAll(lTask, rTask);
        }
    }
}
