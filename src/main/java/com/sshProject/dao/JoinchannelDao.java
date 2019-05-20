package com.sshProject.dao;

import com.sshProject.entity.Joinchannel;

import java.util.List;

public interface JoinchannelDao {
    int saveJoinchannel(Joinchannel joinchannel);

    List<Joinchannel> findByInviteeid(int inviteeid);

    Joinchannel findByJoinchannelid(int joinchannelid);

    boolean updateJoinchannelStatus(String status, int joinchannelid);
}
