package main.repository;

import main.dto.interfaces.TagInterface;
import main.model.Tag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends CrudRepository<Tag, Integer> {

    @Query(value = "SELECT " +
            "    t.name AS name, " +
            "    (SELECT count(p.id) WHERE p.id = post_id) AS postsCount " +
            "FROM " +
            "    blogengine.tag2post " +
            "JOIN tags t ON t.id = tag_id " +
            "JOIN posts p ON p.id = post_id " +
            "WHERE name like '?1%' " +
            "GROUP BY t.name", nativeQuery = true)
    List<TagInterface> getTagsByQuery(String query);

    @Query(value = "SELECT " +
            "    t.name AS name " +
            " FROM " +
            "    tag2post " +
            " JOIN tags t ON t.id = tag_id " +
            " JOIN posts p ON p.id = post_id " +
            " WHERE post_id = :postId", nativeQuery = true)
    List<String> getPostTags(@Param("postId") int postId);

    @Query(value = "WITH total_post_count AS (SELECT count(*) FROM posts)," +
            "most_popular_tag_count AS " +
            "   (SELECT " +
            "       count(t.id) AS postCount " +
            "   FROM tag2post " +
            "   JOIN tags t ON t.id = tag_id " +
            "   GROUP BY t.id " +
            "   order by postCount " +
            "   limit 1) " +
            "SELECT " +
            "   t.name AS name, " +
            "   (COUNT(t.id) / (SELECT " +
            "            *" +
            "        FROM" +
            "            total_post_count)) * (1 / ((SELECT " +
            "            * " +
            "        FROM " +
            "            most_popular_tag_count) / (SELECT " +
            "            * " +
            "        FROM " +
            "            total_post_count))) AS weight " +
            "FROM tag2post JOIN tags t ON t.id = tag_id" +
            " GROUP BY t.id;", nativeQuery = true)
    List<TagInterface> getAllTags();

}
