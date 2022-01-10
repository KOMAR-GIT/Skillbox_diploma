package main.dto;

import java.util.Date;

public class PostCommentsDTO {

    private int id;
    private long timestamp;
    private String text;
    private UserForCommentDTO user;

    public PostCommentsDTO() {
    }

    public PostCommentsDTO(int id, Date timestamp, String text, int userId, String name, String photo) {
        this.id = id;
        this.timestamp = timestamp.getTime() / 1000;
        this.text = text;
        user = new UserForCommentDTO(userId, name, photo);
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

    public UserForCommentDTO getUser() {
        return user;
    }

    public void setUser(UserForCommentDTO user) {
        this.user = user;
    }
}
