package com.sshProject.entity;

public class WorkspaceInvitation {
    private int joinwsid;

    private String invitorname;

    private String workspacename;

    private String description;


    public int getJoinwsid() {
        return joinwsid;
    }

    public void setJoinwsid(int joinwsid) {
        this.joinwsid = joinwsid;
    }

    public String getInvitorname() {
        return invitorname;
    }

    public void setInvitorname(String invitorname) {
        this.invitorname = invitorname;
    }

    public String getWorkspacename() {
        return workspacename;
    }

    public void setWorkspacename(String workspacename) {
        this.workspacename = workspacename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
