package me.kruase.mipt.api.entities.comment.exceptions;


public class CommentCreateException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Cannot create comment";

    public CommentCreateException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
