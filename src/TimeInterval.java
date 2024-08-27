import java.time.LocalTime;

public class TimeInterval {
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeInterval(LocalTime start, LocalTime end)
    {
        this.startTime = start;
        this.endTime = end;
    }

    public boolean overLap(TimeInterval s)
    {
        return true;
    }
    //
}
