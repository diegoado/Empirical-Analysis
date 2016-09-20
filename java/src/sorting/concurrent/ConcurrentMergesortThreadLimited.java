package sorting.concurrent;

import sorting.sequential.SequentialMergesort;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ConcurrentMergesortThreadLimited<T extends Comparable<T>> extends SequentialMergesort<T> {

    private ForkJoinPool executor;

    public ConcurrentMergesortThreadLimited() {
        executor = new ForkJoinPool();
    }

    public ConcurrentMergesortThreadLimited(int nThreads) {
        executor = new ForkJoinPool(nThreads);
    }

    @Override
    protected void sort(T[] array, int leftIndex, int rightIndex) {
        executor.invoke(new MergesortTask(array, leftIndex, rightIndex));
    }

    @SuppressWarnings("unchecked")
    private class MergesortTask extends RecursiveAction {

        private T[] array;
        private int leftIndex, rightIndex;

        private MergesortTask(T[] array, int leftIndex, int rightIndex) {
            this.array = array;
            this.leftIndex = leftIndex;
            this.rightIndex = rightIndex;
        }

        @Override
        protected void compute() {
            if (leftIndex == rightIndex) {
                return;
            }
            int pointMid = (rightIndex + 1 - leftIndex) / 2;

            T[] leftArray = Arrays.copyOfRange(array, leftIndex, leftIndex + pointMid);
            T[] rightArray = Arrays.copyOfRange(array, leftIndex + pointMid, rightIndex + 1);

            MergesortTask lTask = new MergesortTask(leftArray, 0, leftArray.length - 1);
            MergesortTask rTask = new MergesortTask(rightArray, 0, rightArray.length - 1);

            invokeAll(lTask, rTask);
            T[] arrayTemp = merge(leftArray, rightArray);
            System.arraycopy(arrayTemp, 0, array, leftIndex, arrayTemp.length);
        }
    }
}
