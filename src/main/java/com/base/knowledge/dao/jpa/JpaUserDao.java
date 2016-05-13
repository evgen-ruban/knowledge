package com.base.knowledge.dao.jpa;


import com.base.knowledge.dao.UserDao;
import com.base.knowledge.domain.User;
import com.base.knowledge.exception.KnowledgeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class JpaUserDao implements UserDao {

    @PersistenceContext
    EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaUserDao.class);

    @Override
    public User getUserById(int id) {
        try{
            return entityManager.find(User.class, id);
        } catch (Exception e) {
            LOGGER.info("Some problem in JpaUserDao.getUserById", e);
        }
        return null;
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password) {
        try {
           return (User)entityManager.createQuery("FROM User where login =:login and password =:password").
                    setParameter("login", login).setParameter("password", password).getSingleResult();
        } catch (Exception e) {
            LOGGER.info("Some problem in JpaUserDao.getUserByLoginAndPassword", e);
        }
        return null;
    }

    @Override
    public void putUser(User user) throws KnowledgeException {
        try {
            entityManager.persist(user);
        } catch (Exception e) {
            throw new KnowledgeException();
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            entityManager.remove(getUserById(user.getId()));
        } catch (Exception e) {
            LOGGER.info("Some problem in JpaUserDao.remove", e);
        }
    }
}
