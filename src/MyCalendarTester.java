import java.io.*;

import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;
import java.time.DayOfWeek;


public class MyCalendarTester {
    public static void main(String [] args)
    {
        Scanner scan = new Scanner(System.in);
        MyCalendar calendar = new MyCalendar();
        LocalDate currentDate = LocalDate.now();
        LocalDate firstDayMonth = currentDate.withDayOfMonth(1); //gets the first day of current month
        int firstDayOfWeek = firstDayMonth.getDayOfWeek().getValue(); //gets the number value of the day of the first day of the month
        int monthLength = currentDate.lengthOfMonth(); //gets the length of the month
        int dayOfMonth = 1;
        System.out.println(currentDate.getMonth() + " " + currentDate.getYear());
        System.out.print("Su Mo Tu We Th Fr Sa");
        if (firstDayOfWeek != DayOfWeek.SUNDAY.getValue()) {
            // Calculate offset for the first day
            int offset = (firstDayOfWeek == 7) ? 0 : firstDayOfWeek; // This ensures Sunday is at index 0, Monday at 1, etc.
            for (int i = 0; i < offset; i++) {
                System.out.print("   ");
            }
        }

        while(dayOfMonth <= monthLength)
        {
            //if current day is sunday then indent
            if(currentDate.withDayOfMonth(dayOfMonth).getDayOfWeek() == DayOfWeek.SUNDAY)
            {
                System.out.println();
            }
            System.out.print(dayOfMonth == currentDate.getDayOfMonth() ? "[" + dayOfMonth + "]" : dayOfMonth); //if current calendar day is same as current day add brackets
            System.out.print(dayOfMonth < 10 ? "  " : " "); //changes spacing based on day of month
            dayOfMonth++;
        }
        System.out.println();
        System.out.println("\n----------------------------------------------\nCurrent Events\n----------------------------------------------\n");
        calendar.loadEvents("events.txt");
        calendar.printEvents("src/events.txt");
        System.out.println("Select one of the following main menu options:" + "\n[V]iew by [C]reate, [G]o to [E]vent list [D]elete [Q]uit" );
        String input = scan.next().toUpperCase();
        if(!(input.equals("Q")))
        {
            mainMenu(input,calendar);
        }
        saveEvents(calendar);
        System.out.println("Good Bye");


    }

    //method that lets users choose between the day/month view of the calendar
    public static void view(String input, MyCalendar calendar){
        List<Event> events = calendar.getEvents();
        LocalDate currentDate = LocalDate.now();
        int dayOfMonth = 1;
        int monthLength = currentDate.lengthOfMonth();
        LocalDate firstDayMonth = currentDate.withDayOfMonth(1);
        int firstDayOfWeek = firstDayMonth.getDayOfWeek().getValue();
        Scanner scan = new Scanner(System.in);
        input = input.toUpperCase();
        //System.out.println(input);
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");

        Set<LocalDate> daysWithEvents = new HashSet<>();
        for (Event event : events) {
            if (event.isRecurring()) {
                // Assuming recurring events occur from startDate to endDate
                if ((event.getStartDate().isBefore(firstDayMonth.plusMonths(1)) && event.getEndDate().isAfter(firstDayMonth.minusDays(1)))) {
                    LocalDate eventDay = event.getStartDate();
                    while (!eventDay.isAfter(event.getEndDate())) {
                        if (eventDay.getMonth() == currentDate.getMonth()) {
                            daysWithEvents.add(eventDay);
                        }
                        eventDay = eventDay.plusDays(1); // Increment day by day within the event period
                    }
                }
            } else {
                // single occurrence events
                if (event.getDate().getMonth() == currentDate.getMonth() && event.getDate().getYear() == currentDate.getYear()) {
                    daysWithEvents.add(event.getDate());
                }
            }
        }

        if(input.equals("D"))
        {
            displayDayEvents(currentDate, calendar);
            System.out.println("[P]revious, [N]ext, [G]o back to main menu?");
            String subMenuInput = scan.next().toUpperCase();
            while(!(subMenuInput.equals("G")))
            {
                if(subMenuInput.equals("P"))
                {
                    currentDate = currentDate.minusDays(1);
                    displayDayEvents(currentDate, calendar);
                    System.out.println("[P]revious, [N]ext, [G]o back to main menu?");
                    subMenuInput = scan.next().toUpperCase();

                }else if(subMenuInput.equals("N"))
                {
                    currentDate = currentDate.plusDays(1);
                    displayDayEvents(currentDate, calendar);
                    System.out.println("[P]revious, [N]ext, [G]o back to main menu?");
                    subMenuInput = scan.next().toUpperCase();
                }else{
                    System.out.println("Please enter a valid input [P, N, G]");
                    subMenuInput = scan.next().toUpperCase();
                }
            }
        }else if(input.equals("M")){
            displayMonthEvents(currentDate, calendar);
            System.out.println("[P]revious, [N]ext, [G]o back to main menu?");
            String subMenuInput = scan.next().toUpperCase();
            while(!(subMenuInput.equals("G")))

            {

                if(subMenuInput.equals("P"))
                {
                    currentDate = currentDate.minusMonths(1);
                    displayMonthEvents(currentDate, calendar);
                    System.out.println("[P]revious, [N]ext, [G]o back to main menu?");
                    subMenuInput = scan.next().toUpperCase();
                }else if(subMenuInput.equals("N"))
                {
                    currentDate = currentDate.plusMonths(1);
                    displayMonthEvents(currentDate, calendar);
                    System.out.println("[P]revious, [N]ext, [G]o back to main menu?");
                    subMenuInput = scan.next().toUpperCase();


                }else{
                    System.out.println("Please enter a valid input");
                    subMenuInput = scan.next().toUpperCase();
                }
            }
            if(subMenuInput.equals("G"))
            {

            }

        }else{
            System.out.println("Please enter a valid input of D or M");
            input = scan.next();
            view(input, calendar);
        }

    }

