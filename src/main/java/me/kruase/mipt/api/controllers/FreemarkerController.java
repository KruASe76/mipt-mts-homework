package me.kruase.mipt.api.controllers;


import me.kruase.mipt.api.entities.article.ArticleService;
import me.kruase.mipt.api.entities.article.models.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Service;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class FreemarkerController implements Controller {
    private static final Logger LOG = LoggerFactory.getLogger(FreemarkerController.class);

    private final String route;
    private final Service service;
    private final ArticleService articleService;
    private final FreeMarkerEngine freeMarkerEngine;

    public FreemarkerController(
            String route,
            Service service,
            ArticleService articleService,
            FreeMarkerEngine freeMarkerEngine
    ) {
        this.route = route;
        this.service = service;
        this.articleService = articleService;
        this.freeMarkerEngine = freeMarkerEngine;
    }

    @Override
    public void initializeEndpoints() {
        getArticleTable();
    }

    private void getArticleTable() {
        service.get(
                route,
                (Request request, Response response) -> {
                    response.type("text/html; charset=utf-8");

                    List<Article> articles = articleService.findAll();
                    List<Map<String, String>> articleMapList =
                            articles.stream()
                                    .map(article ->
                                            Map.of(
                                                    "id", article.id().toString(),
                                                    "title", article.title(),
                                                    "tags", String.join(" ", article.tags()),
                                                    "comment_count",
                                                    String.valueOf(article.comments().size())
                                            )
                                    )
                                    .toList();

                    LOG.debug("Successfully rendered article table");

                    Map<String, Object> model = new HashMap<>();
                    model.put("articles", articleMapList);
                    return freeMarkerEngine.render(new ModelAndView(model, "index.ftl"));
                }
        );
    }
}
