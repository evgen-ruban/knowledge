package com.base.knowledge.services.implementations;


import com.base.knowledge.dao.QuestionDao;
import com.base.knowledge.domain.Question;
import com.base.knowledge.domain.User;
import com.base.knowledge.services.interfaces.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {


    @Autowired
    @Qualifier("jpaQuestionDao")
    private QuestionDao questionDao;
    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public Question getQuestionById(int id) {
        return questionDao.getQuestionById(id);
    }

    @Override
    public void putQuestion(Question question) {
        questionDao.putQuestion(question);
    }

    @Override
    public void deleteQuestionById(int id) {
        questionDao.deleteQuestionById(id);
    }

    @Override
    public List<Question> getAllQuestionByUser(User user) {
        return questionDao.getAllQuestionByUser(user);
    }


}
