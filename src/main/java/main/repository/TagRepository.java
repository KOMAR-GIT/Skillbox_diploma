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
            "    tag2post " +
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

    @Query(value = "WITH " +
            "   total_post_count " +
            "   AS " +
            "       (SELECT " +
            "           count(*) " +
            "       FROM " +
            "           posts p " +
            "       where " +
            "           is_active = 1  " +
            "       and " +
            "           moderation_status = 'ACCEPTED'  " +
            "       and " +
            "           p.time <= utc_timestamp()), " +
            "    weights" +
            "    AS" +
            "       (SELECT " +
            "           tag_id," +
            "           t.name as name, " +
            "           (count(tp.tag_id) / " +
            "               (SELECT " +
            "                   * " +
            "                FROM " +
            "                   total_post_count)) tagWeight " +
            "        FROM " +
            "           tag2post tp " +
            "           JOIN tags t on tp.tag_id = t.id " +
            "           JOIN posts p on tp.post_id = p.id " +
            "        WHERE p.is_active = 1 " +
            "           AND p.moderation_status = 'ACCEPTED' " +
            "           AND p.time <= utc_timestamp() " +
            "        GROUP BY tp.tag_id)" +
            " SELECT " +
            "   name," +
            "   (tagWeight * " +
            "       (1 / " +
            "           (SELECT " +
            "               tagWeight " +
            "            FROM " +
            "               weights " +
            "            order by " +
            "               tagWeight desc " +
            "            limit 1))) as weight" +
            " FROM " +
            "   weights", nativeQuery = true)
    List<TagInterface> getAllTags();

    List<Tag> findTagsByNameIn(List<String> tags);

}
