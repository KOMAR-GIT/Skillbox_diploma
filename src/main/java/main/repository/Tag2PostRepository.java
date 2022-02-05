package main.repository;

import main.model.Tag2Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface Tag2PostRepository extends CrudRepository<Tag2Post, Integer> {

    @Query(value = "SELECT * FROM tag2post where post_id = :id", nativeQuery = true)
    List<Tag2Post> getRelationsById(@Param("id") int id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM tag2post where post_id = :id")
    void deletePostRelations(@Param("id") int id);

}
