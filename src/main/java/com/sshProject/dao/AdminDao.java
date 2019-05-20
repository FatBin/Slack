package com.sshProject.dao;

import com.sshProject.entity.Admin;

import java.util.List;

public interface AdminDao {
   boolean addAdmin(Admin admin);

   List<Admin> findByWorkspaceid(int workspaceid);

   Admin findByUseridAndWorkspaceid(int userid, int workspaceid);
}
