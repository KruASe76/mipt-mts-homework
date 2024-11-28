package me.kruase.mipt.api.entities.comment;


import me.kruase.mipt.api.entities.article.exceptions.ArticleNotFoundException;
import me.kruase.mipt.api.entities.article.models.Article;
import me.kruase.mipt.api.entities.article.models.ArticleId;
import me.kruase.mipt.api.entities.article.models.NewArticle;
import me.kruase.mipt.api.entities.article.repositories.ArticleRepository;
import me.kruase.mipt.api.entities.comment.exceptions.CommentConflictException;
import me.kruase.mipt.api.entities.comment.exceptions.CommentCreateException;
import me.kruase.mipt.api.entities.comment.exceptions.CommentDeleteException;
import me.kruase.mipt.api.entities.comment.exceptions.CommentFindException;
import me.kruase.mipt.api.entities.comment.exceptions.CommentNotFoundException;
import me.kruase.mipt.api.entities.comment.models.Comment;
import me.kruase.mipt.api.entities.comment.models.CommentId;
import me.kruase.mipt.api.entities.comment.models.CommentTest;
import me.kruase.mipt.api.entities.comment.models.NewComment;
import me.kruase.mipt.api.entities.comment.repositories.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @Mock
    CommentRepository commentRepository;

    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    CommentService service;

    Article article;
    NewComment newComment;
    Comment comment;

    @BeforeEach
    void setUp() {
        article = new Article(
                new ArticleId(123),
                new NewArticle("title", Set.of("tag_1", "tag_2"))
        );

        newComment = new NewComment(article.id(), "content");
        comment = new Comment(new CommentId(123), newComment);
    }

    @Test
    void testFindById() {
        Mockito.when(commentRepository.findById(comment.id())).thenReturn(comment);

        Comment foundComment = service.findById(comment.id().value());

        Mockito.verify(commentRepository).findById(comment.id());
        CommentTest.assertDeepEquals(foundComment, comment);
    }

    @Test
    void testFindByIdThrows() {
        Mockito.when(commentRepository.findById(comment.id()))
                .thenThrow(CommentNotFoundException.class);

        assertThrows(
                CommentFindException.class,
                () -> service.findById(comment.id().value())
        );
        Mockito.verify(commentRepository).findById(comment.id());
    }

    @Test
    void testCreate() {
        Mockito.when(commentRepository.create(newComment)).thenReturn(comment.id());
        Mockito.when(commentRepository.findById(comment.id())).thenReturn(comment);
        Mockito.doNothing().when(articleRepository).addComment(article.id(), comment);

        long createdCommentId =
                service.create(newComment.articleId().value(), newComment.content());

        Mockito.verify(commentRepository).create(newComment);
        Mockito.verify(commentRepository).findById(comment.id());
        Mockito.verify(articleRepository).addComment(comment.articleId(), comment);
        assertEquals(createdCommentId, comment.id().value());
    }

    @Test
    void testCreateThrows_DueToConflict() {
        Mockito.when(commentRepository.create(newComment))
                .thenThrow(CommentConflictException.class);

        assertThrows(
                CommentCreateException.class,
                () -> service.create(newComment.articleId().value(), newComment.content())
        );
        Mockito.verify(commentRepository).create(newComment);
    }

    @Test
    void testCreateThrows_DueToCommentNotFound() {
        Mockito.when(commentRepository.create(newComment)).thenReturn(comment.id());
        Mockito.when(commentRepository.findById(comment.id()))
                .thenThrow(CommentNotFoundException.class);

        assertThrows(
                CommentCreateException.class,
                () -> service.create(newComment.articleId().value(), newComment.content())
        );
        Mockito.verify(commentRepository).create(newComment);
        Mockito.verify(commentRepository).findById(comment.id());
    }

    @Test
    void testCreateThrows_DueToArticleNotFound() {
        Mockito.when(commentRepository.create(newComment)).thenReturn(comment.id());
        Mockito.when(commentRepository.findById(comment.id())).thenReturn(comment);
        Mockito.doThrow(ArticleNotFoundException.class)
                .when(articleRepository).addComment(article.id(), comment);
        Mockito.doNothing().when(commentRepository).delete(comment.id());

        assertThrows(
                CommentCreateException.class,
                () -> service.create(newComment.articleId().value(), newComment.content())
        );
        Mockito.verify(commentRepository).create(newComment);
        Mockito.verify(commentRepository).findById(comment.id());
        Mockito.verify(articleRepository).addComment(comment.articleId(), comment);
        Mockito.verify(commentRepository).delete(comment.id());
    }

    @Test
    void testCreateThrows_DueToArticleNotFound_AndCommentNotFound() {
        Mockito.when(commentRepository.create(newComment)).thenReturn(comment.id());
        Mockito.when(commentRepository.findById(comment.id())).thenReturn(comment);
        Mockito.doThrow(ArticleNotFoundException.class)
                .when(articleRepository).addComment(article.id(), comment);
        Mockito.doThrow(CommentNotFoundException.class)
                .when(commentRepository).delete(comment.id());

        assertThrows(
                CommentCreateException.class,
                () -> service.create(newComment.articleId().value(), newComment.content())
        );
        Mockito.verify(commentRepository).create(newComment);
        Mockito.verify(commentRepository).findById(comment.id());
        Mockito.verify(articleRepository).addComment(comment.articleId(), comment);
        Mockito.verify(commentRepository).delete(comment.id());
    }

    @Test
    void testDelete() {
        Mockito.when(commentRepository.findById(comment.id())).thenReturn(comment);
        Mockito.doNothing().when(articleRepository).removeComment(article.id(), comment);
        Mockito.doNothing().when(commentRepository).delete(comment.id());

        service.delete(comment.id().value());

        Mockito.verify(commentRepository).findById(comment.id());
        Mockito.verify(articleRepository).removeComment(article.id(), comment);
        Mockito.verify(commentRepository).delete(comment.id());
    }

    @Test
    void testDeleteThrows_DueToCommentNotFound() {
        Mockito.when(commentRepository.findById(comment.id()))
                .thenThrow(CommentNotFoundException.class);

        assertThrows(
                CommentDeleteException.class,
                () -> service.delete(comment.id().value())
        );
        Mockito.verify(commentRepository).findById(comment.id());
    }

    @Test
    void testDeleteThrows_DueToArticleNotFound() {
        Mockito.when(commentRepository.findById(comment.id())).thenReturn(comment);
        Mockito.doThrow(ArticleNotFoundException.class)
                .when(articleRepository).removeComment(article.id(), comment);

        assertThrows(
                CommentDeleteException.class,
                () -> service.delete(comment.id().value())
        );
        Mockito.verify(commentRepository).findById(comment.id());
        Mockito.verify(articleRepository).removeComment(article.id(), comment);
    }

    @Test
    void testDeleteThrows_OnDelete_DueToCommentNotFound() {
        Mockito.when(commentRepository.findById(comment.id())).thenReturn(comment);
        Mockito.doNothing().when(articleRepository).removeComment(article.id(), comment);
        Mockito.doThrow(CommentNotFoundException.class)
                .when(commentRepository).delete(comment.id());
        Mockito.doNothing().when(articleRepository).addComment(article.id(), comment);

        assertThrows(
                CommentDeleteException.class,
                () -> service.delete(comment.id().value())
        );
        Mockito.verify(commentRepository).findById(comment.id());
        Mockito.verify(articleRepository).removeComment(article.id(), comment);
        Mockito.verify(commentRepository).delete(comment.id());
        Mockito.verify(articleRepository).addComment(article.id(), comment);
    }

    @Test
    void testDeleteThrows_OnDelete_DueToCommentNotFound_AndArticleNotFound() {
        Mockito.when(commentRepository.findById(comment.id())).thenReturn(comment);
        Mockito.doNothing().when(articleRepository).removeComment(article.id(), comment);
        Mockito.doThrow(CommentNotFoundException.class)
                .when(commentRepository).delete(comment.id());
        Mockito.doThrow(ArticleNotFoundException.class)
                .when(articleRepository).addComment(article.id(), comment);

        assertThrows(
                CommentDeleteException.class,
                () -> service.delete(comment.id().value())
        );
        Mockito.verify(commentRepository).findById(comment.id());
        Mockito.verify(articleRepository).removeComment(article.id(), comment);
        Mockito.verify(commentRepository).delete(comment.id());
        Mockito.verify(articleRepository).addComment(article.id(), comment);
    }
}
