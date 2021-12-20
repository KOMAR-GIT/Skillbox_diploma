package main.repository;

import main.dto.PostInterface;
import main.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

    @Query(value = "SELECT " +
            "   p.id," +
            "   p.time as timestamp," +
            "   u.id as userId," +
            "   u.name," +
            "   p.title," +
            "   p.text as announce," +
            "   p.view_count as viewCount," +
            "   COUNT(pc.id) AS commentCount," +
            "   (SELECT COUNT(*) FROM post_votes pv WHERE pv.value = TRUE) AS likeCount," +
            "   (SELECT COUNT(*) FROM post_votes pv WHERE pv.value = FALSE) AS dislikeCount " +
            "FROM" +
            "   blogengine.posts p " +
            "LEFT JOIN " +
            "   post_comments pc ON post_id = p.id " +
            "LEFT JOIN " +
            "   users u ON p.user_id = u.id " +
            "WHERE " +
            "   is_active = 1 " +
            "GROUP BY p.id " +
            "ORDER BY :sort", nativeQuery = true)
    Page<PostInterface> getPostsOrderBy(@Param("sort") String sort, Pageable pageable);

}
