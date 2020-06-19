package org.arturkufa.mailer;

import org.springframework.stereotype.Component;

@Component
public class ArgHolder {
    int id, timeout;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