    public static void mainMenu(String input, MyCalendar calendar)
    {
        Scanner scan = new Scanner(System.in);

        while (!(input.equals("Q")))
        {
            if(input.equals("V")) {
                System.out.println("[D]ay view or [M]onth view ?");
                String viewInput = scan.next().toUpperCase();
                view(viewInput, calendar);
                System.out.println("Select one of the following main menu options:" + "\n[V]iew by [C]reate, [G]o to [E]vent list [D]elete [Q]uit" );
                input = scan.next().toUpperCase();

            }else if(input.equals("C")){
                create(calendar);
                System.out.println("Select one of the following main menu options:" + "\n[V]iew by [C]reate, [G]o to [E]vent list [D]elete [Q]uit" );
                input = scan.next().toUpperCase();


            }else if(input.equals("G")){
                go(calendar);
                System.out.println("Select one of the following main menu options:" + "\n[V]iew by [C]reate, [G]o to [E]vent list [D]elete [Q]uit" );
                input = scan.next().toUpperCase();

            }else if(input.equals("E")){
                eventList(calendar);
                System.out.println("Select one of the following main menu options:" + "\n[V]iew by [C]reate, [G]o to [E]vent list [D]elete [Q]uit" );
                input = scan.next().toUpperCase();


            }else if(input.equals("D")){
                remove(calendar);
                System.out.println("Select one of the following main menu options:" + "\n[V]iew by [C]reate, [G]o to [E]vent list [D]elete [Q]uit" );
                input = scan.next().toUpperCase();

            }else if(input.equals("Q")){

                break;
            }else{
                System.out.println("Please enter a valid input: [V]iew by [C]reate, [G]o to [E]vent list [D]elete [Q]uit");
                input = scan.next().toUpperCase();
            }

        }

    }

