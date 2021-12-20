package main.dto;

import main.model.User;

import java.sql.Timestamp;
import java.util.Date;

public interface PostInterface {

    Integer getId();

    Date getTimestamp();

    Integer getUserId();

    String getName();

    String getTitle();

    String getAnnounce();

    Integer getLikeCount();

    Integer getDislikeCount();

    Integer getCommentCount();

    Integer getViewCount();


}
