package me.kruase.mipt.api.schemas.request;


import java.util.Set;


public record ArticleUpdateRequest(String title, Set<String> tags) {
}
