package com.tfl;

public class Handler {

    Looper mLooper;

    MessageQueue mQueue;

    public Handler() {
        mLooper = Looper.myLooper();
        mQueue = mLooper.mQueue;
    }

    public void dispatchMessage(Message msg) {
        handleMessage(msg);
    }


    public void handleMessage(Message msg) {
    }

    public void sendMessage(Message msg) {
        //目的地即出发地
        msg.target = this;
        mQueue.enqueueMessage(msg);
    }
}
