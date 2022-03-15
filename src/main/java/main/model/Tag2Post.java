package main.model;

import javax.persistence.*;

@Entity(name = "tag2post")
@Table
public class Tag2Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "post_id")
    private Post postId;

    @ManyToOne
    @JoinColumn(nullable = false, name = "tag_id")
    private Tag tagId;

    public Tag2Post() {
    }

    public Tag2Post(Post postId, Tag tagId) {
        this.postId = postId;
        this.tagId = tagId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Post getPostId() {
        return postId;
    }

    public void setPostId(Post postId) {
        this.postId = postId;
    }

    public Tag getTagId() {
        return tagId;
    }

    public void setTagId(Tag tagId) {
        this.tagId = tagId;
    }
}
