package com.sshProject.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="channel")
public class Channel {
    @Id
    @GeneratedValue
    private int channelid;
    @Column
    private String name;
    @Column
    private int creatorid;
    @Column
    private int workspaceid;
    @Column
    private String type;
    @Column
    private Date createdate;

    public int getChannelid() {
        return channelid;
    }

    public void setChannelid(int channelid) {
        this.channelid = channelid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(int creatorid) {
        this.creatorid = creatorid;
    }

    public int getWorkspaceid() {
        return workspaceid;
    }

    public void setWorkspaceid(int workspaceid) {
        this.workspaceid = workspaceid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}
