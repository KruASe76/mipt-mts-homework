package me.kruase.mipt.api.entities.comment.exceptions;


import me.kruase.mipt.api.entities.comment.models.CommentId;


public class CommentConflictException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Comment with id=%s already exists";

    public CommentConflictException(CommentId commentId) {
        super(String.format(DEFAULT_MESSAGE, commentId));
    }
}
