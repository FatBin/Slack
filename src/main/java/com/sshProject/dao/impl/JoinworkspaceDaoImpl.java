package com.sshProject.dao.impl;

import com.sshProject.dao.JoinworkspaceDao;
import com.sshProject.entity.Joinworkspace;
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
public class JoinworkspaceDaoImpl implements JoinworkspaceDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean updateJoinworkspaceByStatus(String status, int joinwsid) {
        try {
            String hql = "update Joinworkspace j set j.status=:status where j.joinwsid=:joinwsid";
            Query query =sessionFactory.getCurrentSession().createQuery(hql);
            query.setString("status",status);
            query.setInteger("joinwsid",joinwsid);
            query.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int addJoinworkspace(Joinworkspace joinworkspace) {
        try {
            sessionFactory.getCurrentSession().save(joinworkspace);
            return joinworkspace.getJoinwsid();
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Joinworkspace> findByInviteeid(int inviteeid) {
        try {
            String hql = "from Joinworkspace where inviteeid= :inviteeid";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setInteger("inviteeid",inviteeid);
            List<Joinworkspace> result = query.list();
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<Joinworkspace>();
        }
    }

    @Override
    public Joinworkspace findByjoinwsid(int joinwsid) {
        try{
            String hql = "from Joinworkspace where joinwsid = :joinwsid";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setInteger("joinwsid",joinwsid);
            Object object = query.uniqueResult();
            if (object==null){
                return null;
            }else {
                return  (Joinworkspace) object;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
