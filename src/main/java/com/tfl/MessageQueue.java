package com.tfl;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Happiness on 2017/5/17.
 */

public class MessageQueue {

    private Message[] messages;
    private int pullIndex;
    private int takeIndex;
    private int count;

    private Condition produce;//生产
    private Condition consumption;//消费

    private Lock lock;

    public MessageQueue() {
        messages = new Message[50];
        //创建重用锁
        lock = new ReentrantLock();
        produce = lock.newCondition();
        consumption = lock.newCondition();
    }

    /**
     * 入队
     *
     * @param message
     */
    void enqueueMessage(Message message) {
        try {
            lock.lock();
            //如果队列满了,就要阻塞,停止生产
            if (count == messages.length) {
                try {
                    produce.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            messages[++pullIndex == messages.length ? 0 : pullIndex] = message;
            count++;
            //通知消费继续
            consumption.signal();
        } finally {
            lock.unlock();
        }
    }


    /**
     * 出队
     *
     * @return
     */
    Message next() {
        Message message = null;
        try {
            lock.lock();
            //如果队列中没有消息了，那么就要阻塞，停止消费
            if (count == 0) {
                try {
                    consumption.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            message = messages[++takeIndex == messages.length ? 0 : takeIndex];
            count--;
            //通知可以继续生产
            produce.signal();
        } finally {
            lock.unlock();
        }

        return message;
    }

}
