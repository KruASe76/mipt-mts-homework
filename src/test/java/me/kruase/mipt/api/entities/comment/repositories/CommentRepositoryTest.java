package me.kruase.mipt.api.entities.comment.repositories;


import me.kruase.mipt.api.entities.article.models.ArticleId;
import me.kruase.mipt.api.entities.comment.exceptions.CommentNotFoundException;
import me.kruase.mipt.api.entities.comment.models.Comment;
import me.kruase.mipt.api.entities.comment.models.CommentId;
import me.kruase.mipt.api.entities.comment.models.CommentTest;
import me.kruase.mipt.api.entities.comment.models.NewComment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


public abstract class CommentRepositoryTest<I extends CommentRepository> {
    I repository;

    ArticleId articleId1;
    ArticleId articleId2;
    NewComment newComment1;
    NewComment newComment2;
    NewComment newComment3;
    NewComment newComment4;
    Comment referenceComment1;
    Comment referenceComment2;
    Comment referenceComment3;
    CommentId invalidCommentId;

    abstract I getImplementation();

    @BeforeEach
    void setUp() {
        repository = getImplementation();

        articleId1 = new ArticleId(123);
        articleId2 = new ArticleId(456);

        newComment1 = new NewComment(articleId1, "content_1");
        newComment2 = new NewComment(articleId1, "content_3");
        newComment3 = new NewComment(articleId2, "content_2");
        newComment4 = new NewComment(articleId2, "content_4");

        CommentId commentId1 = repository.create(newComment1);
        CommentId commentId2 = repository.create(newComment2);
        CommentId commentId3 = repository.create(newComment3);

        referenceComment1 = new Comment(commentId1, newComment1);
        referenceComment2 = new Comment(commentId2, newComment2);
        referenceComment3 = new Comment(commentId3, newComment3);

        invalidCommentId = new CommentId(0);
    }

    @Test
    void testFindById() {
        Comment foundComment1 = repository.findById(referenceComment1.id());
        Comment foundComment2 = repository.findById(referenceComment2.id());
        Comment foundComment3 = repository.findById(referenceComment3.id());

        CommentTest.assertDeepEquals(foundComment1, referenceComment1);
        CommentTest.assertDeepEquals(foundComment2, referenceComment2);
        CommentTest.assertDeepEquals(foundComment3, referenceComment3);
    }

    @Test
    void testFindByIdThrows() {
        assertThrows(
                CommentNotFoundException.class,
                () -> repository.findById(invalidCommentId)
        );
    }

    @Test
    void testCreate() {
        CommentId createdCommentId = repository.create(newComment4);

        Comment foundComment = repository.findById(createdCommentId);

        CommentTest.assertDeepEquals(foundComment, new Comment(createdCommentId, newComment4));
    }

    @Test
    void testDelete() {
        repository.delete(referenceComment1.id());

        assertThrows(
                CommentNotFoundException.class,
                () -> repository.findById(referenceComment1.id())
        );
        assertDoesNotThrow(() -> repository.findById(referenceComment2.id()));
        assertDoesNotThrow(() -> repository.findById(referenceComment3.id()));
    }

    @Test
    void testDeleteThrows() {
        assertThrows(
                CommentNotFoundException.class,
                () -> repository.delete(invalidCommentId)
        );
    }

    @Test
    void testDeleteAllByArticleId() {
        repository.deleteAllByArticleId(articleId1);

        assertThrows(
                CommentNotFoundException.class,
                () -> repository.findById(referenceComment1.id())
        );
        assertThrows(
                CommentNotFoundException.class,
                () -> repository.findById(referenceComment2.id())
        );
        assertDoesNotThrow(() -> repository.findById(referenceComment3.id()));
    }
}
