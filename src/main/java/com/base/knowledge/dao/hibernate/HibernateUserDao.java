package com.base.knowledge.dao.hibernate;


import com.base.knowledge.dao.UserDao;
import com.base.knowledge.domain.User;
import com.base.knowledge.exception.KnowledgeException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public class HibernateUserDao implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateUserDao.class);

    @Override
    public User getUserByLoginAndPassword(String login, String password) {
        Session session = null;
        User user = null;
        try {
            LOGGER.trace("Open connection to DATABASE");
            session = sessionFactory.openSession();
            Query query = session.createQuery("FROM User WHERE login =:login and password =:password")
                    .setParameter("login", login)
                    .setParameter("password", password);
            user = (User)query.uniqueResult();
        } catch (Exception e) {
            LOGGER.warn("PROBLEM in getUserByLogin in class: " + HibernateUserDao.class, e);
        } finally {
            if (session != null && session.isOpen()) {
                LOGGER.trace("CLOSE connection");
                session.close();
            }
        }
        return user;
    }

    @Override
    public User getUserById(int id) {
        Session session = null;
        User user = null;
        try {
            LOGGER.trace("Open connection to DATABASE");
            session = sessionFactory.openSession();
            user = session.get(User.class, id);
        } catch (Exception e) {
            LOGGER.warn("PROBLEM in getUserById in class: " + HibernateUserDao.class, e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
                LOGGER.trace("CLOSE connection");
            }
        }
        return user;
    }

    @Override
    public void putUser(User user) throws KnowledgeException {
        Session session = null;
        try {
            LOGGER.trace("Open connection to DATABASE");
            session = sessionFactory.openSession();
            session.save(user);
        } catch (Exception e) {
            LOGGER.warn("PROBLEM in putUser in class: " + HibernateUserDao.class, e);
            throw new KnowledgeException();
        } finally {
            if (session != null && session.isOpen()) {
                LOGGER.trace("CLOSE connection");
                session.close();
            }
        }
    }

    @Override
    public void deleteUser(User user) {
        Session session = null;
        try {
            LOGGER.trace("Open connection to DATABASE");
            session = sessionFactory.openSession();
            session.delete(session.get(User.class, user.getId()));
            session.flush();
        } catch (Exception e) {
            LOGGER.warn("PROBLEM in deleteUserById in class: " + HibernateUserDao.class, e);
        } finally {
            if (session != null && session.isOpen()) {
                LOGGER.trace("CLOSE connection");
                session.close();
            }
        }
    }
}
