package main.repository;

import main.dto.PostInterface;
import main.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Integer> {

    @Query(value = "SELECT COUNT(*) FROM posts where is_active = 1 and moderation_status = 'ACCEPTED' and time <= curdate()", nativeQuery = true)
    Integer getPostsCount();

}
