package com.sshProject.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="inworkspace")
public class Inworkspace implements Serializable {

    @Id
    private int workspaceid;

    @Id
    private int participantid;

    public int getWorkspaceid() {
        return workspaceid;
    }

    public void setWorkspaceid(int workspaceid) {
        this.workspaceid = workspaceid;
    }

    public int getParticipantid() {
        return participantid;
    }

    public void setParticipantid(int participantid) {
        this.participantid = participantid;
    }
}
