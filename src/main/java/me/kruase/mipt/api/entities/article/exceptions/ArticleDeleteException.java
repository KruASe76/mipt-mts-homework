package me.kruase.mipt.api.entities.article.exceptions;


import me.kruase.mipt.api.entities.article.models.ArticleId;


public class ArticleDeleteException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Cannot delete article with id=%s";

    public ArticleDeleteException(ArticleId articleId, Throwable cause) {
        super(String.format(DEFAULT_MESSAGE, articleId), cause);
    }
}
