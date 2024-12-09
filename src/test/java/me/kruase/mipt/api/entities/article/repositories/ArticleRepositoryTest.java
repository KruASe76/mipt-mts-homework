package me.kruase.mipt.api.entities.article.repositories;


import me.kruase.mipt.api.entities.article.exceptions.ArticleNotFoundException;
import me.kruase.mipt.api.entities.article.models.Article;
import me.kruase.mipt.api.entities.article.models.ArticleId;
import me.kruase.mipt.api.entities.article.models.ArticleTest;
import me.kruase.mipt.api.entities.article.models.NewArticle;
import me.kruase.mipt.api.entities.comment.models.Comment;
import me.kruase.mipt.api.entities.comment.models.CommentId;
import me.kruase.mipt.api.entities.comment.models.CommentTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;


public abstract class ArticleRepositoryTest<I extends ArticleRepository> {
    I repository;

    NewArticle newArticle1;
    NewArticle newArticle2;
    NewArticle newArticle3;
    Article referenceArticle1;
    Article referenceArticle2;
    ArticleId invalidArticleId;

    abstract I getImplementation();

    @BeforeEach
    void setUp() {
        repository = getImplementation();

        newArticle1 = new NewArticle("title_1", Set.of("tag_1", "tag_2"));
        newArticle2 = new NewArticle("title_2", Set.of("tag_2", "tag_3"));
        newArticle3 = new NewArticle("title_3", Set.of("tag_3", "tag_4"));

        ArticleId articleId1 = repository.create(newArticle1);
        ArticleId articleId2 = repository.create(newArticle2);

        Comment comment1 = new Comment(new CommentId(123), articleId1, "comment_1");
        Comment comment2 = new Comment(new CommentId(456), articleId1, "comment_2");
        repository.addComment(articleId1, comment1);
        repository.addComment(articleId1, comment2);

        referenceArticle1 = new Article(articleId1, newArticle1);
        referenceArticle1 = referenceArticle1.addComment(comment1);
        referenceArticle1 = referenceArticle1.addComment(comment2);

        referenceArticle2 = new Article(articleId2, newArticle2);

        invalidArticleId = new ArticleId(0);
    }

    @Test
    void testFindAll() {
        List<Article> allArticles = repository.findAll();

        ArticleTest.assertDeepEqualsMany(
                allArticles, List.of(referenceArticle1, referenceArticle2)
        );
    }

    @Test
    void testFindById() {
        Article foundArticle1 = repository.findById(referenceArticle1.id());
        Article foundArticle2 = repository.findById(referenceArticle2.id());

        ArticleTest.assertDeepEquals(foundArticle1, referenceArticle1);
        ArticleTest.assertDeepEquals(foundArticle2, referenceArticle2);
    }

    @Test
    void testFindByIdThrows() {
        assertThrows(
                ArticleNotFoundException.class,
                () -> repository.findById(invalidArticleId)
        );
    }

    @Test
    void testCreate() {
        ArticleId createdArticleId = repository.create(newArticle3);

        // test id increment: referenceArticle2 was the last created article
        assertEquals(createdArticleId.value(), referenceArticle2.id().value() + 1);

        Article foundArticle = repository.findById(createdArticleId);

        ArticleTest.assertDeepEquals(foundArticle, new Article(createdArticleId, newArticle3));
    }

    @Test
    void testUpdate() {
        Article updateArticle = new Article(referenceArticle1.id(), newArticle3);

        repository.update(updateArticle);

        Article updatedArticle1 = repository.findById(referenceArticle1.id());

        ArticleTest.assertDeepEquals(updatedArticle1, updateArticle);

        // articles would be equal if compared by id only, but not if compared deeply
        assertEquals(updatedArticle1, referenceArticle1);
        ArticleTest.assertDeepNotEquals(updatedArticle1, referenceArticle1);
    }

    @Test
    void testUpdateThrows() {
        Article updateArticle = new Article(invalidArticleId, newArticle3);

        assertThrows(
                ArticleNotFoundException.class,
                () -> repository.update(updateArticle)
        );
    }

    @Test
    void testDelete() {
        repository.delete(referenceArticle1.id());

        assertThrows(
                ArticleNotFoundException.class,
                () -> repository.findById(referenceArticle1.id())
        );
        assertDoesNotThrow(() -> repository.findById(referenceArticle2.id()));
    }

    @Test
    void testDeleteThrows() {
        assertThrows(
                ArticleNotFoundException.class,
                () -> repository.delete(invalidArticleId)
        );
    }

    @Test
    void testAddComment() {
        Comment commentToAdd =
                new Comment(new CommentId(789), referenceArticle1.id(), "comment_3");

        repository.addComment(referenceArticle1.id(), commentToAdd);

        Article updatedArticle1 = repository.findById(referenceArticle1.id());

        assertEquals(updatedArticle1.comments().size(), referenceArticle1.comments().size() + 1);
        CommentTest.assertDeepEquals(
                updatedArticle1.comments().get(updatedArticle1.comments().size() - 1),
                commentToAdd
        );
    }

    @Test
    void testAddCommentThrows() {
        Comment commentToAdd =
                new Comment(new CommentId(789), invalidArticleId, "comment_3");

        assertThrows(
                ArticleNotFoundException.class,
                () -> repository.addComment(invalidArticleId, commentToAdd)
        );
    }

    @Test
    void testRemoveComment() {
        Comment commentToDelete = referenceArticle1.comments().get(0);

        repository.removeComment(referenceArticle1.id(), commentToDelete);

        Article updatedArticle1 = repository.findById(referenceArticle1.id());

        assertEquals(updatedArticle1.comments().size(), referenceArticle1.comments().size() - 1);
        assertFalse(updatedArticle1.comments().contains(commentToDelete));
    }


    @Test
    void testRemoveCommentThrows() {
        Comment commentToRemove =
                new Comment(new CommentId(789), invalidArticleId, "comment_3");

        assertThrows(
                ArticleNotFoundException.class,
                () -> repository.removeComment(invalidArticleId, commentToRemove)
        );
    }
}
