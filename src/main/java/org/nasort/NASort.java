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
  
  private boolean caseSensitive;
  private int isDouble = 0;
  private int isHex = 0;
  private int order = 1;

  public NASort() {
    caseSensitive = false;
  }
  
  /**
   * Create a comparator to sort case sensitive or insensitive.
   * @param caseSensitive true = insensitive, false = sensitive.
   */
  public NASort(boolean caseSensitive) {
    this.caseSensitive = caseSensitive;
  }

  /**
   * Create a comparator to sort case in reverse.
   * @param order the desire order: 1 = ASC, -1 = DESC
   */
  public NASort(int order) {
    this.order = order;
  }

  /**
   * Create a comparator to sort case sensitive or insensitive with asc or desc order.
   * @param caseSensitive true = insensitive, false = sensitive.
   * @param order the desire order: 1 = ASC, -1 = DESC
   */
  public NASort(boolean caseSensitive, int order) {
    this.caseSensitive = caseSensitive;
    this.order = order;
  }

  @Override
  public int compare(String o1, String o2) {
    if (o1 == null && o2 == null)
      return 0;
    if (o1 == null)
      return 1 * order;
    if (o2 == null)
      return -1 * order;
    // Comparing by Chunk
    List<String> o1Chunks = toChunk(o1);
    List<String> o2Chunks = toChunk(o2);
    // System.out.println("List 1 = " + o1Chunks.toString() + "; List 2 = " + o2Chunks.toString());
    for (int i = 0; i < Math.min(o1Chunks.size(), o2Chunks.size()); i++) {
      String o1Chunk = o1Chunks.get(i);
      String o2Chunk = o2Chunks.get(i);
      // System.out.print("Chunk 1 = " + o1Chunk + "; Chunk 2 = " + o2Chunk + " -> ");
      if (!o1Chunk.equals(o2Chunk)) {
        // if negative number
        // TODO: Interesting problem, with working around '-' because file 
        // "a-1.jpg" go before "a-2.jpg" while -1 > -2. Chunking negative will be different as well
        boolean o1Type = Character.isDigit(o1Chunk.charAt(0));
        boolean o2Type = Character.isDigit(o2Chunk.charAt(0));
        // if different types or both string -> lexi order
        if (isDouble == -1 && isHex == -1 && (o1Type != o2Type || (o1Type == false && o2Type == false))) {
          isHex = 0; isDouble = 0;
          // System.out.println("LEXI ORDER");
          if (caseSensitive) return o1Chunk.compareTo(o2Chunk);
          return o1Chunk.compareToIgnoreCase(o2Chunk) * order;
        } else {
          // Hex
          if (isHex == 1) {
            isHex = 0;
            // System.out.println("Hex ORDER");
            int o1Val = Integer.parseInt(o1Chunk, 16);
            int o2Val = Integer.parseInt(o2Chunk, 16);
            if (Integer.compare(o1Val, o2Val) == 0 && o1Chunk.length() != o2Chunk.length()) {
              return (o2Chunk.length() - o1Chunk.length()) * order;
            }
            return Integer.compare(o1Val, o2Val) * order;
          } else {
            // Double
            isDouble = 0;
            // System.out.println("DOUBLE ORDER");
            double o1Val = Double.parseDouble(o1Chunk);
            double o2Val = Double.parseDouble(o2Chunk);
            if (Double.compare(o1Val, o2Val) == 0 && o1Chunk.length() != o2Chunk.length()) {
              return (o2Chunk.length() - o1Chunk.length()) * order;
            }
            return Double.compare(o1Val, o2Val) * order;
          }
        }
      }
    }
    return o1Chunks.size() - o2Chunks.size();
  }
  // isDouble = 0
  /**
   * Transform the string to a list of string in which all the characters are
   * either all digit or not digit.
   * 
   * @param s The input string.
   * @return List of same type string.
   */
  private List<String> toChunk(String s) {
    if (s.length() == 0)
      return new ArrayList<>();
    List<String> ret = new ArrayList<>();
    // hex
    if ((isHex == 0 || isHex == 1) && s.matches("^0x[0-9A-F]+$")) {
      ret.add(s.substring(2));
      isHex = 1;
      return ret;
    }
    isHex = -1;
    // scientific, float -> double
    if ((isDouble == 0 || isDouble == 1) && (s.matches("^-?(0|[1-9]\\d*)(\\.\\d+)?$") ||
        s.matches("^-?(0|[1-9]\\d*)(\\.\\d+)?(e-?(0|[1-9]\\d*))?$"))) {
      ret.add(s);
      isDouble = 1;
      return ret;
    }
    isDouble = -1;
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
