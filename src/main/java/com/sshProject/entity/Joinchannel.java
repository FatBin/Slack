package com.sshProject.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="joinchannel")
public class Joinchannel {
    @Id
    @GeneratedValue
    private int joinchannelid;

    @Column
    private int channelid;

    @Column
    private int inviteeid;

    @Column
    private Date invitedate;

    @Column
    private String status;

    public int getJoinchannelid() {
        return joinchannelid;
    }

    public void setJoinchannelid(int joinchannelid) {
        this.joinchannelid = joinchannelid;
    }

    public int getChannelid() {
        return channelid;
    }

    public void setChannelid(int channelid) {
        this.channelid = channelid;
    }

    public int getInviteeid() {
        return inviteeid;
    }

    public void setInviteeid(int inviteeid) {
        this.inviteeid = inviteeid;
    }

    public Date getInvitedate() {
        return invitedate;
    }

    public void setInvitedate(Date invitedate) {
        this.invitedate = invitedate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
