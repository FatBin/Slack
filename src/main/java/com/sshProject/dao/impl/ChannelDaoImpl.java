package com.sshProject.dao.impl;

import com.sshProject.dao.ChannelDao;
import com.sshProject.entity.Channel;
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
public class ChannelDaoImpl implements ChannelDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public int saveChannel(Channel channel) {
        try {
            sessionFactory.getCurrentSession().save(channel);
            return channel.getChannelid();
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Channel findByChannelid(int channelid) {
        try{
            String hql = "from Channel where channelid = :channelid";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setInteger("channelid",channelid);
            Object object = query.uniqueResult();
            if (object==null){
                return null;
            }else {
                return  (Channel) object;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Channel> findByWorkspaceid(int workspaceid) {
        try {
            String hql = "from Channel where workspaceid= :workspaceid";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setInteger("workspaceid",workspaceid);
            List<Channel> result = query.list();
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<Channel>();
        }
    }
}
