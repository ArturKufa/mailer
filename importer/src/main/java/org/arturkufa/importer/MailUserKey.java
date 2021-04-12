package org.arturkufa.importer;

import java.io.Serializable;
import java.util.Objects;

public class MailUserKey implements Serializable {
    Integer userId;
    Integer senderId;
    Integer receiverId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailUserKey that = (MailUserKey) o;
        return Objects.equals(userId, that.senderId) ||
                Objects.equals(userId, that.receiverId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, senderId, receiverId);
    }
}
