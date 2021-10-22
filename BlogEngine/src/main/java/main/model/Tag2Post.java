package main.model;

import javax.persistence.*;

@Entity(name = "tag2post")
@Table
public class Tag2Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "post_id")
    private Post post_id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "tag_id")
    private Tag tag_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Post getPost_id() {
        return post_id;
    }

    public void setPost_id(Post post_id) {
        this.post_id = post_id;
    }

    public Tag getTag_id() {
        return tag_id;
    }

    public void setTag_id(Tag tag_id) {
        this.tag_id = tag_id;
    }
}
