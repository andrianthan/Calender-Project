import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.time.format.DateTimeFormatter;
import java.time.DayOfWeek;


public class Event {
    private String eventName;
    private LocalDate date; // For one-time events
    private TimeInterval timeInterval;
    private boolean recurring;
    private Set<String> recurringDays; // For recurring events
    private LocalDate startDate; // For recurring events
    private LocalDate endDate; // For recurring events

    //one time events
// Constructor for one-time events
    public Event(String eventName, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.eventName = eventName;
        this.date = date;
        this.timeInterval = new TimeInterval(startTime, endTime);
        this.recurring = false;
    }

    // Constructor for recurring events
    public Event(String eventName, LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate, Set<String> recurringDays) {
        this.eventName = eventName;
        this.recurringDays = recurringDays;
        this.timeInterval = new TimeInterval(startTime, endTime);
        this.startDate = startDate;
        this.endDate = endDate;
        this.recurring = true;
    }

    public LocalDate getDate(){
        return date;
    }

    public String getEventName(){
        return eventName;
    }

    public LocalTime getStartTime()
    {
        return timeInterval.getStartTime();
    }

    public LocalTime getEndTime(){
        return timeInterval.getEndTime();
    }

    public void printOneTime(){

    }

    public boolean isRecurring() {return recurring;}

    public String printRecurringDays() {
        String a = "";
        for(String s: recurringDays)
        {
            a = a + s;
        }
        return a;
    }
    public Set<String> getRecurringDays()
    {
        return recurringDays;
    }

    public LocalDate getStartDate() {return startDate;}

    public LocalDate getEndDate(){return endDate;}

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }
}
