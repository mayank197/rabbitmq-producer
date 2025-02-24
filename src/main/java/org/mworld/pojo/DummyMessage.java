package org.mworld.pojo;

import java.io.Serializable;

public class DummyMessage implements Serializable {

    private String content;
    private int publishOrder;

    public DummyMessage(String content, int publishOrder) {
        this.content = content;
        this.publishOrder = publishOrder;
    }

    @Override
    public String toString() {
        return "DummyMessage{" +
                "content='" + content + '\'' +
                ", publishOrder=" + publishOrder +
                '}';
    }
}
