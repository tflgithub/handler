package com.tfl;

/**
 * Created by Happiness on 2017/5/17.
 */

public class Looper {

    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();

    MessageQueue mQueue;

    public Looper() {
        mQueue = new MessageQueue();
    }

    public  static void prepare() {

        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper());
    }


    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    //轮询
    public static void loop() {
        final Looper me = myLooper();
        if (me == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        final MessageQueue queue = me.mQueue;
        for (; ; ) {
            Message msg = queue.next(); // might block

            msg.target.dispatchMessage(msg);
        }
    }
}
