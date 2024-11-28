package me.kruase.mipt.api.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.kruase.mipt.api.Application;
import me.kruase.mipt.api.entities.article.models.ArticleId;
import me.kruase.mipt.api.entities.comment.CommentService;
import me.kruase.mipt.api.entities.comment.exceptions.CommentCreateException;
import me.kruase.mipt.api.entities.comment.exceptions.CommentDeleteException;
import me.kruase.mipt.api.entities.comment.exceptions.CommentFindException;
import me.kruase.mipt.api.entities.comment.models.Comment;
import me.kruase.mipt.api.entities.comment.models.CommentId;
import me.kruase.mipt.api.entities.comment.models.CommentTest;
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

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class CommentControllerTest {
    static final String routePrefix = "/test/api/comments";

    Service service;
    ObjectMapper objectMapper;
    Application application;
    SimpleHttpClient client;
    String baseUrl;

    Comment comment;

    @Mock
    CommentService commentService;

    @BeforeEach
    void setUp() {
        service = Service.ignite();

        objectMapper = new ObjectMapper();
        application = new Application(
                List.of(
                        new CommentController(
                                routePrefix,
                                service,
                                commentService,
                                objectMapper
                        )
                )
        );

        application.start();
        service.awaitInitialization();

        baseUrl = "http://localhost:" + service.port() + routePrefix;
        client = new SimpleHttpClient();

        comment = new Comment(
                new CommentId(123),
                new ArticleId(456),
                "content"
        );
    }

    @AfterEach
    void tearDown() {
        service.stop();
        service.awaitStop();
    }

    @Test
    void tesGetComment200() throws IOException, InterruptedException {
        Mockito.when(commentService.findById(comment.id().value())).thenReturn(comment);

        HttpResponse<String> response = client.get(baseUrl + "/" + comment.id().value());

        assertEquals(200, response.statusCode());
        Comment responseComment = objectMapper.readValue(response.body(), Comment.class);
        CommentTest.assertDeepEquals(comment, responseComment);
    }

    @Test
    void testGetComment404() throws IOException, InterruptedException {
        Mockito.when(commentService.findById(comment.id().value()))
                .thenThrow(CommentFindException.class);

        HttpResponse<String> response = client.get(baseUrl + "/" + comment.id().value());

        assertEquals(404, response.statusCode());
    }

    @Test
    void testCreateComment201() throws IOException, InterruptedException {
        Mockito.when(commentService.create(comment.articleId().value(), comment.content()))
                .thenReturn(comment.id().value());

        HttpResponse<String> response = client.post(
                baseUrl, """
                        {
                          "article_id": 456,
                          "content": "content"
                        }
                        """
        );

        assertEquals(201, response.statusCode());
        CreateResponse createResponse =
                objectMapper.readValue(response.body(), CreateResponse.class);
        assertEquals(comment.id().value(), createResponse.id());
    }

    @Test
    void testCreateComment400() throws IOException, InterruptedException {
        Mockito.when(commentService.create(comment.articleId().value(), comment.content()))
                .thenThrow(CommentCreateException.class);

        HttpResponse<String> response = client.post(
                baseUrl, """
                        {
                          "article_id": 456,
                          "content": "content"
                        }
                        """
        );

        assertEquals(400, response.statusCode());
    }

    @Test
    void testDeleteComment204() throws IOException, InterruptedException {
        Mockito.doNothing().when(commentService).delete(comment.id().value());

        HttpResponse<String> response = client.delete(baseUrl + "/" + comment.id().value());

        assertEquals(204, response.statusCode());
    }

    @Test
    void testDeleteComment404() throws IOException, InterruptedException {
        Mockito.doThrow(CommentDeleteException.class)
                .when(commentService).delete(comment.id().value());

        HttpResponse<String> response = client.delete(baseUrl + "/" + comment.id().value());

        assertEquals(404, response.statusCode());
    }
}
