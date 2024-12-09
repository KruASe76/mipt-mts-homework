package me.kruase.mipt.api.entities.article.repositories;


import me.kruase.mipt.api.entities.comment.repositories.InMemoryCommentRepository;


class InMemoryArticleRepositoryTest extends ArticleRepositoryTest<InMemoryArticleRepository> {
    @Override
    InMemoryArticleRepository getImplementation() {
        return new InMemoryArticleRepository(new InMemoryCommentRepository());
    }
}
