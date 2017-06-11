import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by natali on 6/11/2017.
 */

public class CheckForNotGmailDomain {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String x = input.nextLine();
        Boolean b;
        b=MagicSearch(x);
        if (b) {
            System.out.println("Bingo!");
        }
        else {
            System.out.println("Ooooops,there might be email with domain NOT equals to gmail.com");
        }
    }

    public static boolean MagicSearch (String emailsSequence) {
        Pattern p = Pattern.compile("(?:[A-z\\d\\s\\w]+@gmail.com\\,*)");
        Matcher m= p .matcher(emailsSequence);
        String y=m.replaceAll("");
        return y.equals("");
            }
}


