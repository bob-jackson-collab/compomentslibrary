package com.example.momo.myapplication;

/**
 * <pre>
 *   author:yangsong
 *   time:2018/12/29
 *   desc: MyApplication
 * </pre>
 */
public class SortUtils {


    public static void bubbleSort(int numbers[]) {
        int temp;
        int size = numbers.length;
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - 1 - i; j++) {
                if (numbers[j] > numbers[j + 1]) {
                    temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
        }
    }

    public static void selectSort(int numbers[]) {
        int temp;
        int size = numbers.length;
        int k;
        for (int i = 0; i < size - 1; i++) {
            k = i;  // 待确定的位置
            for (int j = size - 1 - i; j > 0; j--) {
                if (numbers[k] > numbers[j]) {
                    k = j;
                }
            }
            temp = numbers[i];
            numbers[i] = numbers[k];
            numbers[k] = temp;
        }
    }

    public static void inserSort(int numbers[]) {
        int temp;
        int size = numbers.length;
        int j;
        for (int i = 1; i < size; i++) {
            temp = numbers[i];
            for (j = i; j > 0 && temp < numbers[j - 1]; j--) {
                numbers[j] = numbers[j - 1];
            }
            numbers[j] = temp;
        }
    }

}
