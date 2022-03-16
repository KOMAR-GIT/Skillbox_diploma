package main.dto;

import java.util.Date;

public class PostCommentsDto {

    private int id;
    private long timestamp;
    private String text;
    private UserForCommentDto user;

    public PostCommentsDto() {
    }

    public PostCommentsDto(int id, Date timestamp, String text, int userId, String name, String photo) {
        this.id = id;
        this.timestamp = timestamp.getTime() / 1000;
        this.text = text;
        user = new UserForCommentDto(userId, name, photo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserForCommentDto getUser() {
        return user;
    }

    public void setUser(UserForCommentDto user) {
        this.user = user;
    }
}
