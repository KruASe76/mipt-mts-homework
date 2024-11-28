package me.kruase.mipt.api.entities.article.exceptions;


import me.kruase.mipt.api.entities.article.models.ArticleId;


public class ArticleFindException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Cannot find article with id=%s";

    public ArticleFindException(ArticleId articleId, Throwable cause) {
        super(String.format(DEFAULT_MESSAGE, articleId), cause);
    }
}
