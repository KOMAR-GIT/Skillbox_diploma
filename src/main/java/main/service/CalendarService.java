package main.service;

import main.dto.CalendarDTO;
import main.repository.DAO.CalendarDao;
import main.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalendarService {

    private final CalendarDao calendarDao;
    private final PostRepository postRepository;

    public CalendarService(CalendarDao calendarDao, PostRepository postRepository) {
        this.calendarDao = calendarDao;
        this.postRepository = postRepository;
    }

    public Map<LocalDate, Integer> getPostsCountByYear(String year) {
        List<CalendarDTO> posts = calendarDao.getPostsCountByYear(String.valueOf(year));
        return posts.stream().collect(Collectors.toMap(CalendarDTO::getDate, CalendarDTO::getCount));
    }

    public List<Integer> getYears() {
        return postRepository.getYears();
    }

}
