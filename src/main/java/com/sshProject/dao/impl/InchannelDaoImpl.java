package com.sshProject.dao.impl;

import com.sshProject.dao.InchannelDao;
import com.sshProject.entity.Inchannel;
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
public class InchannelDaoImpl implements InchannelDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public boolean addInchannel(Inchannel inchannel) {
        try {
            sessionFactory.getCurrentSession().persist(inchannel);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Inchannel> findByParticipantid(int participantid) {
        try {
            String hql = "from Inchannel where participantid= :participantid";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setInteger("participantid",participantid);
            List<Inchannel> result = query.list();
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<Inchannel>();
        }
    }
}
