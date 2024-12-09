SELECT count(profile.profile_id)
FROM profile FULL JOIN post ON profile.profile_id = post.profile_id
GROUP BY post.profile_id
HAVING count(post.post_id) = 0;
