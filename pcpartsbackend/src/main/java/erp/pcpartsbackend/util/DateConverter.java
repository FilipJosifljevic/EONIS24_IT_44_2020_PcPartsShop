package erp.pcpartsbackend.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {
    public static Date convertStringToDate(String dateString) throws ParseException {
        // Define the date format expected in the input string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // Parse the date string into a Date object
        Date date = dateFormat.parse(dateString);

        return date;
    }
}