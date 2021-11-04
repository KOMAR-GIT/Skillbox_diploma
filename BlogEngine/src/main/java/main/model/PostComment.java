package main.model;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "post_comments")
@Table
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private PostComment parent;

    @ManyToOne
    @JoinColumn(nullable = false, name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Column(columnDefinition = "DATETIME", nullable = false)
    private Date time;

    @Column(nullable = false)
    private String text;

    public PostComment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public PostComment getParent() {
        return parent;
    }

    public void setParent(PostComment parent) {
        this.parent = parent;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
