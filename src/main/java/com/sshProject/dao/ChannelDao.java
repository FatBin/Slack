package com.sshProject.dao;

import com.sshProject.entity.Channel;

import java.util.List;

public interface ChannelDao {
    int saveChannel(Channel channel);

    Channel findByChannelid(int channelid);

    List<Channel> findByWorkspaceid(int workspaceid);
}
