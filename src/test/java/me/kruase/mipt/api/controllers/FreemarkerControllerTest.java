package me.kruase.mipt.api.controllers;


import me.kruase.mipt.api.Application;
import me.kruase.mipt.api.entities.article.ArticleService;
import me.kruase.mipt.api.entities.article.models.Article;
import me.kruase.mipt.api.entities.article.models.ArticleId;
import me.kruase.mipt.api.entities.comment.models.Comment;
import me.kruase.mipt.api.entities.comment.models.CommentId;
import me.kruase.mipt.api.util.SimpleHttpClient;
import me.kruase.mipt.api.util.TemplateFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
class FreemarkerControllerTest {
    static final String route = "/test";

    Service service;
    Application application;
    SimpleHttpClient client;
    String baseUrl;

    @Mock
    ArticleService articleService;

    Article article;

    @BeforeEach
    void setUp() {
        service = Service.ignite();

        application = new Application(
                List.of(
                        new FreemarkerController(
                                route,
                                service,
                                articleService,
                                TemplateFactory.freeMarkerEngine()
                        )
                )
        );

        application.start();
        service.awaitInitialization();

        baseUrl = "http://localhost:" + service.port() + route;
        client = new SimpleHttpClient();

        ArticleId articleId = new ArticleId(123);
        article = new Article(
                articleId,
                "title",
                Set.of("tag_1", "tag_2"),
                List.of(
                        new Comment(
                                new CommentId(456),
                                articleId,
                                "content_1"
                        ),
                        new Comment(
                                new CommentId(789),
                                articleId,
                                "content_2"
                        )
                )
        );
    }

    @Test
    void testGetArticleTable200() throws IOException, InterruptedException {
        Mockito.when(articleService.findAll()).thenReturn(List.of(article));

        HttpResponse<String> response = client.get(baseUrl);
        String responseContent = response.body();

        assertTrue(responseContent.contains(article.id().toString()));
        assertTrue(responseContent.contains(article.title()));
        assertTrue(
                article.tags().stream()
                        .allMatch(responseContent::contains)
        );
        assertTrue(responseContent.contains(String.valueOf(article.comments().size())));
    }
}
