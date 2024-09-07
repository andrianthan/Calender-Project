import java.time.LocalTime;

public class TimeInterval {
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeInterval(LocalTime start, LocalTime end) {
        this.startTime = start;
        this.endTime = end;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean overLap(TimeInterval other) {
        // Check if one time interval overlaps with another
        // This assumes that the start time is inclusive and the end time is exclusive
        return !this.endTime.isBefore(other.startTime) && !this.startTime.isAfter(other.endTime);
    }
}