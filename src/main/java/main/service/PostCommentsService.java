package main.service;

import main.dto.interfaces.CommentInterface;
import main.repository.PostCommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostCommentsService {

    private final PostCommentRepository postCommentRepository;

    public PostCommentsService(PostCommentRepository postCommentRepository) {
        this.postCommentRepository = postCommentRepository;
    }

    public List<CommentInterface> getComments(int id){
        return postCommentRepository.getComments(id);
    }

}
