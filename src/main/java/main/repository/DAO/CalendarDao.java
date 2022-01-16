package main.repository.DAO;

import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

@Component
public class CalendarDao {


    private final EntityManager entityManager;

    public CalendarDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List getPostsCountByYear(String year) {

        Integer yearForQuery = year.isEmpty() ? LocalDate.now().getYear() : Integer.parseInt(year);

        Query query = entityManager.createNativeQuery(
                "SELECT " +
                        "    DATE(time) AS date, COUNT(time) postsCount " +
                        " FROM " +
                        "    blogengine.posts " +
                        " WHERE " +
                        "    is_active = 1 " +
                        " AND " +
                        "    moderation_status = 'ACCEPTED' " +
                        " AND " +
                        "    time <= CURDATE() " +
                        " AND " +
                        "    YEAR(time) = :year " +
                        "GROUP BY " +
                        "    DATE(time);", "CalendarDtoMapping"
        ).setParameter("year", yearForQuery);

        return query.getResultList();
    }

}
