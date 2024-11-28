package me.kruase.mipt.api.entities.article.repositories;


import me.kruase.mipt.api.entities.article.exceptions.ArticleConflictException;
import me.kruase.mipt.api.entities.article.exceptions.ArticleNotFoundException;
import me.kruase.mipt.api.entities.article.models.Article;
import me.kruase.mipt.api.entities.article.models.ArticleId;
import me.kruase.mipt.api.entities.article.models.NewArticle;
import me.kruase.mipt.api.entities.comment.models.Comment;
import me.kruase.mipt.api.entities.comment.repositories.CommentRepository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


public final class InMemoryArticleRepository implements ArticleRepository {
    private final AtomicLong lastId = new AtomicLong(0);
    private final Map<ArticleId, Article> storage = new ConcurrentHashMap<>();

    private final CommentRepository commentRepository;

    /**
     * @param commentRepository needed to simulate {@code ON DELETE ...} SQL behavior
     *                          as {@link Comment}
     *                          is dependent on {@link Article}
     */
    public InMemoryArticleRepository(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    private ArticleId generateId() {
        return new ArticleId(lastId.incrementAndGet());
    }

    @Override
    public List<Article> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public Article findById(ArticleId articleId) throws ArticleNotFoundException {
        Article article = storage.get(articleId);

        if (article == null) {
            throw new ArticleNotFoundException(articleId);
        }

        return article;
    }

    @Override
    public synchronized ArticleId create(NewArticle newArticle) throws ArticleConflictException {
        ArticleId articleId = generateId();

        if (storage.get(articleId) != null) {
            throw new ArticleConflictException(articleId);
        }

        Article article = new Article(articleId, newArticle);

        storage.put(articleId, article);

        return articleId;
    }

    @Override
    public synchronized void update(Article article) throws ArticleNotFoundException {
        if (storage.get(article.id()) == null) {
            throw new ArticleNotFoundException(article.id());
        }

        storage.put(article.id(), article);
    }

    @Override
    public synchronized void delete(ArticleId articleId) throws ArticleNotFoundException {
        if (storage.remove(articleId) == null) {
            throw new ArticleNotFoundException(articleId);
        }

        commentRepository.deleteAllByArticleId(articleId);
    }

    @Override
    public synchronized void addComment(ArticleId articleId, Comment comment)
            throws ArticleNotFoundException {
        Article article = findById(articleId);

        Article newArticle = article.addComment(comment);

        storage.put(articleId, newArticle);
    }

    @Override
    public synchronized void removeComment(ArticleId articleId, Comment comment)
            throws ArticleNotFoundException {
        Article article = findById(articleId);

        Article newArticle = article.removeComment(comment);

        storage.put(articleId, newArticle);
    }
}
