import java.time.DayOfWeek;
import java.time.LocalDate;


public class MyCalendarTester {
    public static void main(String [] args)
    {
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



    }
}
