package com.company;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {



        Scanner in = new Scanner(System.in);

        System.out.print("Введите число: ");
        int x = in.nextInt();
        for(int i=0;i<x;i++){
            for(int j=x;j>0;j--){
                if(j<=i) {
                    System.out.print("#");
                }
                else{
                    System.out.print(" ");
                }
            }
            System.out.print(" ");
            for(int j=0;j<x;j++){
                if(j<=i) {
                    System.out.print("#");
                }
                else{
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
