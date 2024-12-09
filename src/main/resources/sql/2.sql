SELECT post.post_id
FROM post JOIN comment ON post.post_id = comment.post_id
WHERE regexp_like(post.title, '^\d.*$') AND length(post.content) > 20
GROUP BY post.post_id
HAVING count(comment.comment_id) = 2
ORDER BY post.post_id;
