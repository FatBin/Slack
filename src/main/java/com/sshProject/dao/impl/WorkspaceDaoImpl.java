package com.sshProject.dao.impl;

import com.sshProject.dao.WorkspaceDao;
import com.sshProject.entity.Workspace;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(isolation = Isolation.SERIALIZABLE)
public class WorkspaceDaoImpl implements WorkspaceDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean addWorkspace(Workspace workspace) {
        try {
            sessionFactory.getCurrentSession().persist(workspace);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Workspace> findWorkspaceByUserid(int userid) {
        try {
            String hql = "from Workspace where userid= :userid";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            System.out.println(userid+"ffff");
            query.setInteger("userid",userid);
            List<Workspace> result = query.list();
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<Workspace>();
        }

    }

    @Override
    public Workspace findByWorkspaceid(int workspaceid) {
        try{
            String hql = "from Workspace where workspaceid = :workspaceid";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setInteger("workspaceid",workspaceid);
            Object object = query.uniqueResult();
            if (object==null){
                return null;
            }else {
                return  (Workspace) object;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int saveWorkspace(Workspace workspace) {
        try {
            sessionFactory.getCurrentSession().persist(workspace);
            return workspace.getWorkspaceid();
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
}
