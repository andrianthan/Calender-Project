import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

/**
 * The Event class represents an event in the calendar.
 * events can be either be categorized as a one-time event or a recurring event.
 * stores the event name, date, time interval, and recurring information.
 */

public class Event {
    private String eventName;
    private LocalDate date; // For one-time events
    private TimeInterval timeInterval;
    private boolean recurring;
    private Set<String> recurringDays; // For recurring events
    private LocalDate startDate; // For recurring events
    private LocalDate endDate; // For recurring events


    /**
     * constructor for one time events.
     * initializes event name, date, and time interval
     */

    public Event(String eventName, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.eventName = eventName;
        this.date = date;
        this.timeInterval = new TimeInterval(startTime, endTime);
        this.recurring = false;
    }

    /**
     * constructor for recurring events.
     * initializes event name, start time, end time, recurring days, and date range.
     */

    public Event(String eventName, LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate, Set<String> recurringDays) {
        this.eventName = eventName;
        this.recurringDays = recurringDays;
        this.timeInterval = new TimeInterval(startTime, endTime);
        this.startDate = startDate;
        this.endDate = endDate;
        this.recurring = true;
    }

    /**
     * gets the date of a one time event.
     */

    public LocalDate getDate(){
        return date;
    }

    /**
     * gets the event name of the event.
     */

    public String getEventName(){
        return eventName;
    }

    /**
     * gets the start time of the event.
     */

    public LocalTime getStartTime()
    {
        return timeInterval.getStartTime();
    }

    /**
     * gets the end time of the event.
     */

    public LocalTime getEndTime(){
        return timeInterval.getEndTime();
    }

    /**
     * returns true or false based on if an event is recurring.
     */

    public boolean isRecurring() {return recurring;}

    /**
     * prints a string of days in which an event is recurring.
     */

    public String printRecurringDays() {
        String a = "";
        for(String s: recurringDays)
        {
            a = a + s;
        }
        return a;
    }
    /**
     * returns a set of days in which an event is recurring.
     */

    public Set<String> getRecurringDays()
    {
        return recurringDays;
    }


    /**
     * gets the start date of a recurring event.
     */

    public LocalDate getStartDate() {return startDate;}

    /**
     * gets the end date of a recurring event.
     */

    public LocalDate getEndDate(){return endDate;}

    /**
     * gets the time interval of an event which include start and end times.
     */

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }
}
