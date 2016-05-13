package com.base.knowledge.dao;


import com.base.knowledge.domain.Question;
import com.base.knowledge.domain.User;
import java.util.List;

public interface QuestionDao {

    public Question getQuestionById(int id);

    public List getAllQuestionByUser(User user);

    public void putQuestion(Question question);

    public void deleteQuestionById(int id);

}
