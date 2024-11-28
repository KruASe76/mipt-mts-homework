package me.kruase.mipt.api.entities.article;


import me.kruase.mipt.api.entities.article.exceptions.ArticleConflictException;
import me.kruase.mipt.api.entities.article.exceptions.ArticleCreateException;
import me.kruase.mipt.api.entities.article.exceptions.ArticleDeleteException;
import me.kruase.mipt.api.entities.article.exceptions.ArticleFindException;
import me.kruase.mipt.api.entities.article.exceptions.ArticleNotFoundException;
import me.kruase.mipt.api.entities.article.exceptions.ArticleUpdateException;
import me.kruase.mipt.api.entities.article.models.Article;
import me.kruase.mipt.api.entities.article.models.ArticleId;
import me.kruase.mipt.api.entities.article.models.NewArticle;
import me.kruase.mipt.api.entities.article.repositories.ArticleRepository;

import java.util.List;
import java.util.Set;


public final class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article findById(long articleId) throws ArticleFindException {
        ArticleId articleIdObject = new ArticleId(articleId);

        try {
            return articleRepository.findById(articleIdObject);
        } catch (ArticleNotFoundException e) {
            throw new ArticleFindException(articleIdObject, e);
        }
    }

    public long create(String title, Set<String> tags) throws ArticleCreateException {
        try {
            return articleRepository.create(new NewArticle(title, tags)).value();
        } catch (ArticleConflictException e) {
            throw new ArticleCreateException(e);
        }
    }

    public void update(long articleId, String title, Set<String> tags)
            throws ArticleUpdateException {
        Article article;
        try {
            article = findById(articleId);
        } catch (ArticleFindException e) {
            throw new ArticleUpdateException(new ArticleId(articleId), e);
        }

        try {
            articleRepository.update(
                    article
                            .withTitle(title)
                            .withTags(tags)
            );
        } catch (ArticleNotFoundException e) {
            throw new ArticleUpdateException(article.id(), e);
        }
    }

    public void delete(long articleId) throws ArticleDeleteException {
        ArticleId articleIdObject = new ArticleId(articleId);

        try {
            articleRepository.delete(articleIdObject);
        } catch (ArticleNotFoundException e) {
            throw new ArticleDeleteException(articleIdObject, e);
        }
    }
}
