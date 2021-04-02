package org.nasort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Instead of sorting numbers in ASCII order like "Java's Natural Order"
 * (Lexicographic) this algorithm sorts numbers in natural sort order: "an
 * ordering of strings in alphabetical order, except that multi-digit numbers
 * are treated atomically, i.e., as if they were a single character. ".
 */
class NASort implements Comparator<String> {
  
  private boolean caseInsensitive;
  
  /**
   * Create a comparator to sort case sensitive or insensitive.
   * @param caseInsensitive true = insensitive, false = sensitive.
   */
  public NASort(boolean caseInsensitive) {
    this.caseInsensitive = caseInsensitive;
  }

  /**
   * Case insensitive sort.
   */
  public void insensitive() {
    caseInsensitive = true;
  }

  /**
   * Case sensitive sort.
   */
  public void sensitive() {
    caseInsensitive = false;
  }

  @Override
  public int compare(String o1, String o2) {
    if (o1 == null && o2 == null)
      return 0;
    if (o1 == null)
      return 1;
    if (o2 == null)
      return -1;
    // Comparing by Chunk
    List<String> o1Chunks = toChunk(o1);
    List<String> o2Chunks = toChunk(o2);
    System.out.println("List 1 = " + o1Chunks.toString() + "; List 2 = " + o2Chunks.toString());
    for (int i = 0; i < Math.min(o1Chunks.size(), o2Chunks.size()); i++) {
      String o1Chunk = o1Chunks.get(i);
      String o2Chunk = o2Chunks.get(i);
      System.out.print("Chunk 1 = " + o1Chunk + "; Chunk 2 = " + o2Chunk + " -> ");
      if (!o1Chunk.equals(o2Chunk)) {
        // if negative number
        // TODO: Interesting problem, with working around '-' because file 
        // "a-1.jpg" go before "a-2.jpg" while -1 > -2. Chunking negative will be different as well
        if (o1.charAt(0) != '-' || o2.charAt(0) != '-') {

        }
        boolean o1Type = Character.isDigit(o1Chunk.charAt(0));
        boolean o2Type = Character.isDigit(o2Chunk.charAt(0));
        // if different types or both string -> lexi order
        if (o1Type != o2Type || (o1Type == false && o2Type == false)) {
          System.out.println("LEXI ORDER");
          if (caseInsensitive) return o1Chunk.compareToIgnoreCase(o2Chunk);
          return o1Chunk.compareTo(o2Chunk);
        } else {
          System.out.println("INT ORDER");
          int o1Int = Integer.parseInt(o1Chunk);
          int o2Int = Integer.parseInt(o2Chunk);
          if (o1Int == o2Int && o1Chunk.length() != o2Chunk.length()) {
            return o2Chunk.length() - o1Chunk.length();
          }
          return o1Int - o2Int;
        }
      }
    }
    return o1Chunks.size() - o2Chunks.size();
  }

  /**
   * Transform the string to a list of string in which all the characters are
   * either all digit or not digit.
   * 
   * @param s The input string.
   * @return List of same type string.
   */
  private static List<String> toChunk(String s) {
    if (s.length() == 0)
      return new ArrayList<>();
    List<String> ret = new ArrayList<>();
    boolean currentString = Character.isDigit(s.charAt(0));
    StringBuilder sb = new StringBuilder();
    sb.append(s.charAt(0));
    for (int i = 1; i < s.length(); i++) {
      if (currentString == Character.isDigit(s.charAt(i))) {
        sb.append(s.charAt(i));
      } else {
        currentString = Character.isDigit(s.charAt(i));
        ret.add(sb.toString());
        sb.setLength(0);
        sb.append(s.charAt(i));
      }
    }
    if (sb.length() != 0)
      ret.add(sb.toString());
    return ret;
  }
  /*
  public static void main(String[] args) {
    // String s = "abc1000ABC9";
    String[] arr = { null, "2000", "3", "01", "00001", "1", "aaa", "aa", "aa1" };
    Comparator<String> c = new NASort();
    Arrays.sort(arr, c);
    for (String s : arr) {
      System.out.print(s + ", ");
    }
  }
  */
}
