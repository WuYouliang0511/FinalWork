package com.llj.work;

import org.jetbrains.annotations.NotNull;

/**
 * @Author: Wu Youliang
 * @CreateDate: 2020/12/31 下午5:13
 * @Company LotoGram
 */
public class Test {

    public static void main(String[] args) {
        int[] a = findIndex("0123456789", "4567");
    }


    private static int[] findIndex(@NotNull String str1, @NotNull String str2) {
        int length1 = str1.length();
        int length2 = str2.length();
        int[] index = new int[]{0, 0};

        for (int i = 0; i < length1 - length2 + 1; i++) {
            System.out.println(str1.substring(i, i + length2));
            if (str1.substring(i, i + length2).equals(str2)) {
                System.out.println(i + "   " + (i + length2));
            }
        }


        return new int[]{1, 1};


    }
}
