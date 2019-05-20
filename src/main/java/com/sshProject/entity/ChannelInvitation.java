package com.sshProject.entity;

public class ChannelInvitation {
    private int joinchannelid;
    private String invitorname;
    private String name;

    public int getJoinchannelid() {
        return joinchannelid;
    }

    public void setJoinchannelid(int joinchannelid) {
        this.joinchannelid = joinchannelid;
    }

    public String getInvitorname() {
        return invitorname;
    }

    public void setInvitorname(String invitorname) {
        this.invitorname = invitorname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
