package main.dto;

import java.time.LocalDate;

public class CalendarDto {

    private LocalDate date;

    private int count;

    public CalendarDto(LocalDate date, Integer count) {
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
