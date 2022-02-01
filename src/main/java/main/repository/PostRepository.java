package main.repository;

import main.dto.interfaces.PostInterface;
import main.model.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    String countQuery = "SELECT " +
            "   COUNT(*) " +
            " FROM " +
            "   posts p" +
            " where " +
            "   is_active = 1 ";
    String filterQuery = " and " +
            "   moderation_status = 'ACCEPTED'" +
            " and " +
            "   time <= curdate()";

    @Query(value = "SELECT " +
            "   p.id as id," +
            "   p.time as timestamp," +
            "   p.is_active as active, " +
            "   u.id as userId," +
            "   u.name userName," +
            "   p.title as title," +
            "   p.text as text," +
            "   (SELECT COUNT(*) FROM post_votes pv WHERE pv.value = TRUE) AS likeCount," +
            "   (SELECT COUNT(*) FROM post_votes pv WHERE pv.value = FALSE) AS dislikeCount, " +
            "   p.view_count as viewCount " +
            " FROM" +
            "   posts p " +
            " LEFT JOIN " +
            "   users u ON p.user_id = u.id " +
            " LEFT JOIN " +
            "   post_votes pv ON p.id = pv.post_id " +
            " WHERE " +
            "   is_active = 1 " +
            " and " +
            "   moderation_status = 'ACCEPTED' " +
            " and " +
            "   p.time <= curdate() " +
            " and " +
            "   p.id = :id" +
            " group by " +
            "   p.id", nativeQuery = true)
    PostInterface getPostById(@Param("id") int id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE blogengine.posts SET view_count = view_count + 1 WHERE (id = :post_id);", nativeQuery = true)
    void increasePostViewsCount(@Param("post_id") int postId);

    @Query(value = countQuery + filterQuery, nativeQuery = true)
    Integer getPostsCount();

    @Query(value = countQuery, nativeQuery = true)
    Integer getPostsForModerationCount();

    @Query(value = "SELECT COUNT(*) FROM posts p where :where ", nativeQuery = true)
    Integer getUserPostsCount(@Param("where") String where);

    @Query(value = countQuery
            + filterQuery
            + " and match(p.text, title) against(:query)", nativeQuery = true)
    Integer getQueriedPostsCount(@Param("query") String query);

    @Query(value = countQuery
            + filterQuery
            + " and DATE(p.time) = :date", nativeQuery = true)
    Integer getPostsCountByDate(@Param("date") String date);

    @Query(value = "SELECT " +
            "    count(*)" +
            " FROM " +
            "    blogengine.posts p" +
            "    join tag2post tp on p.id = post_id" +
            " WHERE" +
            "    p.id in (select post_id from tag2post join tags t on tag_id = t.id where t.name = :tag)" +
            " AND" +
            "    p.is_active = 1 "
            + filterQuery +
            " GROUP BY tp.tag_id", nativeQuery = true)
    Integer getPostsCountByTag(@Param("tag") String tag);

    @Query(value = "SELECT year(time) year FROM blogengine.posts " +
            "WHERE is_active = 1 " +
            "   and moderation_status = 'ACCEPTED' " +
            "   and time <= curdate() " +
            "group by year order by year;", nativeQuery = true)
    List<Integer> getYears();



}
