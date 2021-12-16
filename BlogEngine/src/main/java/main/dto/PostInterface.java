package main.dto;

import main.model.User;

import java.sql.Timestamp;
import java.util.Date;

public interface PostInterface {

    int getId();

    Date getTimestamp();

    int getUserId();

    String getName();

    String getTitle();

    String getAnnounce();

    int getLikeCount();

    int getDislikeCount();

    int getCommentCount();

    int getViewCount();


}
