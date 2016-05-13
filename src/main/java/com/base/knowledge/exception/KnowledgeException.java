package com.base.knowledge.exception;


public class KnowledgeException extends Exception {
    public KnowledgeException() { super(); }
    public KnowledgeException(String message) { super(message); }
    public KnowledgeException(String message, Throwable cause) { super(message, cause); }
    public KnowledgeException(Throwable cause) { super(cause); }
}
