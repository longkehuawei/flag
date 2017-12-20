package com.longke.flag.event;

/**
 * Created by longke on 2017/12/14.
 */

public class MessageEvent {
    private String message;
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public MessageEvent(String tag, String message) {
        this.message = message;
        this.tag=tag;

    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
