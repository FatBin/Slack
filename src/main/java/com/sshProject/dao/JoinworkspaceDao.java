package com.sshProject.dao;

import com.sshProject.entity.Joinworkspace;

import java.util.List;

public interface JoinworkspaceDao {
    boolean updateJoinworkspaceByStatus(String status, int joinwsid);

    int addJoinworkspace(Joinworkspace joinworkspace);

    List<Joinworkspace> findByInviteeid(int inviteeid);

    Joinworkspace findByjoinwsid(int joinwsid);
}
