package com.base.knowledge.hsqldbtest;


import com.base.knowledge.dao.jpa.JpaQuestionDao;
import com.base.knowledge.domain.Question;
import com.base.knowledge.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class JpaQuestionDaoHSQLTest {

    @Autowired
    private EntityManagerFactory managerFactory;

    public EntityManagerFactory getManagerFactory() {
        return managerFactory;
    }

    public void setManagerFactory(EntityManagerFactory managerFactory) {
        this.managerFactory = managerFactory;
    }

    private EntityManager entityManager;

    JpaQuestionDao questionDao;

    @Before
    public void setUp() throws Exception {
        questionDao = new JpaQuestionDao();
        entityManager = managerFactory.createEntityManager();
        questionDao.setEntityManager(this.entityManager);
    }

    @After
    public void tearDown(){
    }

    @Test
     public void testGetQuestionById() throws Exception {
        //arrange
        //Question with data ((40, 'why?', 30) owner = User with login: 'Logruban') was add in insert-data.sql
        Question result;
        entityManager.getTransaction().begin();
        //act
        result = questionDao.getQuestionById(40);
        entityManager.getTransaction().commit();
        //assert
        assertEquals("why?", result.getContent());
    }

    @Test
    public void testGetAllQuestionByUser() throws Exception {
        //arrange
        //Question with data ((40, 'why?', 30) owner = User with login: 'Logruban') was add in insert-data.sql
        User user = new User(30, "evgen", "LNruban", "Logruban", "Pasruban");
        List<Question> result;
        entityManager.getTransaction().begin();
        //act
        result = questionDao.getAllQuestionByUser(user);
        entityManager.getTransaction().commit();
        //assert
        assertEquals(result.size(), 1);
    }

    @Test
    public void testPutQuestion() throws Exception {
        //arrange
        User owner = new User(10, "ivan", "red", "login", "password");
        Question question = new Question("when?", owner);
        List<Question> result;
        entityManager.getTransaction().begin();
        //act
        questionDao.putQuestion(question);
        result = questionDao.getAllQuestionByUser(owner);
        entityManager.getTransaction().commit();
        //assert
        assertEquals(result.size(), 1);
    }

    @Test
    public void testDeleteQuestionById() throws Exception {
        //arrange
        //Question with data ((60, 'why?', 60) owner = User with login: 'admin') was add in insert-data.sql
        entityManager.getTransaction().begin();
        //act
        questionDao.deleteQuestionById(60);
        Question question = questionDao.getQuestionById(60);
        entityManager.getTransaction().commit();
        //assert
        assertNull(question);
    }

}
