package main.api.response;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class CalendarResponse {

    List<Integer> years;

    Map<LocalDate, Integer> posts;

    public CalendarResponse(List<Integer> years, Map<LocalDate, Integer> posts) {
        this.years = years;
        this.posts = posts;
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public Map<LocalDate, Integer> getPosts() {
        return posts;
    }

    public void setPosts(Map<LocalDate, Integer> posts) {
        this.posts = posts;
    }
}