    public static void displayDayEvents(LocalDate currentDate, MyCalendar calendar){

        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E, MMM d yyyy");
        List<Event> events = calendar.getEvents();
        boolean eventFound = false;


        System.out.println(dayFormatter.format(currentDate));
        for(Event event : events)
        {
            if(currentDate.equals(event.getDate()))
            {
                System.out.println(event.getEventName() + " : " + event.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")) + " - " + event.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")));
                eventFound = true;
            }

        }
        if(!eventFound)
        {
            System.out.println("No events found today");
        }
        System.out.println();
    }

    public static void displayMonthEvents(LocalDate currentDate, MyCalendar calendar){
        List<Event> events = calendar.getEvents();
        LocalDate firstDayMonth = currentDate.withDayOfMonth(1);
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        int firstDayOfWeek = firstDayMonth.getDayOfWeek().getValue(); // 1 = Monday, 7 = Sunday
        int monthLength = currentDate.lengthOfMonth();
        int dayOfMonth = 1;

        System.out.println(monthFormatter.format(currentDate));
        System.out.println("Su Mo Tu We Th Fr Sa");

        // Adjust for 0-based index if Sunday is considered the first day of the week
        int offset = (firstDayOfWeek == 7) ? 0 : firstDayOfWeek; // This ensures Sunday is at index 0, Monday at 1, etc.
        for (int i = 0; i < offset; i++) {
            System.out.print("   ");
        }

        while (dayOfMonth <= monthLength) {
            LocalDate currentDay = firstDayMonth.withDayOfMonth(dayOfMonth);
            boolean hasEvent = false;
            for (Event event : events) {
                if (event.isRecurring()) {
                    if (event.getRecurringDays().contains(currentDay.getDayOfWeek().toString().substring(0, 1)) &&
                            !currentDay.isBefore(event.getStartDate()) &&
                            !currentDay.isAfter(event.getEndDate())) {
                        hasEvent = true;
                        break;
                    }
                } else {
                    if (currentDay.equals(event.getDate())) {
                        hasEvent = true;
                        break;
                    }
                }
            }

            if (currentDay.getDayOfWeek() == DayOfWeek.SUNDAY && dayOfMonth != 1) {
                System.out.println(); // New line for new week
            }

            // Print day of month with event indicator
            if (hasEvent) {
                System.out.print("[" + dayOfMonth + "]");
            } else {
                System.out.print(dayOfMonth);
            }

            // Adjust spacing after the day number
            System.out.print(dayOfMonth < 10 ? "  " : " "); // Extra space for single-digit days

            dayOfMonth++;
        }
        System.out.println(); // Finish the last line
    }



    //allows users to schedule a new event
    public static void create(MyCalendar calendar){
        Scanner scan = new Scanner(System.in);
        DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        DateTimeFormatter  timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        System.out.println("Enter the event name:");
        String eventName = scan.nextLine().trim();
        LocalDate date = null;
        LocalTime startTime = null;
        LocalTime endTime = null;
        boolean isBefore = false;
        while(date == null)
        {
            System.out.println("Please enter the date in the following format: MM/DD/YYYY");
            String dateInput = scan.nextLine().trim();
            try{
                date = LocalDate.parse(dateInput, dayFormat);
            }catch (DateTimeException e){
                System.out.println("Error: Wrong date format");
            }
        }
        while(!isBefore)
        {
            while(startTime == null)
            {
                System.out.println("Please enter the start time of the event in 24 hour clock format");
                String startTimeInput = scan.nextLine().trim();
                try{
                    startTime = LocalTime.parse(startTimeInput, timeFormat);
                }catch (DateTimeException e)
                {
                    System.out.println("Error: Wrong time format");
                }
            }

            while(endTime == null)
            {
                System.out.println("Please enter the end time of the event in 24 hour clock format");
                String endTimeInput = scan.nextLine().trim();
                try{
                    endTime = LocalTime.parse(endTimeInput, timeFormat);
                }catch (DateTimeException e)
                {
                    System.out.println("Error: Wrong time format");
                }
            }
            if(startTime.isAfter(endTime))
            {
                System.out.println("The start time of event if after end time. Please try again");
            }else if(startTime.isBefore((endTime)))
            {
                isBefore = true;
            }else{
                System.out.println("The start time of event is the same as end time. Please try again");
            }


        }
        Event e = new Event(eventName, date, startTime, endTime);
        //calendar.addEvent(e);
        calendar.printEvents("src/events.txt");
        try (FileWriter fileWriter = new FileWriter("src/events.txt", true); // Append to the file
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter out = new PrintWriter(bufferedWriter)) {
            out.println(e.getEventName());
            out.printf("%s %s %s%n",
                    e.getDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")),
                    e.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")),
                    e.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            System.out.println("Event successfully added");
        } catch (IOException ex) {
            System.err.println("Error writing to file: " + ex.getMessage());
        }
    }


    public static void remove(MyCalendar calendar) {
        Scanner scan = new Scanner(System.in);
        DateTimeFormatter dayFormat = DateTimeFormatter.ofPattern("MM/dd/yy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

        System.out.println("Loading events from file...");
        System.out.println("Current Events:\n---------------------------------------------");
        calendar.printEvents("src/events.txt");

       //calendar.loadEvents("src/events.txt");
        List<Event> events = calendar.getEvents();
        //System.out.println("---------------------------------------------");
        System.out.println("[S]elected: the user specifies the date and name of an ONE TIME event. The specific one time event will be deleted.\n" +
                "[A]ll: the user specifies a date and then all ONE TIME events scheduled on the date will be deleted.\n" +
                "[DR]: the user specifies the name of a RECURRING event. The specified recurring event will be deleted. This will delete the recurring event throughout the calendar.\n");
        System.out.println("Please enter one of the following:[S]elected  [A]ll   [DR] ");
        String eventInput = scan.nextLine().toUpperCase();
        String deleteEvent = "";
        if(eventInput.equals("S"))
        {
            System.out.println("Specify the date and name of a ONE TIME event to be removed.\nPlease enter the date in the format: MM/DD/YYYY");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate date = null;
            while (date == null) {
                deleteEvent = scan.nextLine();
                try {
                    date = LocalDate.parse(deleteEvent, formatter);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please use MM/DD/YYYY format.");
                }
            }
            System.out.println("Please specify the name of the ONE TIME event to be removed");
            deleteEvent = scan.nextLine();
            Iterator<Event> iterator = events.iterator();
            boolean isRemoved = false;
            while(iterator.hasNext())
            {
                Event e = iterator.next();
                //System.out.println(e.getDate() + " " + date + " " +e.getEventName() + " " + deleteEvent);
            if(e != null && e.getDate() != null && e.getDate().equals(date) && e.getEventName().equals(deleteEvent))
            {
                iterator.remove();
                isRemoved = true;
                System.out.println("Event removed: " + e.getEventName());

            }else if (e == null || e.getDate() == null)
            {
                //Skips a null event or event with a null date;
            }
            }

            if (isRemoved) {
                System.out.println("Writing updated events back to file...");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/events.txt"))) {
                    for (Event e : events) {
                        writer.write(e.getEventName() + "\n");
                        if (!e.isRecurring()) {
                            writer.write(String.format("%s %s %s\n", e.getDate().format(dayFormat), e.getStartTime().format(timeFormat), e.getEndTime().format(timeFormat)));
                        } else {
                            writer.write(String.format("%s %s %s %s %s\n", e.printRecurringDays(), e.getStartTime().format(timeFormat), e.getEndTime().format(timeFormat), e.getStartDate().format(dayFormat), e.getEndDate().format(dayFormat)));
                        }
                    }
                    writer.flush();  // Ensure all data is written to the file
                    System.out.println("File write complete.");
                } catch (IOException ex) {
                    System.out.println("Error writing to file: " + ex.getMessage());
                }
            } else {
                System.out.println("Event with given date and name not found");
            }

        }else if(eventInput.equals("A"))
        {
            System.out.println("Specify the date in which all ONE TIME events scheduled will be removed in the format: MM/DD/YYYY");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate date = null;
            while (date == null) {
                deleteEvent = scan.nextLine();
                try {
                    date = LocalDate.parse(deleteEvent, formatter);
                    date.format(DateTimeFormatter.ofPattern("MM/dd/yy"));

                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please use MM/DD/YYYY format.");
                }
            }
            boolean isRemoved = false;
            Iterator<Event> iterator = events.iterator();
            while(iterator.hasNext()) {
                Event e = iterator.next();
                if (e != null && e.getDate() != null && e.getDate().equals(date)) {
                    iterator.remove();
                    isRemoved = true;
                    System.out.println("Event removed: " + e.getEventName());
                } else if (e == null || e.getDate() == null) {
                    //Skips a null event or event with a null data;
                }
            }

            if (isRemoved) {
                System.out.println("Writing updated events back to file...");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/events.txt"))) {
                    for (Event e : events) {
                        writer.write(e.getEventName() + "\n");
                        if (!e.isRecurring()) {
                            writer.write(String.format("%s %s %s\n", e.getDate().format(dayFormat), e.getStartTime().format(timeFormat), e.getEndTime().format(timeFormat)));
                        } else {
                            writer.write(String.format("%s %s %s %s %s\n", e.printRecurringDays(), e.getStartTime().format(timeFormat), e.getEndTime().format(timeFormat), e.getStartDate().format(dayFormat), e.getEndDate().format(dayFormat)));
                        }
                    }
                    writer.flush();  // Ensure all data is written to the file
                    System.out.println("File write complete.");
                } catch (IOException ex) {
                    System.out.println("Error writing to file: " + ex.getMessage());
                }
            } else {
                System.out.println("Event not found.");
            }

        }else if(eventInput.equals("DR"))
        {
            System.out.println("Please enter the name of the RECURRING event to remove");
            deleteEvent = scan.nextLine();

            boolean isRemoved = false;
            Iterator<Event> iterator = events.iterator();
            while(iterator.hasNext()) {
                Event e = iterator.next();
                if(e.getEventName().equals(deleteEvent) && e.isRecurring()) {
                    iterator.remove();
                    isRemoved = true;
                    System.out.println("Event removed: " + e.getEventName());
                    break;
                }else{
                    System.out.println("Error: Invalid Event or Event is not Recurring");
                }
            }

            if (isRemoved) {
                System.out.println("Writing updated events back to file...");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/events.txt"))) {
                    for (Event e : events) {
                        writer.write(e.getEventName() + "\n");
                        if (!e.isRecurring()) {
                            writer.write(String.format("%s %s %s\n", e.getDate().format(dayFormat), e.getStartTime().format(timeFormat), e.getEndTime().format(timeFormat)));
                        } else {
                            writer.write(String.format("%s %s %s %s %s\n", e.printRecurringDays(), e.getStartTime().format(timeFormat), e.getEndTime().format(timeFormat), e.getStartDate().format(dayFormat), e.getEndDate().format(dayFormat)));
                        }
                    }
                    writer.flush();  // Ensure all data is written to the file
                    System.out.println("File write complete.");
                } catch (IOException ex) {
                    System.out.println("Error writing to file: " + ex.getMessage());
                }
            } else {
                System.out.println("Event not found.");
            }

        }else{

        }

    }






    public static void go(MyCalendar calendar){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the date you would like to go to in the form of MM/DD/YYYY");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = null;
        while (date == null) {
            String userInput = scanner.nextLine();
            try {
                date = LocalDate.parse(userInput, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use MM/DD/YYYY format.");
            }
        }
        displayDayEvents(date, calendar);


    }

    public static void eventList(MyCalendar calendar){

        calendar.printEvents("src/events.txt");



    }
    public static void saveEvents(MyCalendar calendar) {
        List<Event> events = calendar.getEvents(); // Assuming MyCalendar class has a method getEvents() that returns a List of Event objects.
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        try (PrintWriter out = new PrintWriter(new FileWriter("output.txt"))) {
            for (Event e : events) {
                out.println(e.getEventName()); // Print the event name
                if (!e.isRecurring()) {
                    // For one-time events: "09/28/24 09:30 11:30"
                    out.printf("%s %s %s%n",
                            e.getDate().format(dateFormatter),
                            e.getStartTime().format(timeFormatter),
                            e.getEndTime().format(timeFormatter));
                } else {
                    // For recurring events: "TR 09:00 10:15 08/22/24 12/09/24"
                    out.printf("%s %s %s %s %s%n",
                            e.printRecurringDays(),
                            e.getStartTime().format(timeFormatter),
                            e.getEndTime().format(timeFormatter),
                            e.getStartDate().format(dateFormatter),
                            e.getEndDate().format(dateFormatter));
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to write events to file: " + e.getMessage());
        }
    }

}
