package com.sshProject.dao.impl;

import com.sshProject.dao.InworkspaceDao;
import com.sshProject.entity.Inworkspace;
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
public class InworkspaceImpl implements InworkspaceDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean addInworkspace(Inworkspace inworkspace) {
        try {
            sessionFactory.getCurrentSession().persist(inworkspace);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Inworkspace> findByuserid(int userid) {
        try {
            String hql = "from Inworkspace where participantid= :participantid";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setInteger("participantid",userid);
            List<Inworkspace> result = query.list();
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<Inworkspace>();
        }
    }

    @Override
    public Inworkspace findByuseridAndworkspaceid(int userid, int workspaceid) {
        try{
            String hql = "from Inworkspace where participantid = :participantid and workspaceid= :workspaceid";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setInteger("participantid",userid);
            query.setInteger("workspaceid",workspaceid);
            Object object = query.uniqueResult();
            if (object==null){
                return null;
            }else {
                return  (Inworkspace) object;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Inworkspace> findByWorkspaceid(int workspaceid) {
        try {
            String hql = "from Inworkspace where workspaceid= :workspaceid";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setInteger("workspaceid",workspaceid);
            List<Inworkspace> result = query.list();
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<Inworkspace>();
        }
    }
}
