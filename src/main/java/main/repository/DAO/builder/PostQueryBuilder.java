package main.repository.DAO.builder;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostQueryBuilder {

    private final static String generalQuery = "SELECT " +
            "   p.id," +
            "   p.time as timestamp," +
            "   u.id as userId," +
            "   u.name," +
            "   p.title," +
            "   p.text as announce," +
            "   p.view_count as viewCount," +
            "   (select count(*) from blogengine.posts " +
            "       join post_comments on posts.id = post_comments.post_id " +
            "       where posts.id = p.id) AS commentCount," +
            "   (select count(*) from blogengine.posts " +
            "       join post_votes on posts.id = post_votes.post_id " +
            "       where posts.id = p.id and post_votes.value = 1) AS likeCount," +
            "   (select count(*) from blogengine.posts " +
            "       join post_votes on posts.id = post_votes.post_id " +
            "       where posts.id = p.id and post_votes.value = 0) AS dislikeCount " +
            " FROM" +
            "   blogengine.posts p " +
            " LEFT JOIN " +
            "   post_comments pc ON post_id = p.id " +
            " LEFT JOIN " +
            "   users u ON p.user_id = u.id " +
            " LEFT JOIN " +
            "   post_votes pv ON p.id = pv.post_id ";

    private final static String groupQuery = " GROUP BY p.id ";
    private final static String limitQuery = " LIMIT :limit ";
    private final static String offsetQuery = " OFFSET :offset ";
    private final static String generalFilterQuery = " is_active = 1 and moderation_status = 'ACCEPTED' and p.time <= curdate() ";

    private final List<String> filter = new ArrayList<>();
    private final List<String> order = new ArrayList<>();
    private final Map<String, Object> parameters = new HashMap<>();

    public PostQueryBuilder(int offset, int limit) {
        parameters.put("offset", offset);
        parameters.put("limit", limit);
    }

    public PostQueryBuilder parameter(String name, Object value) {
        parameters.put(name,value);
        return this;
    }

    public PostQueryBuilder where(String query) {
        filter.add(query);
        return this;
    }

    public PostQueryBuilder order(String query) {
        order.add(query);
        return this;
    }

    public Query build(EntityManager entityManager) {
        Query query = entityManager.createNativeQuery(generalQuery
                + (filter.isEmpty() ? "" : " where ")
                + String.join(" and ", filter)
                + groupQuery
                + (order.isEmpty() ? "" : " ORDER BY ")
                + String.join(" , ", order)
                + limitQuery
                + offsetQuery,"PostsDtoMapping");
        parameters.forEach(query::setParameter);
        return query;
    }


}
