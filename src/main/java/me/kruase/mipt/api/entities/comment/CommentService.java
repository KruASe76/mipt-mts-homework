package me.kruase.mipt.api.entities.comment;


import me.kruase.mipt.api.entities.article.exceptions.ArticleNotFoundException;
import me.kruase.mipt.api.entities.article.models.Article;
import me.kruase.mipt.api.entities.article.models.ArticleId;
import me.kruase.mipt.api.entities.article.repositories.ArticleRepository;
import me.kruase.mipt.api.entities.comment.exceptions.CommentConflictException;
import me.kruase.mipt.api.entities.comment.exceptions.CommentCreateException;
import me.kruase.mipt.api.entities.comment.exceptions.CommentDeleteException;
import me.kruase.mipt.api.entities.comment.exceptions.CommentFindException;
import me.kruase.mipt.api.entities.comment.exceptions.CommentNotFoundException;
import me.kruase.mipt.api.entities.comment.models.Comment;
import me.kruase.mipt.api.entities.comment.models.CommentId;
import me.kruase.mipt.api.entities.comment.models.NewComment;
import me.kruase.mipt.api.entities.comment.repositories.CommentRepository;


public final class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    /**
     * @param articleRepository needed to add comments to and remove them from
     *                          the {@link Article} entities...
     *                          (weird requirement tbh)
     */
    public CommentService(
            CommentRepository commentRepository, ArticleRepository articleRepository
    ) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    public Comment findById(long commentId) throws CommentFindException {
        CommentId commentIdObject = new CommentId(commentId);

        try {
            return commentRepository.findById(commentIdObject);
        } catch (CommentNotFoundException e) {
            throw new CommentFindException(commentIdObject, e);
        }
    }

    public long create(long articleId, String content) throws CommentCreateException {
        ArticleId articleIdObject = new ArticleId(articleId);

        CommentId commentIdObject;
        try {
            commentIdObject = commentRepository.create(new NewComment(articleIdObject, content));
        } catch (CommentConflictException e) {
            throw new CommentCreateException(e);
        }

        Comment comment;
        try {
            comment = findById(commentIdObject.value());
        } catch (CommentFindException e) {
            throw new CommentCreateException(e);
        }

        try {
            articleRepository.addComment(articleIdObject, comment);
        } catch (ArticleNotFoundException e) {
            // reverting...
            try {
                commentRepository.delete(comment.id());
            } catch (CommentNotFoundException inner) {
                throw new CommentCreateException(inner);
            }

            throw new CommentCreateException(e);
        }

        return commentIdObject.value();
    }

    public void delete(long commentId) throws CommentDeleteException {
        Comment comment;
        try {
            comment = findById(commentId);
        } catch (CommentFindException e) {
            throw new CommentDeleteException(new CommentId(commentId), e);
        }

        try {
            articleRepository.removeComment(comment.articleId(), comment);
        } catch (ArticleNotFoundException e) {
            throw new CommentDeleteException(comment.id(), e);
        }

        try {
            commentRepository.delete(comment.id());
        } catch (CommentNotFoundException e) {
            // reverting...
            try {
                articleRepository.addComment(comment.articleId(), comment);
            } catch (ArticleNotFoundException inner) {
                throw new CommentDeleteException(comment.id(), inner);
            }

            throw new CommentDeleteException(comment.id(), e);
        }
    }
}
