package me.kruase.mipt.api.entities.comment.models;


import me.kruase.mipt.api.entities.article.models.ArticleId;


/**
 * DTO for creating a new {@link Comment}, without {@code commentId} field
 */
public record NewComment(ArticleId articleId, String content) {
}
