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
            "   COUNT(pc.id) AS commentCount," +
            "   sum(case when pv.value = true then 1 else 0 end) AS likeCount," +
            "   sum(case when pv.value = false then 1 else 0 end) AS dislikeCount " +
            " FROM" +
            "   blogengine.posts p " +
            " LEFT JOIN " +
            "   post_comments pc ON post_id = p.id " +
            " LEFT JOIN " +
            "   users u ON p.user_id = u.id " +
            " LEFT JOIN " +
            "   post_votes pv ON p.id = pv.post_id " +
            " WHERE " +
            "   ";

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

    public Query build(EntityManager entityManager, boolean isPostsForModerationOrUser) {
        Query query = entityManager.createNativeQuery(generalQuery
                + (isPostsForModerationOrUser ? "" : generalFilterQuery)
                + ((filter.isEmpty() || isPostsForModerationOrUser) ? "" : " and ")
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
