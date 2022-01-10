package main.repository.DAO;

import main.repository.DAO.builder.PostQueryBuilder;
import main.service.PostOutputMode;
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

        Query query = postQueryBuilder.build(entityManager);

        return query.getResultList();
    }


}
