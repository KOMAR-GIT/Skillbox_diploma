package main.dto;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class PostByIdDTO {

    private Integer id;
    private Long timestamp;
    private boolean active;
    private UserDtoForPost user;
    private String title;
    private String text;
    private Integer likeCount;
    private Integer dislikeCount;
    private Integer viewCount;

    public PostByIdDTO() {
    }


    public PostByIdDTO(Integer id,
                       Date timestamp,
                       Boolean active,
                       Integer userId,
                       String userName,
                       String title,
                       String text,
                       Integer likeCount,
                       Integer dislikeCount,
                       Integer viewCount) {
        this.id = id;
        this.timestamp = timestamp.getTime();
        this.active = active;
        user = new UserDtoForPost(userId, userName);
        this.title = title;
        this.text = text;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.viewCount = viewCount;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}
