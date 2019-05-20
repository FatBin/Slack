package com.sshProject.dao;

import com.sshProject.entity.Inworkspace;

import java.util.List;

public interface InworkspaceDao {
    boolean addInworkspace(Inworkspace inworkspace);

    List<Inworkspace> findByuserid(int userid);

    Inworkspace findByuseridAndworkspaceid(int userid, int workspaceid);

    List<Inworkspace> findByWorkspaceid(int workspaceid);
}
