import java.io.*;
import java.util.Scanner;

public class Parsing_Url{

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.println("Укажите доменное имя: ");
        String Str = sc.nextLine();
        //String Str = new String("http://proglang.su/java/128");

        int startIndex=(Str.indexOf("://"))+3;
        int endIndex=(Str.indexOf("/", startIndex));


        System.out.print("Возвращаемое значение: ");
        System.out.println(Str.substring(startIndex,endIndex));
    }
}