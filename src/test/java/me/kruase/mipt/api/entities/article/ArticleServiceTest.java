package me.kruase.mipt.api.entities.article;


import me.kruase.mipt.api.entities.article.exceptions.ArticleConflictException;
import me.kruase.mipt.api.entities.article.exceptions.ArticleCreateException;
import me.kruase.mipt.api.entities.article.exceptions.ArticleDeleteException;
import me.kruase.mipt.api.entities.article.exceptions.ArticleFindException;
import me.kruase.mipt.api.entities.article.exceptions.ArticleNotFoundException;
import me.kruase.mipt.api.entities.article.exceptions.ArticleUpdateException;
import me.kruase.mipt.api.entities.article.models.Article;
import me.kruase.mipt.api.entities.article.models.ArticleId;
import me.kruase.mipt.api.entities.article.models.ArticleTest;
import me.kruase.mipt.api.entities.article.models.NewArticle;
import me.kruase.mipt.api.entities.article.repositories.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    @Mock
    ArticleRepository articleRepository;

    @InjectMocks
    ArticleService service;

    NewArticle newArticle1;
    Article article1;
    Article article2;
    Article updateArticle1;

    @BeforeEach
    void setUp() {
        newArticle1 = new NewArticle("title_1", Set.of("tag_1", "tag_2"));
        NewArticle newArticle2 = new NewArticle("title_2", Set.of("tag_3", "tag_4"));

        article1 = new Article(new ArticleId(123), newArticle1);
        article2 = new Article(new ArticleId(456), newArticle2);
        updateArticle1 = new Article(article1.id(), newArticle2);
    }

    @Test
    void testFindAll() {
        List<Article> articles = List.of(article1, article2);
        Mockito.when(articleRepository.findAll()).thenReturn(articles);

        List<Article> foundArticles = service.findAll();

        Mockito.verify(articleRepository).findAll();
        ArticleTest.assertDeepEqualsMany(foundArticles, articles);
    }

    @Test
    void testFindById() {
        Mockito.when(articleRepository.findById(article1.id())).thenReturn(article1);

        Article foundArticle = service.findById(article1.id().value());

        Mockito.verify(articleRepository).findById(article1.id());
        ArticleTest.assertDeepEquals(foundArticle, article1);
    }

    @Test
    void testFindByIdThrows() {
        Mockito.when(articleRepository.findById(article1.id()))
                .thenThrow(ArticleNotFoundException.class);

        assertThrows(
                ArticleFindException.class,
                () -> service.findById(article1.id().value())
        );
        Mockito.verify(articleRepository).findById(article1.id());
    }

    @Test
    void testCreate() {
        Mockito.when(articleRepository.create(newArticle1)).thenReturn(article1.id());

        long createdArticleId = service.create(newArticle1.title(), newArticle1.tags());

        Mockito.verify(articleRepository).create(newArticle1);
        assertEquals(createdArticleId, article1.id().value());
    }

    @Test
    void testCreateThrows() {
        Mockito.when(articleRepository.create(newArticle1))
                .thenThrow(ArticleConflictException.class);

        assertThrows(
                ArticleCreateException.class,
                () -> service.create(newArticle1.title(), newArticle1.tags())
        );
        Mockito.verify(articleRepository).create(newArticle1);
    }

    @Test
    void testUpdate() {
        Mockito.when(articleRepository.findById(article1.id())).thenReturn(article1);
        Mockito.doNothing().when(articleRepository).update(updateArticle1);

        service.update(article1.id().value(), updateArticle1.title(), updateArticle1.tags());

        Mockito.verify(articleRepository).findById(article1.id());
        Mockito.verify(articleRepository).update(updateArticle1);
    }

    @Test
    void testUpdateThrows() {
        Mockito.when(articleRepository.findById(article1.id())).thenReturn(article1);
        Mockito.doThrow(ArticleNotFoundException.class)
                .when(articleRepository).update(updateArticle1);

        assertThrows(
                ArticleUpdateException.class,
                () -> service.update(
                        article1.id().value(),
                        updateArticle1.title(),
                        updateArticle1.tags()
                )
        );
        Mockito.verify(articleRepository).update(updateArticle1);
    }

    @Test
    void testDelete() {
        Mockito.doNothing().when(articleRepository).delete(article1.id());

        service.delete(article1.id().value());

        Mockito.verify(articleRepository).delete(article1.id());
    }

    @Test
    void testDeleteThrows() {
        Mockito.doThrow(ArticleNotFoundException.class)
                .when(articleRepository).delete(article1.id());

        assertThrows(
                ArticleDeleteException.class,
                () -> service.delete(article1.id().value())
        );
        Mockito.verify(articleRepository).delete(article1.id());
    }
}
