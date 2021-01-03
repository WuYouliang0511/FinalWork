package com.llj.work;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Test {

    public static void main(String[] args) {
        System.out.println(getRandomStr());
    }


    @NotNull
    private static String getRandomStr() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            int number = random.nextInt(3);
            long result;
            switch (number) {
                case 0:
                    result = Math.round(Math.random() * 25 + 65);
                    sb.append((char) result);
                    break;
                case 1:
                    result = Math.round(Math.random() * 25 + 97);
                    sb.append((char) result);
                    break;
                case 2:
                    sb.append(new Random().nextInt(10));
                    break;
            }
        }
        return sb.toString();
    }
}
