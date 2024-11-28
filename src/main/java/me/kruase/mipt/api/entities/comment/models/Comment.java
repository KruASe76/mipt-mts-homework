package me.kruase.mipt.api.entities.comment.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import me.kruase.mipt.api.entities.article.models.ArticleId;

import java.util.Objects;


public record Comment(
        CommentId id,
        @JsonProperty("article_id") ArticleId articleId,
        String content
) {
    public Comment(CommentId id, NewComment newComment) {
        this(id, newComment.articleId(), newComment.content());
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Comment comment)) {
            return false;
        }

        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
