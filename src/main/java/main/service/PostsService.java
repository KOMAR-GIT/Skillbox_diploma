package main.service;

import main.dto.CalendarDTO;
import main.repository.DAO.CalendarDao;
import main.repository.DAO.PostDAO;
import main.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class PostsService {

    private final PostRepository postRepository;
    private final PostDAO postDAO;
    private final CalendarDao calendarDao;

    public PostsService(PostRepository postRepository, PostDAO postDAO, CalendarDao calendarDao) {
        this.postRepository = postRepository;
        this.postDAO = postDAO;
        this.calendarDao = calendarDao;
    }

    public List getPosts(int offset, int limit, PostOutputMode mode) {
        return postDAO.getPosts(offset, limit, mode, "", "");
    }

    public List getPosts(int offset, int limit, String searchQuery) {
        return postDAO.getPosts(offset, limit, PostOutputMode.recent, searchQuery, "");
    }

    public List getPosts(int offset, int limit, LocalDate date) {
        return postDAO.getPosts(offset, limit, PostOutputMode.recent, "", String.valueOf(date));
    }

    public Integer getAllPostsCount() {
        return postRepository.getPostsCount();
    }

    public Integer getQueriedPostsCount(String searchQuery){
        return postRepository.getQueriedPostsCount(searchQuery);
    }

    public Map<LocalDate, Integer> getPostsCountByYear(String year){
        List<CalendarDTO> posts = calendarDao.getPostsCountByYear(year);
        Map<LocalDate, Integer> map = posts.stream().collect(Collectors.toMap(t -> t.getDate(), CalendarDTO::getCount));

        return map;
    }

    public List<Integer> getYears(){
        return postRepository.getYears();
    }
}
