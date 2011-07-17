package com.leetr.core;

import java.util.LinkedList;

/**
 * Created By: Denis Smirnov <denis@deesastudio.com>
 * <p/>
 * Date: 11-05-07
 * Time: 4:51 PM
 */
public class WorkQueue {
    private final int mNumWorkers;
    private final PoolWorker[] mWorkers;
    private final LinkedList<Runnable> mQueue;

    public WorkQueue(int numWorkers) {
        mNumWorkers = numWorkers;
        mWorkers = new PoolWorker[mNumWorkers];
        mQueue = new LinkedList<Runnable>();

        for (int i = 0; i < mNumWorkers; i++) {
            mWorkers[i] = new PoolWorker();
            mWorkers[i].start();
        }
    }

    public void postRunnable(Runnable r) {
        synchronized (mQueue) {
            mQueue.addLast(r);
            mQueue.notify();
        }
    }

    private class PoolWorker extends Thread {
        public void run() {
            Runnable r;

            while (true) {
                synchronized (mQueue) {
                    while (mQueue.isEmpty()) {
                        try {
                            mQueue.wait();
                        } catch (InterruptedException ignored) {
                        }
                    }

                    r = (Runnable) mQueue.removeFirst();
                }

                try {
                    r.run();
                } catch (RuntimeException e) {

                }
            }
        }
    }
}

