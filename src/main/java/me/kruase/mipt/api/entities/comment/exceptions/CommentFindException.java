package me.kruase.mipt.api.entities.comment.exceptions;


import me.kruase.mipt.api.entities.comment.models.CommentId;


public class CommentFindException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Cannot find comment with id=%s";

    public CommentFindException(CommentId commentId, Throwable cause) {
        super(String.format(DEFAULT_MESSAGE, commentId), cause);
    }
}
