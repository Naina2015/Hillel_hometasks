import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by natali on 6/5/2017.
 */


public class RegexpParser {

    public static void main(String[] args) {
        Scanner a = new Scanner(System.in);

        String email = a.nextLine();

        boolean ValidationCheck = EmailCheck(email);

        if (ValidationCheck) {
            System.out.println("Congrats! Email is Valid");
        } else {
            System.out.println("Sorry,this email is not valid!");
        }


    }

    private static boolean EmailCheck(String email) {
        boolean test;
        Pattern p = Pattern.compile("^.+@[A-Za-z]+\\.[A-Za-z]{2,}$");
        Matcher m = p.matcher(email);
        test = m.matches();
        if (test) {
            return true;
        } else {
            return false;
        }
    }
}
