package com.base.knowledge.controllers;


import com.base.knowledge.domain.Question;
import com.base.knowledge.domain.User;
import com.base.knowledge.services.interfaces.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);

    @RequestMapping(value = "/question", method = {RequestMethod.POST, RequestMethod.GET})
    public String makeQuestion(HttpServletRequest request, HttpSession session) {
        User user = (User)session.getAttribute("user");
        String content = request.getParameter("content");
        if (user != null && content != null && content.length() > 0) {
            LOGGER.trace("user = " + user.getLogin() + " make question - " + content);
            Question question = new Question(content, user);
            questionService.putQuestion(question);
        } else {
            LOGGER.warn("Some problems occurred when user asked a question");
        }
        return "main";
    }

    @RequestMapping(value = "/watchquestion", method = RequestMethod.POST)
    public String watchQuestion () {
        return "main";
    }

}
