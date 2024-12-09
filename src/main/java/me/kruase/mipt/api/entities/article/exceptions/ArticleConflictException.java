package me.kruase.mipt.api.entities.article.exceptions;


import me.kruase.mipt.api.entities.article.models.ArticleId;


public class ArticleConflictException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Article with id=%s already exists";

    public ArticleConflictException(ArticleId articleId) {
        super(String.format(DEFAULT_MESSAGE, articleId));
    }
}
