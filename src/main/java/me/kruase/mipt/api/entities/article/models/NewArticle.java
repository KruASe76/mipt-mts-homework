package me.kruase.mipt.api.entities.article.models;


import java.util.Set;


/**
 * DTO for creating a new {@link Article}, without {@code articleId} field
 */
public record NewArticle(String title, Set<String> tags) {
}
