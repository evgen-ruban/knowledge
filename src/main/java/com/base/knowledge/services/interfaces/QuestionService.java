package com.base.knowledge.services.interfaces;


import com.base.knowledge.domain.Question;
import com.base.knowledge.domain.User;

import java.util.List;

public interface QuestionService {

    public Question getQuestionById(int id);

    public List<Question> getAllQuestionByUser(User user);

    public void putQuestion(Question question);

    public void deleteQuestionById(int id);

}

