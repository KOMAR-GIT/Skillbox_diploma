package main.service;

import main.api.response.LikeDislikeResponse;
import main.model.Post;
import main.model.PostVotes;
import main.model.User;
import main.repository.PostVotesRepository;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class PostVotesService {

    private final PostVotesRepository postVotesRepository;


    public PostVotesService(PostVotesRepository postVotesRepository) {
        this.postVotesRepository = postVotesRepository;
    }

    public LikeDislikeResponse addLike(int postId, int userId) {
        PostVotes postVotes = postVotesRepository.findByPostIdAndUserId(postId, userId);
        PostVotes newVote = new PostVotes(
                new Post(postId),
                new User(userId),
                Calendar.getInstance().getTime(),
                true);
        if (postVotes == null) {
            postVotesRepository.save(newVote);
            return new LikeDislikeResponse(true);
        } else if (!postVotes.isValue()) {
            postVotesRepository.delete(postVotes);
            postVotesRepository.save(newVote);
            return new LikeDislikeResponse(true);
        } else {
            return new LikeDislikeResponse(false);
        }
    }

    public LikeDislikeResponse addDislike(int postId, int userId) {
        PostVotes postVotes = postVotesRepository.findByPostIdAndUserId(postId, userId);
        PostVotes newVote = new PostVotes(
                new Post(postId),
                new User(userId),
                Calendar.getInstance().getTime(),
                false);
        if (postVotes == null) {
            postVotesRepository.save(newVote);
            return new LikeDislikeResponse(true);
        } else if (postVotes.isValue()) {
            postVotesRepository.delete(postVotes);
            postVotesRepository.save(newVote);
            return new LikeDislikeResponse(true);
        } else {
            return new LikeDislikeResponse(false);
        }
    }

}
