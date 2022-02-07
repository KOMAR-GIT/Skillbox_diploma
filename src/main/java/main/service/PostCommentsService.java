package main.service;

import main.api.response.AddCommentResponse;
import main.api.response.ResponseWithErrors;
import main.dto.interfaces.CommentInterface;
import main.dto.interfaces.PostInterface;
import main.model.Post;
import main.model.PostComment;
import main.model.User;
import main.repository.PostCommentRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Service
public class PostCommentsService {

    private final PostCommentRepository postCommentRepository;
    private final PostsService postsService;

    public PostCommentsService(PostCommentRepository postCommentRepository, PostsService postsService) {
        this.postCommentRepository = postCommentRepository;
        this.postsService = postsService;
    }

    public List<CommentInterface> getComments(int id) {
        return postCommentRepository.getComments(id);
    }

    public AddCommentResponse addComment(Integer parentId, Integer postId, String text, Integer userId) {
        Post post = postId != null ? postsService.findPostById(postId) : null;
        PostComment postComment = parentId != null ? postCommentRepository.findById(parentId).orElse(null) : null;
        User user = new User(userId);
        if (post == null) {
            return new AddCommentResponse(false, null);
        } else if (text.length() > 0) {
            PostComment newPostComment = new PostComment(
                    postComment,
                    post,
                    user,
                    Calendar.getInstance().getTime(),
                    text);
            postCommentRepository.save(newPostComment);
            return new AddCommentResponse(newPostComment.getId());
        }
        HashMap<String, String> errors = new HashMap<>();
        errors.put("text", "Текст комментария не задан или слишком короткий");
        return new AddCommentResponse(false, errors);
    }
}
