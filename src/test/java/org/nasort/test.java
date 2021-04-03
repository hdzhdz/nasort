package org.nasort;

import static org.junit.Assert.assertArrayEquals;

import java.lang.annotation.Repeatable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import org.junit.Test;

public class test {
  public static Comparator<String> comparator = new NASort();
  private static int SIZE;


  public static void main(String[] args) {
    int[] s_arr = {1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
    for (int i = 0; i < s_arr.length; i++) {
      System.out.println("- Testing size: " + s_arr[i]);
      for (int j = 1; j < 4; j++) {
        System.out.println("  >>> Trial: " + j);
        sort(s_arr[i]);
      }
    }
  }
  /**
   * Numbers come before characters.
   */
  @Test
  public static void sort(int s) {
    SIZE = s;
    String[] sorted = new String[SIZE];
    String[] unSorted = new String[SIZE];
    setup(sorted, unSorted);
    long startTime = System.currentTimeMillis();
    String[] arr = nasort(unSorted);
    long endTime = System.currentTimeMillis();
    long duration = (endTime - startTime);
    System.out.println("       Java Natural sort order sorted String[" + SIZE + "] in : " + duration + " milliseconds");
    assertArrayEquals(arr, sorted);
  }

  // Util

  /**
   * Helper method to perform NASort on input String.
   * 
   * @param arr input string array.
   * @return NASorted string array.
   */
  private static String[] nasort(String[] arr) {
    Arrays.sort(arr, comparator);
    return arr;
  }

  private static void setup(String[] sorted, String[] unSorted) {
    Random rd = new Random(); // creating Random object
    int[] arr = new int[SIZE];
    for (int i = 0; i < arr.length; i++) {
      arr[i] = rd.nextInt(); // storing random integers in an array
    }
    int[] sortedArr = arr.clone();
    long startTime = System.currentTimeMillis();
    Arrays.sort(sortedArr);
    long endTime = System.currentTimeMillis();
    long duration = (endTime - startTime);
    System.out.println("       Java sorted Int[" + SIZE + "] in : " + duration + " milliseconds");
    for (int i = 0; i < arr.length; i++) {
      unSorted[i] = "" + arr[i];
      sorted[i] = "" + sortedArr[i];
    }
    startTime = System.currentTimeMillis();
    Arrays.sort(unSorted);
    endTime = System.currentTimeMillis();
    duration = (endTime - startTime);
    System.out.println("       Java Lexicographic order sorted String[" + SIZE + "] in : " + duration + " milliseconds");
  }
}
