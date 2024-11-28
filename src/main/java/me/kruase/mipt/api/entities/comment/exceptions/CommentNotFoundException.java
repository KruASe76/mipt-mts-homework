package me.kruase.mipt.api.entities.comment.exceptions;


import me.kruase.mipt.api.entities.comment.models.CommentId;


public class CommentNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Comment with id=%s does not exist";

    public CommentNotFoundException(CommentId commentId) {
        super(String.format(DEFAULT_MESSAGE, commentId));
    }

}
