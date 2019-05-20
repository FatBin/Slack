package com.sshProject.dao;

import com.sshProject.entity.Workspace;

import java.util.List;

public interface WorkspaceDao {
    boolean addWorkspace(Workspace workspace);

    List<Workspace> findWorkspaceByUserid(int userid);

    Workspace findByWorkspaceid(int workspaceid);

    int saveWorkspace(Workspace workspace);
}
