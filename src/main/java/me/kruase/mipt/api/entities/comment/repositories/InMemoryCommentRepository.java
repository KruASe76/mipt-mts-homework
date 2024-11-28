package me.kruase.mipt.api.entities.comment.repositories;


import me.kruase.mipt.api.entities.article.models.ArticleId;
import me.kruase.mipt.api.entities.comment.exceptions.CommentConflictException;
import me.kruase.mipt.api.entities.comment.exceptions.CommentNotFoundException;
import me.kruase.mipt.api.entities.comment.models.Comment;
import me.kruase.mipt.api.entities.comment.models.CommentId;
import me.kruase.mipt.api.entities.comment.models.NewComment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


public final class InMemoryCommentRepository implements CommentRepository {
    private final AtomicLong lastId = new AtomicLong(0);
    private final Map<CommentId, Comment> storage = new ConcurrentHashMap<>();

    private CommentId generateId() {
        return new CommentId(lastId.incrementAndGet());
    }

    @Override
    public Comment findById(CommentId commentId) throws CommentNotFoundException {
        Comment comment = storage.get(commentId);

        if (comment == null) {
            throw new CommentNotFoundException(commentId);
        }

        return comment;
    }

    @Override
    public synchronized CommentId create(NewComment comment) throws CommentConflictException {
        CommentId commentId = generateId();

        if (storage.get(commentId) != null) {
            throw new CommentConflictException(commentId);
        }

        Comment newComment = new Comment(commentId, comment);

        storage.put(commentId, newComment);

        return commentId;
    }

    @Override
    public void delete(CommentId commentId) throws CommentNotFoundException {
        if (storage.remove(commentId) == null) {
            throw new CommentNotFoundException(commentId);
        }
    }

    @Override
    public void deleteAllByArticleId(ArticleId articleId) {
        storage.values().removeIf(comment -> comment.articleId().equals(articleId));
    }
}
