package me.kruase.mipt.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.kruase.mipt.api.controllers.ArticleController;
import me.kruase.mipt.api.controllers.CommentController;
import me.kruase.mipt.api.controllers.FreemarkerController;
import me.kruase.mipt.api.entities.article.ArticleService;
import me.kruase.mipt.api.entities.article.models.Article;
import me.kruase.mipt.api.entities.article.repositories.ArticleRepository;
import me.kruase.mipt.api.entities.article.repositories.InMemoryArticleRepository;
import me.kruase.mipt.api.entities.comment.CommentService;
import me.kruase.mipt.api.entities.comment.repositories.CommentRepository;
import me.kruase.mipt.api.entities.comment.repositories.InMemoryCommentRepository;
import me.kruase.mipt.api.schemas.response.CreateResponse;
import me.kruase.mipt.api.util.SimpleHttpClient;
import me.kruase.mipt.api.util.TemplateFactory;
import org.junit.jupiter.api.Test;
import spark.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ApplicationTest {
    static final String globalPrefix = "/test";
    static final String apiPrefix = globalPrefix + "/api";

    @Test
    void testEndToEnd() throws IOException, InterruptedException {
        // Setup
        Service service = Service.ignite();
        ObjectMapper objectMapper = new ObjectMapper();

        CommentRepository commentRepository = new InMemoryCommentRepository();
        ArticleRepository articleRepository = new InMemoryArticleRepository(commentRepository);

        ArticleService articleService = new ArticleService(articleRepository);
        CommentService commentService = new CommentService(commentRepository, articleRepository);

        ArticleController articleController = new ArticleController(
                apiPrefix + "/articles",
                service,
                articleService,
                objectMapper
        );
        CommentController commentController = new CommentController(
                apiPrefix + "/comments",
                service,
                commentService,
                objectMapper
        );
        FreemarkerController freemarkerController = new FreemarkerController(
                globalPrefix,
                service,
                articleService,
                TemplateFactory.freeMarkerEngine()
        );

        Application application = new Application(
                List.of(
                        articleController,
                        commentController,
                        freemarkerController
                )
        );

        application.start();
        service.awaitInitialization();

        String baseUrl = "http://localhost:" + service.port() + globalPrefix;
        String apiUrl = "http://localhost:" + service.port() + apiPrefix;
        SimpleHttpClient client = new SimpleHttpClient();

        // Constants
        String title = "title";
        String titleUpdated = "title_updated";
        String tag1 = "tag_1";
        String tag1Updated = "tag_1_updated";
        String tag2 = "tag_2";
        String tag2Updated = "tag_2_updated";
        String content = "content";

        // Test
        HttpResponse<String> articleCreateHttpResponse = client.post(
                apiUrl + "/articles",
                """
                        {
                          "title": "%s",
                          "tags": ["%s", "%s"]
                        }
                        """.formatted(title, tag1, tag2)
        );
        long articleId =
                objectMapper.readValue(articleCreateHttpResponse.body(), CreateResponse.class).id();

        HttpResponse<String> commentCreateHttpResponse = client.post(
                apiUrl + "/comments",
                """
                        {
                          "article_id": "%d",
                          "content": "%s"
                        }
                        """.formatted(articleId, content)
        );
        long commentId =
                objectMapper.readValue(commentCreateHttpResponse.body(), CreateResponse.class).id();

        HttpResponse<String> articleGetHttpResponse =
                client.get(apiUrl + "/articles/" + articleId);
        Article article = objectMapper.readValue(articleGetHttpResponse.body(), Article.class);

        assertEquals(title, article.title());
        assertEquals(Set.of(tag1, tag2), article.tags());
        assertEquals(1, article.comments().size());
        assertEquals(commentId, article.comments().get(0).id().value());
        assertEquals(content, article.comments().get(0).content());

        HttpResponse<String> htmlResponse = client.get(baseUrl);
        String htmlResponseContent = htmlResponse.body();

        assertTrue(htmlResponseContent.contains(String.valueOf(articleId)));
        assertTrue(htmlResponseContent.contains(title));
        assertTrue(htmlResponseContent.contains(tag1));
        assertTrue(htmlResponseContent.contains(tag2));
        assertTrue(htmlResponseContent.contains(String.valueOf(article.comments().size())));

        client.put(
                apiUrl + "/articles/" + articleId,
                """
                        {
                          "title": "%s",
                          "tags": ["%s", "%s"]
                        }
                        """.formatted(titleUpdated, tag1Updated, tag2Updated)
        );

        client.delete(apiUrl + "/comments/" + commentId);

        articleGetHttpResponse = client.get(apiUrl + "/articles/" + articleId);
        article = objectMapper.readValue(articleGetHttpResponse.body(), Article.class);

        assertEquals(titleUpdated, article.title());
        assertEquals(Set.of(tag1Updated, tag2Updated), article.tags());
        assertEquals(0, article.comments().size());

        htmlResponse = client.get(baseUrl);
        htmlResponseContent = htmlResponse.body();

        assertTrue(htmlResponseContent.contains(String.valueOf(articleId)));
        assertTrue(htmlResponseContent.contains(titleUpdated));
        assertTrue(htmlResponseContent.contains(tag1Updated));
        assertTrue(htmlResponseContent.contains(tag2Updated));
        assertTrue(htmlResponseContent.contains(String.valueOf(article.comments().size())));

        // Shutdown
        service.stop();
        service.awaitStop();
    }
}
