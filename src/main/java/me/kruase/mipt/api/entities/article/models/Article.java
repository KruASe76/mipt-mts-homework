package me.kruase.mipt.api.entities.article.models;


import me.kruase.mipt.api.entities.comment.models.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


public record Article(ArticleId id, String title, Set<String> tags, List<Comment> comments) {
    public Article(ArticleId id, NewArticle newArticle) {
        this(id, newArticle.title(), newArticle.tags(), List.of());
    }

    public Article withTitle(String newTitle) {
        return new Article(id, newTitle, tags, comments);
    }

    public Article withTags(Set<String> newTags) {
        return new Article(id, title, newTags, comments);
    }

    public Article addComment(Comment comment) {
        List<Comment> newComments = new ArrayList<>(comments);
        newComments.add(comment);

        return new Article(id, title, tags, newComments);
    }

    public Article removeComment(Comment comment) {
        List<Comment> newComments = new ArrayList<>(comments);
        newComments.remove(comment);

        return new Article(id, title, tags, newComments);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Article article)) {
            return false;
        }

        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
