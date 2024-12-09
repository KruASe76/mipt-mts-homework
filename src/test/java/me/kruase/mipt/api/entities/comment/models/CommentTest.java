package me.kruase.mipt.api.entities.comment.models;


import me.kruase.mipt.api.entities.article.models.ArticleId;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class CommentTest {
    @Test
    void testCommentCreation() {
        CommentId commentId = new CommentId(123);
        NewComment newComment = new NewComment(new ArticleId(456), "content");

        Comment createdComment = new Comment(commentId, newComment);

        assertEquals(commentId, createdComment.id());
        assertEquals(newComment.articleId(), createdComment.articleId());
        assertEquals(newComment.content(), createdComment.content());
    }

    public static void assertDeepEquals(Comment expected, Comment actual) {
        if (expected == actual) {
            return;
        }

        assertNotNull(expected);
        assertNotNull(actual);

        assertEquals(expected.id(), actual.id());
        assertEquals(expected.articleId(), actual.articleId());
        assertEquals(expected.content(), actual.content());
    }

    public static void assertDeepEqualsMany(
            Iterable<Comment> expectedIterable, Iterable<Comment> actualIterable
    ) {
        if (expectedIterable == actualIterable) {
            return;
        }

        assertNotNull(expectedIterable);
        assertNotNull(actualIterable);

        Iterator<Comment> expectedIterator = expectedIterable.iterator();
        Iterator<Comment> actualIterator = actualIterable.iterator();

        while (expectedIterator.hasNext() && actualIterator.hasNext()) {
            assertDeepEquals(expectedIterator.next(), actualIterator.next());
        }

        assertFalse(expectedIterator.hasNext());
        assertFalse(actualIterator.hasNext());
    }
}
