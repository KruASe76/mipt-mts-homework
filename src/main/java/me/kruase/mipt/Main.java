package me.kruase.mipt;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.kruase.mipt.api.Application;
import me.kruase.mipt.api.controllers.FreemarkerController;
import me.kruase.mipt.api.entities.article.ArticleService;
import me.kruase.mipt.api.entities.article.repositories.ArticleRepository;
import me.kruase.mipt.api.entities.article.repositories.InMemoryArticleRepository;
import me.kruase.mipt.api.entities.comment.CommentService;
import me.kruase.mipt.api.entities.comment.repositories.CommentRepository;
import me.kruase.mipt.api.entities.comment.repositories.InMemoryCommentRepository;
import me.kruase.mipt.api.controllers.ArticleController;
import me.kruase.mipt.api.controllers.CommentController;
import me.kruase.mipt.api.util.TemplateFactory;
import spark.Service;

import java.util.List;


public class Main {
    private static final String apiPrefix = "/api";

    public static void main(String[] args) {
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
                "/",
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
    }
}
