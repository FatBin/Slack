package com.sshProject.dao.impl;

import com.sshProject.dao.AdminDao;
import com.sshProject.entity.Admin;
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
public class AdminDaoImpl implements AdminDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public boolean addAdmin(Admin admin) {
        try {
            sessionFactory.getCurrentSession().persist(admin);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Admin> findByWorkspaceid(int workspaceid) {
        try {
            String hql = "from Admin where workspaceid= :workspaceid";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setInteger("workspaceid",workspaceid);
            List<Admin> result = query.list();
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<Admin>();
        }
    }

    @Override
    public Admin findByUseridAndWorkspaceid(int userid, int workspaceid) {
        try{
            String hql = "from Admin where userid = :userid and workspaceid= :workspaceid";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setInteger("userid",userid);
            query.setInteger("workspaceid",workspaceid);
            Object object = query.uniqueResult();
            if (object==null){
                return null;
            }else {
                return  (Admin) object;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
