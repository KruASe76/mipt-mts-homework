package me.kruase.mipt.api.entities.article.exceptions;


public class ArticleCreateException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Cannot create article";

    public ArticleCreateException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
