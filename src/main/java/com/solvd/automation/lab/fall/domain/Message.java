package com.solvd.automation.lab.fall.domain;

import java.util.Date;

public class Message extends Entity {
    private boolean isDraft;
    private Date dispatchTime;
    private String content;
    private int clientFromId;
    private int clientToId;

    public Message(boolean isDraft, String content, int clientFromId, int clientToId) {
        this.isDraft = isDraft;
        this.dispatchTime = new Date();
        this.content = content;
        this.clientFromId = clientFromId;
        this.clientToId = clientToId;
    }

    public Message() {
    }

    public boolean isDraft() {
        return isDraft;
    }

    public void setDraft(boolean draft) {
        isDraft = draft;
    }

    public Date getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(Date dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getClientFromId() {
        return clientFromId;
    }

    public void setClientFromId(int clientFromId) {
        this.clientFromId = clientFromId;
    }

    public int getClientToId() {
        return clientToId;
    }

    public void setClientToId(int clientToId) {
        this.clientToId = clientToId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (isDraft != message.isDraft) return false;
        if (clientFromId != message.clientFromId) return false;
        if (clientToId != message.clientToId) return false;
        if (dispatchTime != null ? !dispatchTime.equals(message.dispatchTime) : message.dispatchTime != null)
            return false;
        return content != null ? content.equals(message.content) : message.content == null;
    }

    @Override
    public int hashCode() {
        int result = (isDraft ? 1 : 0);
        result = 31 * result + (dispatchTime != null ? dispatchTime.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + clientFromId;
        result = 31 * result + clientToId;
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Message{");
        sb.append("id=").append(super.getId());
        sb.append(", isDraft=").append(isDraft);
        sb.append(", dispatchTime=").append(dispatchTime);
        sb.append(", content='").append(content).append('\'');
        sb.append(", clientFromId=").append(clientFromId);
        sb.append(", clientToId=").append(clientToId);
        sb.append('}');
        return sb.toString();
    }
}
