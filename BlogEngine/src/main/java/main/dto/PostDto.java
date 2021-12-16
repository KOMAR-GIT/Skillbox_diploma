package main.dto;

import org.jsoup.Jsoup;

import java.sql.Timestamp;
import java.util.Date;

public class PostDto {

    private int id;
    private Timestamp timestamp;
    private UserDtoForPost user;
    private String title;
    private String announce;
    private int likeCount;
    private int dislikeCount;
    private int commentCount;
    private int viewCount;

    public void editAnnounceText(String text){
        text = Jsoup.parse(text).text();
        int ANNOUNCE_TEXT_LIMIT = 149;
        text = text.length() > ANNOUNCE_TEXT_LIMIT ? text.substring(0, ANNOUNCE_TEXT_LIMIT) : text;
        announce = text;
    }

    public void convertTimeToTimestamp(Date time){
        timestamp = Timestamp.valueOf(String.valueOf(time));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public UserDtoForPost getUser() {
        return user;
    }

    public void setUser(UserDtoForPost user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(String announce) {
        this.announce = announce;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }
}
