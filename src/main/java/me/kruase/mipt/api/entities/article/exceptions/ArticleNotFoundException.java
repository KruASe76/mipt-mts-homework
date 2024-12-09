package me.kruase.mipt.api.entities.article.exceptions;


import me.kruase.mipt.api.entities.article.models.ArticleId;


public class ArticleNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Article with id=%s does not exist";

    public ArticleNotFoundException(ArticleId articleId) {
        super(String.format(DEFAULT_MESSAGE, articleId));
    }

}
