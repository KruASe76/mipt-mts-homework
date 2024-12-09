package me.kruase.mipt.api.schemas.request;


import com.fasterxml.jackson.annotation.JsonProperty;


public record CommentCreateRequest(@JsonProperty("article_id") long articleId, String content) {
}
