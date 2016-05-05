package annanormark.calendarview;

import java.util.Date;

/**
 * Created by Anna on 2016-05-02.
 */
public class Booking {
    private String title;
    private Long date;
    private int work;

    public Booking(String title, Date date, int workTime) {
        this.title = title;
       // this.date = date;
        this.work = workTime;
    }


    @Override
    public String toString() {
        return "Booking{" +
                "title='" + title + '\'' +
                ", date=" + date +
                '}';
    }

    public Long getDate(){
        return this.date;
    }

    public String getTitle(){
        return this.title;
    }

    public int getWorkTime(){
        return this.work;
    }
}
