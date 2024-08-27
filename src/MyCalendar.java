import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.LinkedHashSet;


public class MyCalendar {
    public List<Event> events;
    private LocalDate currentDate;

    public MyCalendar() {
        events = new ArrayList<>();
        currentDate = LocalDate.now();
    }
    public void loadEvents(String file)
    {

        try (BufferedReader reader = new BufferedReader(new FileReader("src/events.txt")))
        {
            String line;
            String eventName = "";
            LocalDate date;
            LocalTime startTime;
            LocalTime endTime;
            LocalDate startDate;
            LocalDate endDate;

            while((line = reader.readLine()) != null)
            {
                Set<String> recurringDays = new LinkedHashSet<>();
                if(!(line.contains("/"))) {
                    eventName = line.trim();
                    //System.out.println(eventName);
                }else{
                    String[] parts = line.split(" ");
                    if(parts.length == 3)
                    {
                        date = LocalDate.parse(parts[0], DateTimeFormatter.ofPattern("M/d/yy"));
                        startTime = LocalTime.parse(parts[1], DateTimeFormatter.ofPattern("H:mm"));
                        endTime = LocalTime.parse(parts[2], DateTimeFormatter.ofPattern("H:mm"));
                        Event e = new Event(eventName, date, startTime, endTime);
                        events.add(e);

                    }else if (parts.length == 5){
                        if (parts[0].contains("M")) recurringDays.add("M");
                        if (parts[0].contains("T")) recurringDays.add("T");
                        if (parts[0].contains("W")) recurringDays.add("W");
                        if (parts[0].contains("R")) recurringDays.add("R");
                        if (parts[0].contains("F")) recurringDays.add("F");
                        startTime = LocalTime.parse(parts[1], DateTimeFormatter.ofPattern("H:mm"));
                        endTime = LocalTime.parse(parts[2], DateTimeFormatter.ofPattern("H:mm"));
                        startDate = LocalDate.parse(parts[3], DateTimeFormatter.ofPattern("M/d/yy"));
                        endDate = LocalDate.parse(parts[4], DateTimeFormatter.ofPattern("M/d/yy"));
                        Event e = new Event(eventName, startTime, endTime, startDate, endDate, recurringDays);
                        events.add(e);
                    }

                }


            }
            System.out.println("Loading is done!");

        }catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());

        }
        //a

    }


}



