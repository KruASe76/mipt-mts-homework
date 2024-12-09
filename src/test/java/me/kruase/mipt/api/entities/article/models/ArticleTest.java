package me.kruase.mipt.api.entities.article.models;


import me.kruase.mipt.api.entities.comment.models.Comment;
import me.kruase.mipt.api.entities.comment.models.CommentId;
import me.kruase.mipt.api.entities.comment.models.CommentTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class ArticleTest {
    ArticleId articleId;
    Article article;

    @BeforeEach
    void setUp() {
        articleId = new ArticleId(123);
        article = new Article(
                articleId,
                "title",
                Set.of("tag_1", "tag_2"),
                List.of(
                        new Comment(
                                new CommentId(123),
                                articleId,
                                "comment_1"
                        ),
                        new Comment(
                                new CommentId(456),
                                articleId,
                                "comment_2"
                        )
                )
        );
    }

    @Test
    void testArticleCreation() {
        NewArticle newArticle = new NewArticle("new_title", Set.of("tag_3", "tag_4"));

        Article createdArticle = new Article(articleId, newArticle);

        assertEquals(articleId, createdArticle.id());
        assertEquals(newArticle.title(), createdArticle.title());
        assertEquals(newArticle.tags(), createdArticle.tags());
        assertIterableEquals(List.of(), createdArticle.comments());
    }

    @Test
    void testWithTitle() {
        String newTitle = "new_title";

        Article newArticle = article.withTitle(newTitle);

        assertEquals(article.id(), newArticle.id());
        assertEquals(newTitle, newArticle.title());
        assertEquals(article.tags(), newArticle.tags());
        assertEquals(article.comments(), newArticle.comments());
    }

    @Test
    void testWithTags() {
        Set<String> newTags = Set.of("tag_3", "tag_4");

        Article newArticle = article.withTags(newTags);

        assertEquals(article.id(), newArticle.id());
        assertEquals(article.title(), newArticle.title());
        assertEquals(newTags, newArticle.tags());
        assertEquals(article.comments(), newArticle.comments());
    }

    @Test
    void testAddComment() {
        Comment addedComment = new Comment(
                new CommentId(789),
                articleId,
                "comment_3"
        );

        Article newArticle = article.addComment(addedComment);

        ArrayList<Comment> newCommentList = new ArrayList<>(article.comments());
        newCommentList.add(addedComment);

        assertEquals(article.id(), newArticle.id());
        assertEquals(article.title(), newArticle.title());
        assertEquals(article.tags(), newArticle.tags());
        assertIterableEquals(newCommentList, newArticle.comments());
    }

    @Test
    void testRemoveComment() {
        Comment removedComment = article.comments().get(0);

        Article newArticle = article.removeComment(removedComment);

        ArrayList<Comment> newCommentList = new ArrayList<>(article.comments());
        newCommentList.remove(removedComment);

        assertEquals(article.id(), newArticle.id());
        assertEquals(article.title(), newArticle.title());
        assertEquals(article.tags(), newArticle.tags());
        assertIterableEquals(newCommentList, newArticle.comments());
    }

    public static void assertDeepEquals(Article expected, Article actual) {
        if (expected == actual) {
            return;
        }

        assertNotNull(expected);
        assertNotNull(actual);

        assertEquals(expected.id(), actual.id());
        assertEquals(expected.title(), actual.title());
        assertEquals(expected.tags(), actual.tags());

        CommentTest.assertDeepEqualsMany(expected.comments(), actual.comments());
    }

    public static void assertDeepNotEquals(Article expected, Article actual) {
        assertThrows(
                AssertionFailedError.class,
                () -> assertDeepEquals(expected, actual)
        );
    }

    public static void assertDeepEqualsMany(
            Iterable<Article> expectedIterable, Iterable<Article> actualIterable
    ) {
        if (expectedIterable == actualIterable) {
            return;
        }

        assertNotNull(expectedIterable);
        assertNotNull(actualIterable);

        Iterator<Article> expectedIterator = expectedIterable.iterator();
        Iterator<Article> actualIterator = actualIterable.iterator();

        while (expectedIterator.hasNext() && actualIterator.hasNext()) {
            assertDeepEquals(expectedIterator.next(), actualIterator.next());
        }

        assertFalse(expectedIterator.hasNext());
        assertFalse(actualIterator.hasNext());
    }
}
