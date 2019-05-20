package com.sshProject.dao;

import com.sshProject.entity.Message;

import java.util.List;

public interface MessageDao {
    int saveMessage(Message message);

    List<Message> findByChannelid(int channelid);
}
