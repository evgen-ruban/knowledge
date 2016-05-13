package com.base.knowledge.dao.hibernate;


import com.base.knowledge.dao.QuestionDao;
import com.base.knowledge.domain.Question;
import com.base.knowledge.domain.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class HibernateQuestionDao implements QuestionDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateQuestionDao.class);

    @Override
    public Question getQuestionById(int id) {
        Session session = null;
        Question question = null;
        try {
            LOGGER.trace("Open connection to DATABASE");
            session = sessionFactory.openSession();
            question = session.get(Question.class, id);
        } catch (Exception e) {
            LOGGER.warn("PROBLEM in getQuestionById in class: " + HibernateQuestionDao.class, e);
        } finally {
            if (session != null && session.isOpen()) {
                LOGGER.trace("CLOSE connection");
                session.close();
            }
        }
        return question;
    }

    @Override
    public List<Question> getAllQuestionByUser(User user) {
        Session session = null;
        List<Question> questions = null;
        try {
            LOGGER.trace("Open connection to DATABASE");
            session = sessionFactory.openSession();
            if(user != null){
                questions = session.createQuery("FROM Question WHERE owner = :owner").setParameter("owner", user).list();
            } else {
                LOGGER.warn("PROBLEM in HibernateQuestionDao.getAllQuestionByUser: USER IS NULL");
            }
        } catch (Exception e) {
            LOGGER.warn("PROBLEM in getAllQuestionByUser in class: " + HibernateQuestionDao.class, e);
        } finally {
            if (session != null && session.isOpen()) {
                LOGGER.trace("CLOSE connection");
                session.close();
            }
        }
        return questions;
    }

    @Override
    public void putQuestion(Question question) {
        Session session = null;
        try {
            LOGGER.trace("Open connection to DATABASE");
            session = sessionFactory.openSession();
            session.save(question);
        } catch (Exception e) {
            LOGGER.warn("PROBLEM in putQuestion in class: " + HibernateQuestionDao.class, e);
        } finally {
            if (session != null && session.isOpen()) {
                LOGGER.trace("CLOSE connection");
                session.close();
            }
        }
    }

    @Override
    public void deleteQuestionById(int id) {
        Session session = null;
        try {
            LOGGER.trace("Open connection to DATABASE");
            session = sessionFactory.openSession();
            Query query = session.createQuery("delete Question where id = :ID");
            query.setParameter("ID", id);
            query.executeUpdate();
        } catch (Exception e) {
            LOGGER.warn("PROBLEM in deleteQuestionById in class: " + HibernateQuestionDao.class, e);
        } finally {
            if (session != null && session.isOpen()) {
                LOGGER.trace("CLOSE connection");
                session.close();
            }
        }
    }
}
