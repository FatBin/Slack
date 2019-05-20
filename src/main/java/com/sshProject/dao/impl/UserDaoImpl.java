package com.sshProject.dao.impl;

import com.sshProject.dao.UserDao;
import com.sshProject.entity.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;


import java.util.List;

@Repository
@Transactional(isolation = Isolation.SERIALIZABLE)
public class UserDaoImpl implements UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean addUser(User user) {
        try {
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            sessionFactory.getCurrentSession().persist(user);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User findUserByemail(String email) {
        try{
            String hql = "from User where email = :email";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setString("email",email);
            Object object = query.uniqueResult();
            if (object==null){
                return null;
            }else {
                return  (User) object;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User findUserById(int userid) {
        try{
            String hql = "from User where userid = :userid";
            Query query = sessionFactory.getCurrentSession().createQuery(hql);
            query.setInteger("userid",userid);
            Object object = query.uniqueResult();
            if (object==null){
                return null;
            }else {
                return  (User) object;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int saveUser(User user) {
        try {
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            sessionFactory.getCurrentSession().save(user);
            return user.getUserid();
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }
}
