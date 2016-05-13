package com.base.knowledge.util;

import com.base.knowledge.dao.QuestionDao;
import com.base.knowledge.dao.UserDao;
import com.base.knowledge.dao.hibernate.HibernateQuestionDao;
import com.base.knowledge.dao.hibernate.HibernateUserDao;
import com.base.knowledge.dao.jdbc.JdbcQuestionDao;
import com.base.knowledge.dao.jdbc.JdbcUserDao;
import com.base.knowledge.dao.jpa.JpaQuestionDao;
import com.base.knowledge.dao.jpa.JpaUserDao;


public class DaoFactory {

    private Tools toolsName;

    public Tools getToolsName() {
        return toolsName;
    }

    public void setToolsName(Tools toolsName) {
        this.toolsName = toolsName;
    }

    public UserDao createUserDao() {
        UserDao result;
        if (toolsName == Tools.JPA) {
            result =  new JpaUserDao();
        }
        if (toolsName == Tools.HIBERNATE) {
            result =  new HibernateUserDao();
        }
        if (toolsName == Tools.JDBC){
            result =  new JdbcUserDao();
        }
        result =  null;
        return result;
    }

    public QuestionDao createQuestionDao() {
        QuestionDao result;
        if (toolsName == Tools.JPA) {
            result =  new JpaQuestionDao();
        }
        if (toolsName == Tools.HIBERNATE) {
            result =  new HibernateQuestionDao();
        }
        if (toolsName == Tools.JDBC){
            result =  new JdbcQuestionDao();
        }
        result =  null;
        return result;
    }
}
