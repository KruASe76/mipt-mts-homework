package me.kruase.mipt.api.schemas.request;


import java.util.Set;


public record ArticleCreateRequest(String title, Set<String> tags) {
}
