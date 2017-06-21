package com.tfl;

/**
 * Created by Happiness on 2017/5/17.
 */

public class Message {

    public int what;

    public Object obj;

    public Handler target;

    public Message() {

    }

    @Override
    public String toString() {
        return obj.toString();
    }
}
