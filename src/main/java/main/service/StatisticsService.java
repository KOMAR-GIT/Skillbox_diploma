package main.service;

import main.dto.interfaces.StatisticsInterface;
import main.repository.PostRepository;
import main.security.SecurityUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    private final PostRepository postRepository;


    public StatisticsService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public StatisticsInterface getUserStatistics(){
        SecurityUser securityUser = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return postRepository.getUserStatistics(securityUser.getUserId());
    }

    public StatisticsInterface getGlobalStatistics(){
        return postRepository.getGlobalStatistics();
    }


}
