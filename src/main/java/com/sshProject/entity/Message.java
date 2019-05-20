package com.sshProject.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="message")
public class Message {

    @Id
    @GeneratedValue
    private int messageid;

    @Column
    private int channelid;
    @Column
    private int senderid;
    @Column
    private String content;
    @Column
    private Date senddate;

    public int getMessageid() {
        return messageid;
    }

    public void setMessageid(int messageid) {
        this.messageid = messageid;
    }

    public int getChannelid() {
        return channelid;
    }

    public void setChannelid(int channelid) {
        this.channelid = channelid;
    }

    public int getSenderid() {
        return senderid;
    }

    public void setSenderid(int senderid) {
        this.senderid = senderid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSenddate() {
        return senddate;
    }

    public void setSenddate(Date senddate) {
        this.senddate = senddate;
    }
}
