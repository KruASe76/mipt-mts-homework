package me.kruase.mipt.api.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.kruase.mipt.api.entities.article.ArticleService;
import me.kruase.mipt.api.entities.article.exceptions.ArticleCreateException;
import me.kruase.mipt.api.entities.article.exceptions.ArticleDeleteException;
import me.kruase.mipt.api.entities.article.exceptions.ArticleFindException;
import me.kruase.mipt.api.entities.article.exceptions.ArticleUpdateException;
import me.kruase.mipt.api.entities.article.models.Article;
import me.kruase.mipt.api.schemas.request.ArticleCreateRequest;
import me.kruase.mipt.api.schemas.request.ArticleUpdateRequest;
import me.kruase.mipt.api.schemas.response.CreateResponse;
import me.kruase.mipt.api.schemas.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Service;


public final class ArticleController implements Controller {
    private static final Logger LOG = LoggerFactory.getLogger(ArticleController.class);

    private final String routePrefix;
    private final Service service;
    private final ArticleService articleService;
    private final ObjectMapper objectMapper;


    public ArticleController(
            String routePrefix,
            Service service, ArticleService articleService, ObjectMapper objectMapper
    ) {
        this.routePrefix = routePrefix;
        this.service = service;
        this.articleService = articleService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void initializeEndpoints() {
        getAllArticles();
        getArticle();
        createArticle();
        updateArticle();
        deleteArticle();
    }

    private void getAllArticles() {
        service.get(
                routePrefix,
                (request, response) -> {
                    response.type("application/json");

                    LOG.debug("Successfully found all articles");

                    response.status(200);
                    return objectMapper.writeValueAsString(articleService.findAll());
                }
        );
    }

    private void getArticle() {
        service.get(
                routePrefix + "/:id",
                (request, response) -> {
                    response.type("application/json");

                    long articleId = Long.parseLong(request.params("id"));

                    try {
                        Article article = articleService.findById(articleId);

                        LOG.debug("Successfully found article by id={}", articleId);

                        response.status(200);
                        return objectMapper.writeValueAsString(article);
                    } catch (ArticleFindException e) {
                        LOG.warn("Error getting article by id={}", articleId, e);

                        response.status(404);
                        return objectMapper.writeValueAsString(new ErrorResponse(e));
                    }
                }
        );
    }

    private void createArticle() {
        service.post(
                routePrefix,
                (request, response) -> {
                    response.type("application/json");

                    ArticleCreateRequest articleCreateRequest =
                            objectMapper.readValue(request.body(), ArticleCreateRequest.class);

                    try {
                        long articleId = articleService.create(
                                articleCreateRequest.title(),
                                articleCreateRequest.tags()
                        );

                        LOG.debug("Successfully created article with id={}", articleId);

                        response.status(201);
                        return objectMapper.writeValueAsString(new CreateResponse(articleId));
                    } catch (ArticleCreateException e) {
                        LOG.warn("Error creating article", e);

                        response.status(400);
                        return objectMapper.writeValueAsString(new ErrorResponse(e));
                    }
                }
        );
    }

    private void updateArticle() {
        service.put(
                routePrefix + "/:id",
                (request, response) -> {
                    response.type("application/json");

                    long articleId = Long.parseLong(request.params("id"));
                    ArticleUpdateRequest articleUpdateRequest =
                            objectMapper.readValue(request.body(), ArticleUpdateRequest.class);

                    try {
                        articleService.update(
                                articleId,
                                articleUpdateRequest.title(),
                                articleUpdateRequest.tags()
                        );

                        LOG.debug("Successfully updated article by id={}", articleId);

                        response.status(204);
                        return "";
                    } catch (ArticleUpdateException e) {
                        LOG.warn("Error updating article by id={}", articleId, e);

                        response.status(404);
                        return objectMapper.writeValueAsString(new ErrorResponse(e));
                    }
                }
        );
    }

    private void deleteArticle() {
        service.delete(
                routePrefix + "/:id",
                (request, response) -> {
                    response.type("application/json");

                    long articleId = Long.parseLong(request.params("id"));

                    try {
                        articleService.delete(articleId);

                        LOG.debug("Successfully deleted article by id={}", articleId);

                        response.status(204);
                        return "";
                    } catch (ArticleDeleteException e) {
                        LOG.warn("Error deleting article by id={}", articleId, e);

                        response.status(404);
                        return objectMapper.writeValueAsString(new ErrorResponse(e));
                    }
                }
        );
    }
}
