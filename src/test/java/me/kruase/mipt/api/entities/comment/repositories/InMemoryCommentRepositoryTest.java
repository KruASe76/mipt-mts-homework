package me.kruase.mipt.api.entities.comment.repositories;


class InMemoryCommentRepositoryTest extends CommentRepositoryTest<InMemoryCommentRepository> {
    @Override
    InMemoryCommentRepository getImplementation() {
        return new InMemoryCommentRepository();
    }
}
