package main.repository;

import main.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    @Query(value = "SELECT COUNT(*) FROM posts where is_active = 1 and moderation_status = 'ACCEPTED' and time <= curdate()", nativeQuery = true)
    Integer getPostsCount();

    @Query(value = "SELECT" +
            "   COUNT(*) " +
            "FROM " +
            "   posts " +
            "where " +
            "   is_active = 1 " +
            "and " +
            "   moderation_status = 'ACCEPTED' " +
            "and " +
            "   time <= curdate()" +
            "and" +
            "   match(posts.text, title) against(:query)", nativeQuery = true)
    Integer getQueriedPostsCount(@Param("query") String query);

    @Query(value = "SELECT year(time) year FROM blogengine.posts group by year order by year;", nativeQuery = true)
    List<Integer> getYears();




}
