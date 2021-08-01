package com.codemanship.asyncrover;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Rover rover = new Rover("N", 0, 0);

        new Thread(() -> { rover.run(); }).start();

        while(true){
            System.out.println("Please enter instructions...");
            Scanner in = new Scanner(System.in);
            String instructions = in.nextLine().trim();
            System.out.println(instructions);
            if(instructions.trim().equals("x")){
                rover.stop();
                return;
            }
            rover.go(instructions);
        }
    }
}
