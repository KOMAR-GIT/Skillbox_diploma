package main.api.response;

import org.springframework.stereotype.Component;

@Component
public class StatisticResponse {

    private Integer postsCount;
    private Integer likesCount;
    private Integer dislikesCount;
    private Integer viewsCount;
    private Long firstPublication;

    public StatisticResponse() {
        postsCount = 0;
        likesCount = 0;
        dislikesCount = 0;
        viewsCount = 0;
        firstPublication = 0L;
    }

    public StatisticResponse(Integer postsCount, Integer likesCount, Integer dislikesCount, Integer viewsCount, Long firstPublication) {
        this.postsCount = postsCount;
        this.likesCount = likesCount;
        this.dislikesCount = dislikesCount;
        this.viewsCount = viewsCount;
        this.firstPublication = firstPublication;
    }

    public Integer getPostsCount() {
        return postsCount;
    }

    public void setPostsCount(Integer postsCount) {
        this.postsCount = postsCount;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getDislikesCount() {
        return dislikesCount;
    }

    public void setDislikesCount(Integer dislikesCount) {
        this.dislikesCount = dislikesCount;
    }

    public Integer getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(Integer viewsCount) {
        this.viewsCount = viewsCount;
    }

    public Long getFirstPublication() {
        return firstPublication;
    }

    public void setFirstPublication(Long firstPublication) {
        this.firstPublication = firstPublication;
    }
}
