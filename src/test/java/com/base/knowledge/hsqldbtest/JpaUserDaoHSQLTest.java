package com.base.knowledge.hsqldbtest;


import com.base.knowledge.dao.jpa.JpaUserDao;
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
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
public class JpaUserDaoHSQLTest {

    @Autowired
    private EntityManagerFactory managerFactory;

    public EntityManagerFactory getManagerFactory() {
        return managerFactory;
    }

    public void setManagerFactory(EntityManagerFactory managerFactory) {
        this.managerFactory = managerFactory;
    }

    private EntityManager entityManager;

    JpaUserDao userDao;

    @Before
    public void setUp() throws Exception {
        userDao = new JpaUserDao();
        entityManager = managerFactory.createEntityManager();
        userDao.setEntityManager(this.entityManager);
    }

    @After
    public void tearDown(){
    }

    @Test
    public void testGetUserById() throws Exception {
        //arrange
        //User with data (30, 'evgen', 'LNruban', 'Logruban', 'Pasruban') was add in insert-data.sql
        User result;
        entityManager.getTransaction().begin();
        //act
        result = userDao.getUserById(30);
        entityManager.getTransaction().commit();
        //assert
        assertEquals("LNruban", result.getLastName());
        assertEquals("evgen", result.getName());
        assertEquals("Logruban", result.getLogin());
        assertEquals("Pasruban", result.getPassword());
    }

    @Test
    public void testGetUserByLoginAndPassword() throws Exception {
        //arrange
        //User with data (10, 'red', 'login', 'ivan', 'password') was add in insert-data.sql
        User result;
        entityManager.getTransaction().begin();
        //act
        result = userDao.getUserByLoginAndPassword("login", "password");
        entityManager.getTransaction().commit();
        //assert
        assertEquals("red", result.getLastName());
        assertEquals("ivan", result.getName());
        assertEquals("login", result.getLogin());
        assertEquals("password", result.getPassword());
    }

    @Test
    public void testPutUser() throws Exception {
        //arrange
        User user = new User("Name", "LastName", "LogAdmin", "admin");
        User result;
        entityManager.getTransaction().begin();
        //act
        userDao.putUser(user);
        result = userDao.getUserByLoginAndPassword("LogAdmin", "admin");
        entityManager.getTransaction().commit();
        //assert
        assertSame(user, result);
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getLogin(), result.getLogin());
        assertEquals(user.getPassword(), result.getPassword());
    }

    @Test
    public void testDeleteUser() throws Exception {
        //arrange
        //User with data (20, 'userForDelete', 'userForDelete', 'userForDelete', 'userForDelete') was add in insert-data.sql
        User user = new User(20, "userForDelete", "userForDelete", "userForDelete", "userForDelete");
        User result;
        entityManager.getTransaction().begin();
        //act
        userDao.deleteUser(user);
        result = userDao.getUserByLoginAndPassword("userForDelete", "userForDelete");
        entityManager.getTransaction().commit();
        //assert
        assertNull(result);
    }

}
