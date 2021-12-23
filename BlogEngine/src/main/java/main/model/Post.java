package main.model;

import main.dto.PostDto;
import org.hibernate.loader.custom.ConstructorResultColumnProcessor;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.util.Date;



@Entity(name = "posts")
@SqlResultSetMapping(
        name = "PostsMapping",
        classes = @ConstructorResult(
                targetClass = PostDto.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "timestamp", type = Date.class),
                        @ColumnResult(name = "userId", type = Integer.class),
                        @ColumnResult(name = "name", type = String.class),
                        @ColumnResult(name = "title", type = String.class),
                        @ColumnResult(name = "announce", type = String.class),
                        @ColumnResult(name = "likeCount", type = Integer.class),
                        @ColumnResult(name = "dislikeCount", type = Integer.class),
                        @ColumnResult(name = "commentCount", type = Integer.class),
                        @ColumnResult(name = "viewCount", type = Integer.class)
                }
        )
)
@Table
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "is_active", columnDefinition = "tinyint", nullable = false)
    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_status", nullable = false, columnDefinition = "enum('NEW', 'ACCEPTED', 'DECLINED')")
    private ModerationStatus moderationStatus;

    @ManyToOne
    @JoinColumn(name = "moderator_id")
    private User moderator;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Date time;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

    public Post() {
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public ModerationStatus getModerationStatus() {
        return moderationStatus;
    }

    public void setModerationStatus(ModerationStatus moderationStatus) {
        this.moderationStatus = moderationStatus;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public User getModerator() {
        return moderator;
    }

    public void setModerator(User moderator) {
        this.moderator = moderator;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}
