package main.repository;

import main.dto.TagInterface;
import main.model.Tag2Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Tag2PostRepository extends CrudRepository<Tag2Post, Integer> {


    @Query(value = "SELECT " +
            "    t.name as name, " +
            "    (select count(p.id) where p.id = post_id) as postsCount " +
            "FROM " +
            "    blogengine.tag2post " +
            "join tags t on t.id = tag_id " +
            "join posts p on p.id = post_id " +
            "where name like '?1%' " +
            "group by t.name", nativeQuery = true)
    List<TagInterface> getTagsByQuery(String query);


    @Query(value = "SELECT " +
            "t.name as tag, " +
            "(select count(p.id) where p.id = post_id) as tagCount " +
            "FROM " +
            "blogengine.tag2post " +
            "join tags t on t.id = tag_id " +
            "join posts p on p.id = post_id " +
            "group by t.name", nativeQuery = true)
    List<TagInterface> getAllTags();

}
