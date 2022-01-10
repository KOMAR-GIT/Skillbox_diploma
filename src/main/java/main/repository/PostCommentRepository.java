package main.repository;

import main.dto.CommentInterface;
import main.dto.PostCommentsDTO;
import main.model.PostComment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostCommentRepository extends CrudRepository<PostComment,Integer> {

    @Query(value = "SELECT " +
            "   pc.id, " +
            "   pc.time as timestamp, " +
            "   pc.text, " +
            "   u.id as userId, " +
            "   u.name as userName, " +
            "   u.photo " +
            " FROM " +
            "   post_comments pc" +
            " JOIN " +
            "   users u" +
            " ON" +
            "   pc.user_id = u.id" +
            " WHERE " +
            "   pc.post_id = :id", nativeQuery = true)
    List<CommentInterface> getComments(@Param("id") int id);

}
