package me.kruase.mipt.api.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import me.kruase.mipt.api.entities.comment.CommentService;
import me.kruase.mipt.api.entities.comment.exceptions.CommentCreateException;
import me.kruase.mipt.api.entities.comment.exceptions.CommentDeleteException;
import me.kruase.mipt.api.entities.comment.exceptions.CommentFindException;
import me.kruase.mipt.api.entities.comment.models.Comment;
import me.kruase.mipt.api.schemas.request.CommentCreateRequest;
import me.kruase.mipt.api.schemas.response.CreateResponse;
import me.kruase.mipt.api.schemas.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Service;


public final class CommentController implements Controller {
    private static final Logger LOG = LoggerFactory.getLogger(CommentController.class);

    private final String routePrefix;
    private final Service service;
    private final CommentService commentService;
    private final ObjectMapper objectMapper;

    public CommentController(
            String routePrefix,
            Service service, CommentService commentService, ObjectMapper objectMapper
    ) {
        this.routePrefix = routePrefix;
        this.service = service;
        this.commentService = commentService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void initializeEndpoints() {
        getComment();
        createComment();
        deleteComment();
    }

    private void getComment() {
        service.get(
                routePrefix + "/:id",
                (request, response) -> {
                    response.type("application/json");

                    long commentId = Long.parseLong(request.params("id"));

                    try {
                        Comment comment = commentService.findById(commentId);

                        LOG.debug("Successfully found comment by id={}", commentId);

                        response.status(200);
                        return objectMapper.writeValueAsString(comment);
                    } catch (CommentFindException e) {
                        LOG.warn("Error getting comment by id={}", commentId, e);

                        response.status(404);
                        return objectMapper.writeValueAsString(new ErrorResponse(e));
                    }
                }
        );
    }

    private void createComment() {
        service.post(
                routePrefix,
                (request, response) -> {
                    response.type("application/json");

                    CommentCreateRequest commentCreateRequest =
                            objectMapper.readValue(request.body(), CommentCreateRequest.class);

                    try {
                        long commentId = commentService.create(
                                commentCreateRequest.articleId(),
                                commentCreateRequest.content()
                        );

                        LOG.debug(
                                "Successfully added new comment with id={} to article with id={}",
                                commentId, commentCreateRequest.articleId()
                        );

                        response.status(201);
                        return objectMapper.writeValueAsString(new CreateResponse(commentId));
                    } catch (CommentCreateException e) {
                        LOG.warn("Error creating comment", e);

                        response.status(400);
                        return objectMapper.writeValueAsString(new ErrorResponse(e));
                    }
                }
        );
    }

    private void deleteComment() {
        service.delete(
                routePrefix + "/:id",
                (request, response) -> {
                    response.type("application/json");

                    long commentId = Long.parseLong(request.params("id"));

                    try {
                        commentService.delete(commentId);

                        LOG.debug("Successfully deleted comment with id={}", commentId);

                        response.status(204);
                        return "";
                    } catch (CommentDeleteException e) {
                        LOG.warn("Error deleting comment by id={}", commentId, e);

                        response.status(404);
                        return objectMapper.writeValueAsString(new ErrorResponse(e));
                    }
                }
        );
    }
}
