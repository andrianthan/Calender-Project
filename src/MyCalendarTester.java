import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


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
        System.out.println("Su Mo Tu We Th Fr Sa");
        for(int i = 0; i < firstDayOfWeek; i++)
        {
            System.out.print("   ");
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
        calendar.loadEvents("events.txt");
        System.out.println("Select one of the following main menu options:" + "\n[V]iew by [C]reate, [G]o to [E]vent list [D]elete [Q]uit" );
        String input = scan.next();
        while (!(input.equals("Q")))
        {
            if(input.equals("V")) {
                System.out.println("[D]ay view or [M]onth view ?");
                String viewInput = scan.next();
                view(viewInput, calendar);

            }else if(input.equals("C")){

            }else if(input.equals("G")){

            }else if(input.equals("E")){

            }else if(input.equals("D")){

            }else if(input.equals("Q")){
                break;
            }else{
                System.out.println("Please enter a valid input");
                input = scan.next();
            }
            input = scan.next();

        }

    }

    //method that lets users choose between the day/month view of the calendar
    public static void view(String input, MyCalendar calendar){
        List<Event> events = calendar.events;
        LocalDate currentDate = LocalDate.now();
        int dayOfMonth = 1;
        int monthLength = currentDate.lengthOfMonth();
        LocalDate firstDayMonth = currentDate.withDayOfMonth(1);
        int firstDayOfWeek = firstDayMonth.getDayOfWeek().getValue();
        Scanner scan = new Scanner(System.in);
        input = input.toUpperCase();
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E, MMM d yyyy");
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        boolean eventFound = false;

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
                // Single occurrence events
                if (event.getDate().getMonth() == currentDate.getMonth() && event.getDate().getYear() == currentDate.getYear()) {
                    daysWithEvents.add(event.getDate());
                }
            }
        }

        if(input.equals("D"))
        {
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


        }else if(input.equals("M")){
// Handle month view
            System.out.println(monthFormatter.format(currentDate));
            System.out.println("Su Mo Tu We Th Fr Sa");
            for (int i = 1; i < firstDayOfWeek; i++) {
                System.out.print("   ");
            }
            while (dayOfMonth <= monthLength) {
                LocalDate currentDay = firstDayMonth.withDayOfMonth(dayOfMonth);
                boolean hasEvent = false;
                for (Event event : events) {
                    if (event.isRecurring()) {
                        // Check if the current day's day of week matches any in the event's recurringDays
                        if (event.getRecurringDays().contains(currentDay.getDayOfWeek().toString().substring(0, 1)) &&
                                !currentDay.isBefore(event.getStartDate()) &&
                                !currentDay.isAfter(event.getEndDate())) {
                            hasEvent = true;
                            break;  // Once a match is found, no need to check further
                        }
                    } else {
                        // For non-recurring events, check date directly
                        if (currentDay.equals(event.getDate())) {
                            hasEvent = true;
                            break;
                        }
                    }
                }

                if (currentDay.getDayOfWeek() == DayOfWeek.SUNDAY && dayOfMonth != 1) {
                    System.out.println();
                }
                if (hasEvent) {
                    System.out.print("[" + (dayOfMonth < 10 ? " " : "") + dayOfMonth + "]");
                } else {
                    System.out.print((dayOfMonth < 10 ? "  " : " ") + dayOfMonth);
                }
                dayOfMonth++;
            }
            System.out.println();

        }else{
            System.out.println("Please enter a valid input of D or M");
            input = scan.next();
            view(input, calendar);
        }

    }

    //allows users to schedule a new event
    public void create(){

    }

    //
    public void go(){

    }

    public void eventList(){

    }

    public void delete(){

    }

}
