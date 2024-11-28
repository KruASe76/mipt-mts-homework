package me.kruase.mipt.api.entities.article.repositories;


import me.kruase.mipt.api.entities.article.exceptions.ArticleConflictException;
import me.kruase.mipt.api.entities.article.exceptions.ArticleNotFoundException;
import me.kruase.mipt.api.entities.article.models.Article;
import me.kruase.mipt.api.entities.article.models.ArticleId;
import me.kruase.mipt.api.entities.article.models.NewArticle;
import me.kruase.mipt.api.entities.comment.models.Comment;

import java.util.List;


public interface ArticleRepository {
    List<Article> findAll();

    Article findById(ArticleId articleId) throws ArticleNotFoundException;

    ArticleId create(NewArticle newArticle) throws ArticleConflictException;

    void update(Article article) throws ArticleNotFoundException;

    void delete(ArticleId articleId) throws ArticleNotFoundException;

    void addComment(ArticleId articleId, Comment comment) throws ArticleNotFoundException;

    void removeComment(ArticleId articleId, Comment comment) throws ArticleNotFoundException;
}
