import java.time.LocalTime;

/**
 * the TimeInterval class represents a time interval with a start time and an end time.
 * provides methods to check if the time interval of a specified event overlaps with t
 * the time interval of another event.
 */

public class TimeInterval {
    private LocalTime startTime;
    private LocalTime endTime;


    /**
     * constructs a TimeInterval with a specified start and end time.
     */

    public TimeInterval(LocalTime start, LocalTime end) {
        this.startTime = start;
        this.endTime = end;
    }


    /**
     * gets the start time for a TimeInterval object.
     */

    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * gets the end time for a TimeInterval object.
     */

    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * checks to see if this time interval overlaps with another time interval.
     */

    public boolean overLap(TimeInterval other) {
        return !this.endTime.isBefore(other.startTime) && !this.startTime.isAfter(other.endTime);
    }
}