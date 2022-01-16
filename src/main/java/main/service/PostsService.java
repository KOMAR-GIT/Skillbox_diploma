package main.service;

import main.dto.CalendarDTO;
import main.dto.interfaces.PostInterface;
import main.repository.DAO.CalendarDao;
import main.repository.DAO.PostDAO;
import main.repository.DAO.builder.PostQueryBuilder;
import main.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public List getPostsForModeration(int offset, int limit){
        return postDAO.getPostsForModeration(new PostQueryBuilder(offset, limit));
    }

    public List getPostsByMode(int offset, int limit, PostOutputMode mode) {
        String sortMode;
        switch (mode) {
            case early:
                sortMode = "p.time desc";
                break;
            case best:
                sortMode = "likeCount desc";
                break;
            case popular:
                sortMode = "commentCount desc";
                break;
            case recent:
            default:
                sortMode = "p.time";

        }
        return postDAO.getPosts(new PostQueryBuilder(offset, limit).order(sortMode));
    }

    public List getPostsByQuery(int offset, int limit, String searchQuery) {
        PostQueryBuilder postQueryBuilder = new PostQueryBuilder(offset, limit);
        if (!searchQuery.isEmpty()) {
            postQueryBuilder
                    .where("match(p.text, title) against(concat(\"*\",:searchQuery, \"*\") IN BOOLEAN MODE) ")
                    .parameter("searchQuery", searchQuery);
        }
        return postDAO.getPosts(postQueryBuilder);
    }

    public List getPostsByDate(int offset, int limit, LocalDate date) {
        PostQueryBuilder postQueryBuilder = new PostQueryBuilder(offset, limit);
        if (date != null) {
            postQueryBuilder
                    .where(" date(p.time) =  :date ")
                    .parameter("date", String.valueOf(date));
        }
        return postDAO.getPosts(postQueryBuilder);
    }

    public List getPostsByTag(int offset, int limit, String tag) {
        PostQueryBuilder postQueryBuilder = new PostQueryBuilder(offset, limit);
        if (!tag.isEmpty()) {
            postQueryBuilder
                    .where(" p.id in (select post_id from tag2post join tags t on tag_id = t.id where t.name = :tag) ")
                    .parameter("tag", tag);
        }
        return postDAO.getPosts(postQueryBuilder);
    }

    public PostInterface getPostById(int id) {
        return postRepository.getPostById(id);
    }

    //Различные методы по получению количества постов

    public Integer getPostsCount() {
        return postRepository.getPostsCount();
    }

    public Integer getPostsForModerationCount() {
        return postRepository.getPostsForModerationCount();
    }

    public Integer getPostsCount(String searchQuery) {
        return postRepository.getQueriedPostsCount(searchQuery);
    }

    public Integer getPostsCountByDate(String date) {
        return postRepository.getPostsCountByDate(date);
    }

    public Integer getPostsCountByTag(String tag) {
        return postRepository.getPostsCountByTag(tag);
    }


    public Map<LocalDate, Integer> getPostsCountByYear(String year) {
        List<CalendarDTO> posts = calendarDao.getPostsCountByYear(String.valueOf(year));
        return posts.stream().collect(Collectors.toMap(CalendarDTO::getDate, CalendarDTO::getCount));
    }

    public List<Integer> getYears() {
        return postRepository.getYears();
    }


}
