package main.repository;

import main.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

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
            "   match(posts.text, title) against(?1)", nativeQuery = true)
    Integer getQueriedPostsCount(String query); //Вернул 2 , но вывелся только 1 пост (последнее верно)
}
