package com.base.knowledge.domain;


import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="question")
public class Question implements Serializable {

    private int id;
    private String content;
    private User owner;

    public Question() {}

    public Question(String content) {
        this.content = content;
    }

    public Question(String content, User owner) {
        this.content = content;
        this.owner = owner;
    }

    @Id
    @GeneratedValue()
    @Column(name="id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name="content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner")
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        if (content != null ? !content.equals(question.content) : question.content != null) return false;
        if (owner != null ? !owner.equals(question.owner) : question.owner != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }
}
