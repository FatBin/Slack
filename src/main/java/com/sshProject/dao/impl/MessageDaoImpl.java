package com.sshProject.dao.impl;

import com.sshProject.dao.MessageDao;
import com.sshProject.entity.Message;
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
public class MessageDaoImpl implements MessageDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public int saveMessage(Message message) {
        try {
            sessionFactory.getCurrentSession().save(message);
            return message.getMessageid();
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Message> findByChannelid(int channelid) {
        try {
            String hql = "from Message where channelid= :channelid";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setInteger("channelid",channelid);
            List<Message> result = query.list();
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<Message>();
        }
    }
}
