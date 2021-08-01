package com.codemanship.asyncrover;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class Rover {
    public static final int FORWARD = 1;
    public static final int BACKWARD = -1;
    private String direction;
    private int x;
    private int y;
    private final Runnable callback;
    private boolean running = false;
    private Queue<Character> queue = new LinkedBlockingQueue<>();
    private final HashMap<String, int[]> vectors = new HashMap<>(){{
        put("N", new int[]{0, 1});
        put("E", new int[]{1, 0});
        put("S", new int[]{0, -1});
        put("W", new int[]{-1, 0});
    }};

    public Rover(String direction, int x, int y, Runnable callback) {
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.callback = callback;
    }

    public void run() {
        running = true;

        HashMap<Character, Runnable> actions = new HashMap<>(){{
            put('r', () -> right());
            put('l', () -> left());
            put('f', () -> forward());
            put('b', () -> back());
        }};

        while(running){
            if(!queue.isEmpty()){
                Character instruction = queue.remove();
                System.out.println("Instruction: " + instruction);
                actions.get(instruction).run();
                System.out.println(toString());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(queue.isEmpty()){
                    callback.run();
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Rover{" +
                "direction='" + direction + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public void go(String instructions){
        char[] characters = instructions.toCharArray();

        for (int i = 0; i < characters.length; i++) {
            char c = characters[i];
            queue.add(c);
        }

    }

    public void stop(){
        running = false;
    }

    private void back() {
        move(BACKWARD);
    }

    private void forward() {
        move(FORWARD);
    }

    private void move(int coefficient) {
        int[] vector = vectors.get(direction);
        x += vector[0] * coefficient;
        y += vector[1] * coefficient;
    }

    private void left() {
        turn(Arrays.asList("N", "W", "S", "E"));
    }

    private void right() {
        turn(Arrays.asList("N", "E", "S", "W"));
    }

    private void turn(List<String> compass) {
        int current = compass.indexOf(direction);
        direction = compass.get((current + 1) % 4);
    }
}
