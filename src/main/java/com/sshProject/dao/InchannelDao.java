package com.sshProject.dao;

import com.sshProject.entity.Inchannel;

import java.util.List;

public interface InchannelDao {
    boolean addInchannel(Inchannel inchannel);

    List<Inchannel> findByParticipantid(int participantid);
}
