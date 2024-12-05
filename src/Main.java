import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Main {
    public static void main(String[] args) {
        boolean dateValid = false;
        System.out.println("Please enter date in dd/mm/yyyy format");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while(!dateValid) {
            Scanner scan = new Scanner(System.in);
            String dateInput = scan.nextLine();
            try {
                LocalDate parsedDate = LocalDate.parse(dateInput, formatter);
                if (parsedDate.isAfter(LocalDate.now()) ||
                        parsedDate.isBefore(LocalDate.of(1960, 1, 1))) {
                    System.out.println("Please enter valid date between 01/01/1960 and now");
                } else {
                    System.out.println("Date entered is valid " + parsedDate);
                    dateValid = true;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format try again.");
            }
        }
        }

    }
