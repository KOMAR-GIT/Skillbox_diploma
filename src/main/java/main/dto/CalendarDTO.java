package main.dto;

import java.time.LocalDate;
import java.util.Date;

public class CalendarDTO {

    private LocalDate date;

    private int count;

    public CalendarDTO(LocalDate date, Integer count) {
        this.date = date;
        this.count = count;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
