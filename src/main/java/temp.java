import java.time.LocalDate;

public class temp {
    public static void main(String[] args) {
        LocalDate date = LocalDate.parse("10-10-2020");
        System.out.println(date.plusDays(10));
    }
}
