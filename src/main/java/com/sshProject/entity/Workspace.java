package com.sshProject.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "workspace")
public class Workspace {
    @Id
    @GeneratedValue
    private int workspaceid;
    @Column
    private int userid;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Date createdate;

    public int getWorkspaceid() {
        return workspaceid;
    }

    public void setWorkspaceid(int workspaceid) {
        this.workspaceid = workspaceid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}
