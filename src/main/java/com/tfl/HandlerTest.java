package com.tfl;

import java.util.UUID;

/**
 * Created by Administrator on 2017/5/17.
 */

public class HandlerTest {

    public static void main(String[] args) {
        Looper.prepare();
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                System.out.println("receiver msg = [" + msg + "]");
            }
        };
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.what = 1;
                    synchronized (UUID.class) {
                        message.obj = UUID.randomUUID();
                    }
                    handler.sendMessage(message);
                    System.out.println(Thread.currentThread().getName() + " send message:" + message.obj.toString());
                }
            }).start();
        }
        Looper.loop();
    }
}
