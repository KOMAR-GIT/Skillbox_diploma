package main.dto.interfaces;

import java.sql.Timestamp;

public interface StatisticsInterface {

    Integer getPostsCount();
    Integer getLikesCount();
    Integer getDislikesCount();
    Integer getViewsCount();
    Timestamp getFirstPublication();

}
