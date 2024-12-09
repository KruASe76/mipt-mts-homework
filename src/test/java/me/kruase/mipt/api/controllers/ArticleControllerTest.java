package me.kruase.mipt.api.controllers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.kruase.mipt.api.Application;
import me.kruase.mipt.api.entities.article.ArticleService;
import me.kruase.mipt.api.entities.article.exceptions.ArticleCreateException;
import me.kruase.mipt.api.entities.article.exceptions.ArticleDeleteException;
import me.kruase.mipt.api.entities.article.exceptions.ArticleFindException;
import me.kruase.mipt.api.entities.article.exceptions.ArticleUpdateException;
import me.kruase.mipt.api.entities.article.models.Article;
import me.kruase.mipt.api.entities.article.models.ArticleId;
import me.kruase.mipt.api.entities.article.models.ArticleTest;
import me.kruase.mipt.api.entities.comment.models.Comment;
import me.kruase.mipt.api.entities.comment.models.CommentId;
import me.kruase.mipt.api.schemas.response.CreateResponse;
import me.kruase.mipt.api.util.SimpleHttpClient;
import org.junit.jupiter.api.AfterEach;
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

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class ArticleControllerTest {
    static final String routePrefix = "/test/api/articles";

    Service service;
    ObjectMapper objectMapper;
    Application application;
    SimpleHttpClient client;
    String baseUrl;

    @Mock
    ArticleService articleService;

    Article article1;
    Article article2;
    Article updatedArticle;

    @BeforeEach
    void setUp() {
        service = Service.ignite();

        objectMapper = new ObjectMapper();
        application = new Application(
                List.of(
                        new ArticleController(
                                routePrefix,
                                service,
                                articleService,
                                objectMapper
                        )
                )
        );

        application.start();
        service.awaitInitialization();

        baseUrl = "http://localhost:" + service.port() + routePrefix;
        client = new SimpleHttpClient();

        ArticleId articleId1 = new ArticleId(123);
        ArticleId articleId2 = new ArticleId(456);

        article1 = new Article(
                articleId1,
                "title_1",
                Set.of("tag_1", "tag_2"),
                List.of(
                        new Comment(new CommentId(123), articleId1, "content_1"),
                        new Comment(new CommentId(456), articleId1, "content_2")
                )
        );
        article2 = new Article(
                articleId2,
                "title_2",
                Set.of("tag_3", "tag_4"),
                List.of(
                        new Comment(new CommentId(789), articleId2, "content_3")
                )
        );
        updatedArticle = new Article(
                articleId1,
                "title_1_updated",
                Set.of("tag_1_updated", "tag_2_updated"),
                article1.comments()
        );
    }

    @AfterEach
    void tearDown() {
        service.stop();
        service.awaitStop();
    }

    @Test
    void testGetAllArticles200() throws IOException, InterruptedException {
        List<Article> articles = List.of(article1, article2);
        Mockito.when(articleService.findAll()).thenReturn(articles);

        HttpResponse<String> response = client.get(baseUrl);

        assertEquals(200, response.statusCode());
        List<Article> responseArticles =
                objectMapper.readValue(response.body(), new TypeReference<>() {});
        ArticleTest.assertDeepEqualsMany(articles, responseArticles);
    }

    @Test
    void testGetArticle200() throws IOException, InterruptedException {
        Mockito.when(articleService.findById(article1.id().value()))
                .thenReturn(article1);

        HttpResponse<String> response = client.get(baseUrl + "/" + article1.id().value());

        assertEquals(200, response.statusCode());
        Article responseArticle = objectMapper.readValue(response.body(), Article.class);
        ArticleTest.assertDeepEquals(article1, responseArticle);
    }

    @Test
    void testGetArticle404() throws IOException, InterruptedException {
        Mockito.when(articleService.findById(article1.id().value()))
                .thenThrow(ArticleFindException.class);

        HttpResponse<String> response = client.get(baseUrl + "/" + article1.id().value());

        assertEquals(404, response.statusCode());
    }

    @Test
    void testCreateArticle201() throws IOException, InterruptedException {
        Mockito.when(articleService.create(article1.title(), article1.tags()))
                .thenReturn(article1.id().value());

        HttpResponse<String> response = client.post(
                baseUrl,
                """
                        {
                          "title": "title_1",
                          "tags": ["tag_1", "tag_2"]
                        }
                        """
        );

        assertEquals(201, response.statusCode());
        CreateResponse createdArticleId =
                objectMapper.readValue(response.body(), CreateResponse.class);
        assertEquals(article1.id().value(), createdArticleId.id());
    }

    @Test
    void testCreateArticle400() throws IOException, InterruptedException {
        Mockito.when(articleService.create(article1.title(), article1.tags()))
                .thenThrow(ArticleCreateException.class);

        HttpResponse<String> response = client.post(
                baseUrl,
                """
                        {
                          "title": "title_1",
                          "tags": ["tag_1", "tag_2"]
                        }
                        """
        );

        assertEquals(400, response.statusCode());
    }

    @Test
    void testUpdateArticle204() throws IOException, InterruptedException {
        Mockito.doNothing()
                .when(articleService)
                .update(article1.id().value(), updatedArticle.title(), updatedArticle.tags());

        HttpResponse<String> response = client.put(
                baseUrl + "/" + article1.id().value(),
                """
                        {
                          "title": "title_1_updated",
                          "tags": ["tag_1_updated", "tag_2_updated"]
                        }
                        """
        );

        assertEquals(204, response.statusCode());
    }

    @Test
    void testUpdateArticle404() throws IOException, InterruptedException {
        Mockito.doThrow(ArticleUpdateException.class)
                .when(articleService)
                .update(article1.id().value(), updatedArticle.title(), updatedArticle.tags());

        HttpResponse<String> response = client.put(
                baseUrl + "/" + article1.id().value(),
                """
                        {
                          "title": "title_1_updated",
                          "tags": ["tag_1_updated", "tag_2_updated"]
                        }
                        """
        );

        assertEquals(404, response.statusCode());
    }

    @Test
    void testDeleteArticle204() throws IOException, InterruptedException {
        Mockito.doNothing().when(articleService).delete(article1.id().value());

        HttpResponse<String> response = client.delete(baseUrl + "/" + article1.id().value());

        assertEquals(204, response.statusCode());
    }

    @Test
    void testDeleteArticle404() throws IOException, InterruptedException {
        Mockito.doThrow(ArticleDeleteException.class)
                .when(articleService).delete(article1.id().value());

        HttpResponse<String> response = client.delete(baseUrl + "/" + article1.id().value());

        assertEquals(404, response.statusCode());
    }
}
