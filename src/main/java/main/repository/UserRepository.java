package main.repository;

import main.dto.interfaces.UserInterface;
import main.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);

    @Query(value = " SELECT " +
            "    u.email AS email, " +
            "    u.name as name, " +
            "    u.photo as photo, " +
            "    u.id AS id,  " +
            "    u.is_moderator AS moderation, " +
            "    (select count(*) from blogengine.posts where moderation_status = \"NEW\" and is_active = 1) as moderationCount " +
            "FROM " +
            "    blogengine.users u " +
            "    where email = :email", nativeQuery = true)
    UserInterface getByEmail(@Param("email") String email);

    User findByCode(String code);

}
