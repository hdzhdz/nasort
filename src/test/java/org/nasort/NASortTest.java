package org.nasort;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class NASortTest {
  public Comparator<String> comparator = new NASort();

  /**
   * Numbers come before characters.
   */
  @Test
  public void numberComeFirst() {
    assertArrayEquals(nasort(new String[] { "a", "1" }), new String[] { "1", "a" });
  }

  /**
   * Date and time ordering
   */
  @Test
  public void comparingDates() {
    assertArrayEquals(
        nasort(new String[] { "10/12/2008", "10/11/2008", "10/11/2007", "10/12/2007" }),
        new String[] { "10/11/2007", "10/12/2007", "10/11/2008", "10/12/2008" });
    assertArrayEquals(
        nasort(new String[] { "01/01/2008", "01/10/2008", "01/01/1992", "01/01/1991" }),
        new String[] { "01/01/1991", "01/01/1992", "01/01/2008", "01/10/2008" });
    assertArrayEquals(
        nasort(new String[] { "Wed Jan 01 2010 00:00:00 GMT-0800 (Pacific Standard Time)",
            "Thu Dec 31 2009 00:00:00 GMT-0800 (Pacific Standard Time)",
            "Wed Jan 01 2010 00:00:00 GMT-0500 (Eastern Standard Time)" }),
        new String[] { "Thu Dec 31 2009 00:00:00 GMT-0800 (Pacific Standard Time)",
            "Wed Jan 01 2010 00:00:00 GMT-0500 (Eastern Standard Time)",
            "Wed Jan 01 2010 00:00:00 GMT-0800 (Pacific Standard Time)" });
    assertArrayEquals(
        nasort(new String[] { "Saturday, July 3, 2010", "Monday, August 2, 2010", "Monday, May 3, 2010" }),
        new String[] { "Monday, May 3, 2010", "Saturday, July 3, 2010", "Monday, August 2, 2010" });
    assertArrayEquals(
        nasort(new String[] { "Mon, 15 Jun 2009 20:45:30 GMT", "Mon, 3 May 2010 17:45:30 GMT",
            "Mon, 15 Jun 2009 17:45:30 GMT" }),
        new String[] { "Mon, 15 Jun 2009 17:45:30 GMT", "Mon, 15 Jun 2009 20:45:30 GMT",
            "Mon, 3 May 2010 17:45:30 GMT" });
    assertArrayEquals(
        nasort(new String[] { "Saturday, July 3, 2010 1:45 PM", "Saturday, July 3, 2010 1:45 AM",
            "Monday, August 2, 2010 1:45 PM", "Monday, May 3, 2010 1:45 PM" }),
        new String[] { "Monday, May 3, 2010 1:45 PM", "Saturday, July 3, 2010 1:45 AM",
            "Saturday, July 3, 2010 1:45 PM", "Monday, August 2, 2010 1:45 PM" });
    assertArrayEquals(
        nasort(new String[] { "Saturday, July 3, 2010 1:45:30 PM", "Saturday, July 3, 2010 1:45:29 PM",
            "Monday, August 2, 2010 1:45:01 PM", "Monday, May 3, 2010 1:45:00 PM" }),
        new String[] { "Monday, May 3, 2010 1:45:00 PM", "Saturday, July 3, 2010 1:45:29 PM",
            "Saturday, July 3, 2010 1:45:30 PM", "Monday, August 2, 2010 1:45:01 PM" });
    assertArrayEquals(
        nasort(new String[] { "2/15/2009 1:45 PM", "1/15/2009 1:45 PM", "2/15/2009 1:45 AM" }),
        new String[] { "1/15/2009 1:45 PM", "2/15/2009 1:45 AM", "2/15/2009 1:45 PM" });
    assertArrayEquals(nasort(
        new String[] { "2010-06-15T13:45:30", "2009-06-15T13:45:30", "2009-06-15T01:45:30.2", "2009-01-15T01:45:30" }),
        new String[] { "2009-01-15T01:45:30", "2009-06-15T01:45:30.2", "2009-06-15T13:45:30", "2010-06-15T13:45:30" });
    assertArrayEquals(
        nasort(new String[] { "2010-06-15 13:45:30", "2009-06-15 13:45:30", "2009-01-15 01:45:30" }),
        new String[] { "2009-01-15 01:45:30", "2009-06-15 13:45:30", "2010-06-15 13:45:30" });
    assertArrayEquals(
        nasort(new String[] { "Mon, 15 Jun 2009 20:45:30 GMT", "Mon, 15 Jun 2009 20:45:30 PDT",
            "Mon, 15 Jun 2009 20:45:30 EST" }),
        new String[] { "Mon, 15 Jun 2009 20:45:30 GMT", "Mon, 15 Jun 2009 20:45:30 EST",
            "Mon, 15 Jun 2009 20:45:30 PDT" });
    assertArrayEquals(
        nasort(new String[] { "1245098730000", "14330728000", "1245098728000" }),
        new String[] { "14330728000", "1245098728000", "1245098730000" });
  }

  /**
   * Version ordering
   */
  @Test
  public void comparingVersion() {
    assertArrayEquals(
        nasort(new String[] { "1.0.2", "1.0.1", "1.0.0", "1.0.9" }),
        new String[] { "1.0.0", "1.0.1", "1.0.2", "1.0.9" });
    assertArrayEquals(
        nasort(new String[] { "1.0.03", "1.0.003", "1.0.002", "1.0.0001" }),
        new String[] { "1.0.0001", "1.0.002", "1.0.003", "1.0.03" });
    assertArrayEquals(
        nasort(new String[] { "1.1beta", "1.1.2alpha3", "1.0.2alpha3", "1.0.2alpha1", "1.0.1alpha4", "2.1.2", "2.1.1" }),
        new String[] { "1.0.1alpha4", "1.0.2alpha1", "1.0.2alpha3", "1.1.2alpha3", "1.1beta", "2.1.1", "2.1.2" });
    assertArrayEquals(
        nasort(new String[] { "myrelease-1.1.3", "myrelease-1.2.3", "myrelease-1.1.4", "myrelease-1.1.1", "myrelease-1.0.5" }),
        new String[] { "myrelease-1.0.5", "myrelease-1.1.1", "myrelease-1.1.3", "myrelease-1.1.4", "myrelease-1.2.3" });
  }

  /**
   * Version ordering
   */
  @Test
  public void comparingIP() {
    assertArrayEquals(
        nasort(new String[] { "192.168.0.100", "192.168.0.1", "192.168.1.1", "192.168.0.250", "192.168.1.123","10.0.0.2", "10.0.0.1" }),
        new String[] { "10.0.0.1", "10.0.0.2", "192.168.0.1", "192.168.0.100", "192.168.0.250", "192.168.1.1","192.168.1.123" });
  }

  /**
   * File name ordering
   */
  @Test
  public void comparingFileName() {
    assertArrayEquals(
        nasort(new String[] { "img12.png", "img10.png", "img2.png", "img1.png" }),
        new String[] { "img1.png", "img2.png", "img10.png", "img12.png" });
    assertArrayEquals(
        nasort(new String[] { "car.mov", "01alpha.sgi", "001alpha.sgi", "my.string_41299.tif", "organic2.0001.sgi" }),
        new String[] { "001alpha.sgi", "01alpha.sgi", "car.mov", "my.string_41299.tif", "organic2.0001.sgi" });
    assertArrayEquals(
        nasort(new String[] { "./system/kernel/js/01_ui.core.js", "./system/kernel/js/00_jquery-1.3.2.js","./system/kernel/js/02_my.desktop.js" }),
        new String[] { "./system/kernel/js/00_jquery-1.3.2.js", "./system/kernel/js/01_ui.core.js","./system/kernel/js/02_my.desktop.js" });
  }

  /**
   * Hex ordering
   */
  @Test
  public void comparingComplexNumber() {
    assertArrayEquals(
        nasort(new String[] { "10.0401", "10.022", "10.042", "10.021999" }),
        new String[] { "10.021999", "10.022", "10.0401", "10.042" });
    assertArrayEquals(nasort(new String[] { "-1", "-2", "4", "-3", "0", "-5" }),
        new String[] { "-5", "-3", "-2", "-1", "0", "4" });
    assertArrayEquals(nasort(new String[] { "0001", "002", "001", "1" }), new String[] { "0001", "001", "1", "002" });
    assertArrayEquals(
        nasort(new String[] { "1.528535047e5", "1.528535047e7", "1.52e15", "1.528535047e3", "1.59e-3" }),
        new String[] { "1.59e-3", "1.528535047e3", "1.528535047e5", "1.528535047e7", "1.52e15" });
    assertArrayEquals(
        nasort(new String[] { "-1", "-2", "4", "-3", "0", "-5" }),
        new String[] { "-5", "-3", "-2", "-1", "0", "4" });
    printArr(nasort(new String[] { "0xA", "0x9", "0x99" }));
    assertArrayEquals(nasort(new String[] { "0xA", "0x9", "0x99" }), new String[] { "0x9", "0xA", "0x99" });
    assertArrayEquals(
        nasort(new String[] { "0xZZ", "0xVVV", "0xVEV", "0xUU" }),
        new String[] { "0xUU", "0xVEV", "0xVVV", "0xZZ" });
    assertArrayEquals(
        nasort(new String[] { "\u0044", "\u0055", "\u0054", "\u0043" }),
        new String[] { "\u0043", "\u0044", "\u0054", "\u0055" });
  }

  /**
   * Case sensitive ordering
   */
  @Test
  public void comparingCaseSensitive() {
    assertArrayEquals(
        nasort(new String[] { "A", "b", "C", "d", "E", "f" }),
        new String[] { "A", "b", "C", "d", "E", "f" });
    assertArrayEquals(
        nasort(new String[] { "A", "C", "E", "b", "d", "f" }),
        new String[] { "A", "b", "C", "d", "E", "f" });
    Comparator<String> sensitiveComparator = new NASort(true);
    String[] arr1 = new String[] { "A", "C", "E", "b", "d", "f" };
    Arrays.sort(arr1, sensitiveComparator);
    assertArrayEquals(arr1, new String[] { "A", "C", "E", "b", "d", "f" });
    String[] arr2 = new String[] { "A", "b", "C", "d", "E", "f" };
    Arrays.sort(arr2, sensitiveComparator);
    assertArrayEquals( arr2, new String[] { "A", "C", "E", "b", "d", "f" });
  }

  /**
   * Case sensitive ordering
   */
  @Test
  public void complexCases() {
    printArr(nasort(new String[] { "T78", "U17", "U10", "U12", "U14", "745", "U7", "01", "485", "S16", "S2", "S22", "1081",
    "S25", "1055", "779", "776", "771", "44", "4", "87", "1091", "42", "480", "952", "951", "756", "1000",
    "824", "770", "666", "633", "619", "1", "991", "77H", "PIER-7", "47", "29", "9", "77L", "433" }));
    assertArrayEquals(
        nasort(new String[] { "T78", "U17", "U10", "U12", "U14", "745", "U7", "01", "485", "S16", "S2", "S22", "1081",
            "S25", "1055", "779", "776", "771", "44", "4", "87", "1091", "42", "480", "952", "951", "756", "1000",
            "824", "770", "666", "633", "619", "1", "991", "77H", "PIER-7", "47", "29", "9", "77L", "433" }),
        new String[] { "01", "1", "4", "9", "29", "42", "44", "47", "77H", "77L", "87", "433", "480", "485", "619",
            "633", "666", "745", "756", "770", "771", "776", "779", "824", "951", "952", "991", "1000", "1055", "1081",
            "1091", "PIER-7", "S2", "S16", "S22", "S25", "T78", "U7", "U10", "U12", "U14", "U17" });
    assertArrayEquals(
        nasort(new String[] { "FSI stop, Position: 5", "Mail Group stop, Position: 5", "Mail Group stop, Position: 5",
            "FSI stop, Position: 6", "FSI stop, Position: 6", "Newsstand stop, Position: 4",
            "Newsstand stop, Position: 4", "FSI stop, Position: 5" }),
        new String[] { "FSI stop, Position: 5", "FSI stop, Position: 5", "FSI stop, Position: 6",
            "FSI stop, Position: 6", "Mail Group stop, Position: 5", "Mail Group stop, Position: 5",
            "Newsstand stop, Position: 4", "Newsstand stop, Position: 4" });
    assertArrayEquals(
        nasort(new String[] { "9", "11", "22", "99", "A", "aaaa", "bbbb", "Aaaa", "aAaa", "aa", "AA", "Aa", "aA", "BB",
            "bB", "aaA", "AaA", "aaa" }),
        new String[] { "9", "11", "22", "99", "A", "aa", "AA", "Aa", "aA", "aaA", "AaA", "aaa", "aaaa", "Aaaa", "aAaa",
            "BB", "bB", "bbbb" });
    Comparator<String> sensitiveComparator = new NASort(true);
    String[] arr1 = new String[] { "9", "11", "22", "99", "A", "aaaa", "bbbb", "Aaaa", "aAaa", "aa", "AA", "Aa", "aA", "BB",
            "bB", "aaA", "AaA", "aaa" };
    Arrays.sort(arr1, sensitiveComparator);
    assertArrayEquals(arr1, 
        new String[] { "9", "11", "22", "99", "A", "AA", "Aa", "AaA", "Aaaa", "BB", "aA", "aAaa", "aa", "aaA", "aaa",
            "aaaa", "bB", "bbbb" });
    assertArrayEquals(
        nasort(new String[] { "5D", "1A", "2D", "33A", "5E", "33K", "33D", "5S", "2C", "5C", "5F", "1D", "2M" }),
        new String[] { "1A", "1D", "2C", "2D", "2M", "5C", "5D", "5E", "5F", "5S", "33A", "33D", "33K" });
  }

  /**
   * Reverse ordering
   */
  @Test
  public void reverseComparingComplexNumber() {
    assertArrayEquals(
        reverseSort(new String[] { "10.0401", "10.022", "10.042", "10.021999" }),
        new String[] { "10.042", "10.0401", "10.022", "10.021999" });
    assertArrayEquals(reverseSort(new String[] { "-1", "-2", "4", "-3", "0", "-5" }),
        new String[] { "4", "0", "-1", "-2", "-3", "-5" });
    
  }

  // Util

  /**
   * Helper method to perform NASort on input String.
   * 
   * @param arr input string array.
   * @return NASorted string array.
   */
  public String[] nasort(String[] arr) {
    Arrays.sort(arr, comparator);
    return arr;
  }

  /**
   * Helper method to perform NASort on input String in reverse.
   * 
   * @param arr input string array.
   * @return NASorted string array.
   */
  public String[] reverseSort(String[] arr) {
    Comparator<String> reverseComparator = new NASort(-1);
    Arrays.sort(arr, reverseComparator);
    return arr;
  }

  /**
   * Print string array.
   * @param arr input string array.
   */
  public void printArr(String[] arr) {
    System.out.print("{");
    for (int i = 0; i < arr.length - 1; i++) {
      System.out.print(arr[i] +", ");
    }
    System.out.println(arr[arr.length - 1] +"}");
  }
}
