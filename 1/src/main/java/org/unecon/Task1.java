package org.unecon;

import java.util.Arrays;
import java.util.Random;

public class Task1 {
    private static int[] genArray(int length) {
        Random rnd = new Random();
        int[] array = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = rnd.nextInt(1000);
        }
        return array;
    }

    public static int[] changeArray(int[] array, int length) {
        int left = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] % 2 != 0){
                int safe = array[i];
                for (int j = i; j > left; j--) {
                        array[j] = array[j - 1];
                }
                array[left] = safe;
                left++;
            }
        }
        return array;
    }

    public static void main(String[] args) {
        int length = 10;
        int[] array = genArray(length);
        System.out.println(Arrays.toString(array));
        changeArray(array, length);
        System.out.println(Arrays.toString(array));
    }
}

