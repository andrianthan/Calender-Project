import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.time.format.DateTimeFormatter;


public class Event {
    private String eventName;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean recurring;
    private Set<String> recurringDays;
    private LocalDate startDate;
    private LocalDate endDate;

    //one time events
    public Event(String name, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.eventName = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.recurring = false; //false for one time events
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d yyyy");
        System.out.println(name + "\n" + startTime.toString() + " " + endTime.toString() +" "+ formatter.format(date));
    }

    //recurring events
    public Event(String name, LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate, Set<String> recurringDays) {
        this.eventName = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.recurringDays = recurringDays;
        this.recurring = true; // This event is recurring
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, MMM d yyyy");
        System.out.println(name);
        for(String s: recurringDays)
        {
            System.out.print(s);
        }
        System.out.println(" " + startTime.toString() + " " +  endTime.toString() + " " + formatter.format(startDate) + " " + formatter.format(endDate));

    }

    public LocalDate getDate(){
        return date;
    }

    public String getEventName(){
        return eventName;
    }

    public LocalTime getStartTime()
    {
        return startTime;
    }

    public LocalTime getEndTime(){
        return endTime;
    }

    public void printOneTime(){

    }

    public boolean isRecurring() {
        return recurring;
    }

    public Set<String> getRecurringDays() {
        return recurringDays;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate(){
        return endDate;
    }
}
