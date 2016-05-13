package com.base.knowledge.dao.jpa;


import com.base.knowledge.dao.QuestionDao;
import com.base.knowledge.domain.Question;
import com.base.knowledge.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class JpaQuestionDao implements QuestionDao {


    @PersistenceContext
    EntityManager entityManager;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(JpaQuestionDao.class);

    @Override
    public Question getQuestionById(int id) {
        try {
            return entityManager.find(Question.class, id);
        } catch (Exception e) {
            LOGGER.info("Some problem in JpaQuestionDao.getQuestionById", e);
        }
        return null;
    }

    @Override
    public List<Question> getAllQuestionByUser(User user) {
        try {
            return entityManager.
                    createQuery("FROM Question WHERE owner = :owner").setParameter("owner", user).getResultList();
        } catch (Exception e) {
            LOGGER.info("Some problem in JpaQuestionDao.getAllQuestionByUser", e);
        }
        return null;
    }

    @Override
    public void putQuestion(Question question) {
        try {
            entityManager.persist(question);
        } catch (Exception e) {
            LOGGER.info("Some problem in JpaQuestionDao.putQuestion", e);
        }
    }

    @Override
    public void deleteQuestionById(int id) {
        try {
            entityManager.remove(getQuestionById(id));
        } catch (Exception e) {
            LOGGER.info("Some problem in JpaQuestionDao.deleteQuestionById", e);
        }
    }
}
