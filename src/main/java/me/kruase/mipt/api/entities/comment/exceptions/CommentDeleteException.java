package me.kruase.mipt.api.entities.comment.exceptions;


import me.kruase.mipt.api.entities.comment.models.CommentId;


public class CommentDeleteException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Cannot delete comment with id=%s";

    public CommentDeleteException(CommentId commentId, Throwable cause) {
        super(String.format(DEFAULT_MESSAGE, commentId), cause);
    }
}
