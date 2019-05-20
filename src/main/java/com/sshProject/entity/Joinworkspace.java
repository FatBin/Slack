package com.sshProject.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="joinworkspace")
public class Joinworkspace {
    @Id
    @GeneratedValue
    private int joinwsid;
    @Column
    private int inviterid;
    @Column
    private int inviteeid;
    @Column
    private int workspaceid;
    @Column
    private String status;
    @Column
    private Date invitedate;

    public int getJoinwsid() {
        return joinwsid;
    }

    public void setJoinwsid(int joinwsid) {
        this.joinwsid = joinwsid;
    }

    public int getInviterid() {
        return inviterid;
    }

    public void setInviterid(int inviterid) {
        this.inviterid = inviterid;
    }

    public int getInviteeid() {
        return inviteeid;
    }

    public void setInviteeid(int inviteeid) {
        this.inviteeid = inviteeid;
    }

    public int getWorkspaceid() {
        return workspaceid;
    }

    public void setWorkspaceid(int workspaceid) {
        this.workspaceid = workspaceid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getInvitedate() {
        return invitedate;
    }

    public void setInvitedate(Date invitedate) {
        this.invitedate = invitedate;
    }
}
