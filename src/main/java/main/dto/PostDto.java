package main.dto;

import org.jsoup.Jsoup;

import java.sql.Timestamp;
import java.util.Date;

public class PostDto {

    private Integer id;
    private Long timestamp;
    private UserDtoForPost user;
    private String title;
    private String announce;
    private Integer likeCount;
    private Integer dislikeCount;
    private Integer commentCount;
    private Integer viewCount;

    public PostDto() {
    }

    public PostDto(Integer id, Date timestamp, Integer userId, String name, String title, String announce, Integer likeCount, Integer dislikeCount, Integer commentCount, Integer viewCount) {
        this.id = id;
        this.timestamp = timestamp.getTime() / 1000;
        user = new UserDtoForPost(userId,name);
        this.title = title;
        this.announce = announce;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.commentCount = commentCount;
        this.viewCount = viewCount;
    }

    public void editAnnounceText(String text){
        text = Jsoup.parse(text).text();
        int ANNOUNCE_TEXT_LIMIT = 149;
        text = text.length() > ANNOUNCE_TEXT_LIMIT ? text.substring(0, ANNOUNCE_TEXT_LIMIT) : text;
        announce = text;
    }

    public void convertTimeToTimestamp(Date time){
        timestamp = Timestamp.valueOf(String.valueOf(time)).getTime() / 1000;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
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

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Integer getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(Integer dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}
