import java.io.*;
import java.time.*;
import java.util.*;
import java.time.format.DateTimeFormatter;

/**
 * MyCalendar class represents a calendar object which stores and manages events.
 * can load events from a file, add events, print events, and provide access events in calendar object.
 */

public class MyCalendar {
    private List<Event> events;
    private LocalDate currentDate;

    /**
     * constructs a new MyCalendar object by initializing events object
     * and setting currentDate.
     */

    public MyCalendar() {
        events = new ArrayList<>();
        currentDate = LocalDate.now();
    }


    /**
     * loads events from a specified file and adds them to calendar
     */

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
                        date = LocalDate.parse(parts[0], DateTimeFormatter.ofPattern("MM/dd/yy"));
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
            //System.out.println("\n----------------------------------------------\nLoading is done!\n----------------------------------------------\n");

        }catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());

        }
        //a

    }

    /**
     * adds an event object and appends it to the events file.
     * only is able to add one-time events
     */

    public void addEvent(Event event)
    {
        try (FileWriter fileWriter = new FileWriter("src/events.txt", true); // Append to the file
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter out = new PrintWriter(bufferedWriter)) {
            // Only handle single events
            out.println(event.getEventName());
            out.printf("%s %s %s%n",
                    event.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")),
                    event.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                    event.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            System.out.println("Event successfully added");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }

    }

    /**
     * returns a list of events
     */

    public List<Event> getEvents(){ return events;}

    /**
     * prints both one-time events and recurring events in a specified format.
     * categories events based on if they are one-time or recurring
     */

    public void printEvents(String file) {
        DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("MM/dd/yy");
        DateTimeFormatter fullDateFormat = DateTimeFormatter.ofPattern("EEEE MMMM dd HH:mm");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        TreeMap<Integer, List<String>> oneTimeEvents = new TreeMap<>();
        List<String> recurringEvents = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String eventName = "";

            while ((line = reader.readLine()) != null) {
                if (!line.contains("/")) {
                    eventName = line.trim();
                } else {
                    String[] parts = line.split(" ");
                    if (parts.length == 3) {  // One-time events
                        LocalDate date = LocalDate.parse(parts[0], dayFormat);
                        LocalTime startTime = LocalTime.parse(parts[1], timeFormat);
                        LocalTime endTime = LocalTime.parse(parts[2], timeFormat);
                        LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
                        int year = date.getYear();
                        oneTimeEvents.putIfAbsent(year, new ArrayList<>());
                        oneTimeEvents.get(year).add(String.format("  %s - %s %s", startDateTime.format(fullDateFormat), endTime.format(timeFormat), eventName));
                    } else if (parts.length == 5) {  // Recurring events
                        Set<String> recurringDays = new LinkedHashSet<>(Arrays.asList(parts[0].split("")));
                        LocalTime startTime = LocalTime.parse(parts[1], timeFormat);
                        LocalTime endTime = LocalTime.parse(parts[2], timeFormat);
                        LocalDate startDate = LocalDate.parse(parts[3], dayFormat);
                        LocalDate endDate = LocalDate.parse(parts[4], dayFormat);
                        recurringEvents.add(String.format("%s\n%s %s %s %s - %s", eventName, String.join("", recurringDays), startTime.format(timeFormat), endTime.format(timeFormat), startDate.format(dayFormat), endDate.format(dayFormat)));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }

        // Print one-time events
        System.out.println("ONE TIME EVENTS");
        oneTimeEvents.forEach((year, events) -> {
            System.out.println(year);
            events.forEach(System.out::println);
        });

        // Print recurring events
        System.out.println("\nRECURRING EVENTS");
        recurringEvents.forEach(System.out::println);
    }


}



