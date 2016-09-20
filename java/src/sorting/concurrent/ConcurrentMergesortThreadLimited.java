package sorting.concurrent;

import sorting.sequential.SequentialMergesort;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ConcurrentMergesortThreadLimited<T extends Comparable<T>> extends SequentialMergesort<T> {

    ThreadPoolExecutor executor;

    public ConcurrentMergesortThreadLimited() {
        this(Runtime.getRuntime().availableProcessors());
    }

    public ConcurrentMergesortThreadLimited(int nThreads) {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(nThreads);
    }

    @Override
    protected void sort(T[] array, int leftIndex, int rightIndex) {
        if (leftIndex == rightIndex) {
            return;
        }
        int pointMid = (rightIndex + 1 - leftIndex) / 2;

        T[] leftArray = Arrays.copyOfRange(array, leftIndex, leftIndex + pointMid);
        T[] rightArray = Arrays.copyOfRange(array, leftIndex + pointMid, rightIndex + 1);

        executor.execute(
                () -> sort(leftArray, 0, leftArray.length - 1)
        );
//        Thread leftThread = new Thread(
//                () -> sort(leftArray, 0, leftArray.length-1, cores / 2)
//        );
//        Thread rightThread = new Thread(
//                () -> sort(rightArray, 0, rightArray.length-1, cores - cores / 2)
//        );
        executor.execute(
                () -> sort(rightArray, 0, rightArray.length - 1)
        );
//        leftThread.run();
//        rightThread.run();
        T[] arrayTemp = merge(leftArray, rightArray);

        System.arraycopy(arrayTemp, 0, array, leftIndex, arrayTemp.length);
    }

    public void shutDownThreadPoll() {
        executor.shutdown();
    }
}
