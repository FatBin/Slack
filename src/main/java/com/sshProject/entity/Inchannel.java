package com.sshProject.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="inchannel")
public class Inchannel implements Serializable {
    @Id
    private int channelid;
    @Id
    private int participantid;

    public int getChannelid() {
        return channelid;
    }

    public void setChannelid(int channelid) {
        this.channelid = channelid;
    }

    public int getParticipantid() {
        return participantid;
    }

    public void setParticipantid(int participantid) {
        this.participantid = participantid;
    }
}
