package main.repository.DAO;

import main.repository.DAO.builder.PostQueryBuilder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Component
public class PostDAO {

    private final EntityManager entityManager;

    public PostDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    public List getPosts(PostQueryBuilder postQueryBuilder) {

        Query query = postQueryBuilder
                .where(" is_active = 1 and moderation_status = 'ACCEPTED' and p.time <= curdate() ")
                .build(entityManager);

        return query.getResultList();
    }

    public List getPostsForUser(PostQueryBuilder postQueryBuilder) {

        Query query = postQueryBuilder.build(entityManager);

        return query.getResultList();
    }

    public List getPostsForModerator(PostQueryBuilder postQueryBuilder) {

        Query query = postQueryBuilder
                .where(" is_active = 1")
                .build(entityManager);

        return query.getResultList();
    }

}
