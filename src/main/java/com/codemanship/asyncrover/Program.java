package com.codemanship.asyncrover;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) {
        Rover rover = new Rover("N", 0, 0, () -> instructionsDone());

        new Thread(() -> { rover.run(); }).start();

        System.out.println("Please enter instructions (r for right, l for left, f for forward, b for back, x to exit)...");

        while(true){
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

    public static void instructionsDone(){
        System.out.println("Awaiting more instructions...");
    }
}
