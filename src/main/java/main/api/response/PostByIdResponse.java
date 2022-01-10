package main.api.response;

import main.dto.PostByIdDTO;
import main.dto.PostCommentsDTO;
import main.dto.UserDtoForPost;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostByIdResponse {

    private Integer id;
    private Long timestamp;
    private boolean active;
    private UserDtoForPost user;
    private String title;
    private String text;
    private Integer likeCount;
    private Integer dislikeCount;
    private Integer viewCount;
    private List<PostCommentsDTO> comments;
    private List<String> tags;

    public PostByIdResponse() {
    }

    public PostByIdResponse(PostByIdDTO postByIdDTO,
                            List<PostCommentsDTO> comments,
                            List<String> tags) {
        this.id = postByIdDTO.getId();
        this.timestamp = postByIdDTO.getTimestamp() / 1000;
        this.active = postByIdDTO.isActive();
        this.user = postByIdDTO.getUser();
        this.title = postByIdDTO.getTitle();
        this.text = postByIdDTO.getText();
        this.likeCount = postByIdDTO.getLikeCount();
        this.dislikeCount = postByIdDTO.getDislikeCount();
        this.viewCount = postByIdDTO.getViewCount();
        this.comments = comments;
        this.tags = tags;
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

    public List<PostCommentsDTO> getComments() {
        return comments;
    }

    public void setComments(List<PostCommentsDTO> comments) {
        this.comments = comments;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
