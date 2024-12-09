package me.kruase.mipt.api.entities.comment.repositories;


import me.kruase.mipt.api.entities.article.models.ArticleId;
import me.kruase.mipt.api.entities.comment.exceptions.CommentConflictException;
import me.kruase.mipt.api.entities.comment.exceptions.CommentNotFoundException;
import me.kruase.mipt.api.entities.comment.models.Comment;
import me.kruase.mipt.api.entities.comment.models.CommentId;
import me.kruase.mipt.api.entities.comment.models.NewComment;


public interface CommentRepository {
    Comment findById(CommentId commentId) throws CommentNotFoundException;

    CommentId create(NewComment comment) throws CommentConflictException;

    void delete(CommentId commentId) throws CommentNotFoundException;

    void deleteAllByArticleId(ArticleId articleId);

}
