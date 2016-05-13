package com.base.knowledge.domain;

/**
 * Created by Рубан on 13.05.2016.
 */
public class Answer {

    private int id;
    private String content;
    private User userOwner;
    private Question questionForAnswer;

    public Answer() {
    }

    public Answer(String content, User userOwner, Question questionForAnswer) {
        this.content = content;
        this.userOwner = userOwner;
        this.questionForAnswer = questionForAnswer;
    }

    public Answer(int id, String content, User userOwner, Question questionForAnswer) {
        this.id = id;
        this.content = content;
        this.userOwner = userOwner;
        this.questionForAnswer = questionForAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(User userOwner) {
        this.userOwner = userOwner;
    }

    public Question getQuestionForAnswer() {
        return questionForAnswer;
    }

    public void setQuestionForAnswer(Question questionForAnswer) {
        this.questionForAnswer = questionForAnswer;
    }
}
