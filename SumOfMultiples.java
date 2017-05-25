import java.util.Scanner;

/**
 * Created by natali on 5/25/2017.
 */
public class SumOfMultiples {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println(SumOfAllMultiples(scan.nextInt()));
    }

    private static int SumArifmProgr(int a1, int an, int n) {
        return (a1 + an) * n / 2;
    }

    private static int SumOfAllMultiples(int N) {
    int x=0;
    int y=N%3;
    if(y==0){
       x = x + SumArifmProgr(3,N-3,(N-3)/3);
    }
    else{
        x = x + SumArifmProgr(3,N-y,(N-y)/3);
    }
        y=N%5;
        if(y==0){
            x = x + SumArifmProgr(5,N-5,(N-5)/5);
        }
        else{
            x = x + SumArifmProgr(5,N-y,(N-y)/5);
        }
    return x;
    }


}