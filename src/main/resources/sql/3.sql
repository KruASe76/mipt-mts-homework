SELECT post.post_id
FROM post FULL JOIN comment ON post.post_id = comment.post_id
GROUP BY post.post_id
HAVING count(post.post_id) <= 1
ORDER BY post.post_id
LIMIT 10;
