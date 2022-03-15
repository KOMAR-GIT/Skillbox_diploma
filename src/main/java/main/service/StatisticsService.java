package main.service;

import main.api.response.StatisticResponse;
import main.dto.interfaces.StatisticsInterface;
import main.repository.PostRepository;
import main.security.SecurityUser;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;


    public StatisticsService(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    public StatisticResponse getUserStatistics(){
        SecurityUser securityUser = (SecurityUser)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = securityUser.getUserId();
        StatisticsInterface statisticsInterface = postRepository.getUserStatistics(userId);
        if(statisticsInterface.getPostsCount() == 0){
            return new StatisticResponse();
        }
        StatisticResponse statisticResponse = modelMapper.map(statisticsInterface, StatisticResponse.class);
        statisticResponse.setFirstPublication(statisticResponse.getFirstPublication() / 1000);
        return statisticResponse;
    }

    public StatisticsInterface getGlobalStatistics(){
        return postRepository.getGlobalStatistics();
    }


}
