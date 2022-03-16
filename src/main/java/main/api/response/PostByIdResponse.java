package main.api.response;

import main.dto.PostCommentsDto;
import main.dto.interfaces.PostInterface;
import main.dto.UserForPostDto;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

@Component
public class PostByIdResponse {

    private Integer id;
    private Long timestamp;
    private Byte active;
    private UserForPostDto user;
    private String title;
    private String text;
    private BigInteger likeCount;
    private BigInteger dislikeCount;
    private Integer viewCount;
    private List<PostCommentsDto> comments;
    private List<String> tags;

    public PostByIdResponse() {
    }

    public PostByIdResponse(PostInterface postInterface,
                            List<PostCommentsDto> comments,
                            List<String> tags) {
        this.id = postInterface.getId();
        this.timestamp = postInterface.getTimestamp().getTime() / 1000;
        this.active = postInterface.getActive();
        this.user = new UserForPostDto(postInterface.getUserId(), postInterface.getUserName());
        this.title = postInterface.getTitle();
        this.text = postInterface.getText();
        this.likeCount = postInterface.getLikeCount();
        this.dislikeCount = postInterface.getDislikeCount();
        this.viewCount = postInterface.getViewCount();
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

    public Byte isActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }

    public UserForPostDto getUser() {
        return user;
    }

    public void setUser(UserForPostDto user) {
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

    public BigInteger getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(BigInteger likeCount) {
        this.likeCount = likeCount;
    }

    public BigInteger getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(BigInteger dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public List<PostCommentsDto> getComments() {
        return comments;
    }

    public void setComments(List<PostCommentsDto> comments) {
        this.comments = comments;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
