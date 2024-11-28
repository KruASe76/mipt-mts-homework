package me.kruase.mipt.api.entities.article.exceptions;


import me.kruase.mipt.api.entities.article.models.ArticleId;


public class ArticleUpdateException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Cannot update article with id=%s";

    public ArticleUpdateException(ArticleId articleId, Throwable cause) {
        super(String.format(DEFAULT_MESSAGE, articleId), cause);
    }
}
