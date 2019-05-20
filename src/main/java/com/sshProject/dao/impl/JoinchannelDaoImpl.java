package com.sshProject.dao.impl;

import com.sshProject.dao.JoinchannelDao;
import com.sshProject.entity.Joinchannel;
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
public class JoinchannelDaoImpl implements JoinchannelDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public int saveJoinchannel(Joinchannel joinchannel) {
        try {
            sessionFactory.getCurrentSession().save(joinchannel);
            return joinchannel.getJoinchannelid();
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Joinchannel> findByInviteeid(int inviteeid) {
        try {
            String hql = "from Joinchannel where inviteeid= :inviteeid";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setInteger("inviteeid",inviteeid);
            List<Joinchannel> result = query.list();
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<Joinchannel>();
        }
    }

    @Override
    public Joinchannel findByJoinchannelid(int joinchannelid) {
        try{
            String hql = "from Joinchannel where joinchannelid = :joinchannelid";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setInteger("joinchannelid",joinchannelid);
            Object object = query.uniqueResult();
            if (object==null){
                return null;
            }else {
                return  (Joinchannel) object;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean updateJoinchannelStatus(String status, int joinchannelid) {
        try {
            String hql = "update Joinchannel j set j.status=:status where j.joinchannelid=:joinchannelid";
            Query query =sessionFactory.getCurrentSession().createQuery(hql);
            query.setString("status",status);
            query.setInteger("joinchannelid",joinchannelid);
            query.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
