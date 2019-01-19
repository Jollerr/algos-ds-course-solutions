import com.sun.javafx.geom.transform.BaseTransform;

import java.util.*;

public class Answer {

   public static void main (String[] param) {

      // TODO!!! Solutions to small problems 
      //   that do not need an independent method!
    
      // conversion double -> String
      double d = 1.;
      String str = "" + d;
      // conversion String -> int
      int inti = 1;
      str = "" + d;
      // "hh:mm:ss"
      long milliseconds = System.currentTimeMillis();
      String seconds = String.valueOf((int) (milliseconds / 1000) % 60) ;
      String minutes = String.valueOf ((int)((milliseconds / (1000*60)) % 60));
      String hours   = String.valueOf ((int)((milliseconds / (1000*60*60)) % 24));
      ArrayList<String> arrayList = new ArrayList<>();
      ArrayList<String> temp = new ArrayList<>();
      arrayList.add(hours);
      arrayList.add(minutes);
      arrayList.add(seconds);
      for (int i = 0; i < arrayList.size(); i++) {
         String s;

         if ((s =  arrayList.get(i)).length() < 2) {
            temp.add("0" + s);
         } else {
            temp.add(s);
         }

      }
      arrayList = temp;
      System.out.println(arrayList.get(0) + ":" + arrayList.get(1) + ":" + arrayList.get(2));

      // cos 45 deg
      System.out.println(Math.cos(Math.toRadians(45)));
      // table of square roots
      for (int i = 0; i < 101; i++) {
         System.out.println(Math.sqrt(i));
      }

      String firstString = "ABcd12";
      String result = reverseCase (firstString);
      System.out.println ("\"" + firstString + "\" -> \"" + result + "\"");

      // reverse string

      String s = "How  many	 words   here";
      int nw = countWords (s);
      System.out.println (s + "\t" + String.valueOf (nw));

      // pause. COMMENT IT OUT BEFORE JUNIT-TESTING!

      final int LSIZE = 100;
      ArrayList<Integer> randList = new ArrayList<Integer> (LSIZE);
      Random generaator = new Random();
      for (int i=0; i<LSIZE; i++) {
         randList.add (Integer.valueOf (generaator.nextInt(1000)));
      }

      // minimal element

      // HashMap tasks:
      //    create
      //    print all keys
      //    remove a key
      //    print all pairs

      System.out.println ("Before reverse:  " + randList);
      reverseList (randList);
      System.out.println ("After reverse: " + randList);

      System.out.println ("Maximum: " + maximum (randList));
   }

   /** Finding the maximal element.
    * @param a Collection of Comparable elements
    * @return maximal element.
    * @throws NoSuchElementException if <code> a </code> is empty.
    */
   static public <T extends Object & Comparable<? super T>>
         T maximum (Collection<? extends T> a) 
            throws NoSuchElementException {
      Iterator itr = a.iterator();
      T max = (T)itr.next();
      while (itr.hasNext()) {
         T element = (T)itr.next();
         if (max.compareTo(element) < 0) {
            max = element;
         }
      }
      return max;
   }

   /** Counting the number of words. Any number of any kind of
    * whitespace symbols between words is allowed.
    * @param text text
    * @return number of words in the text
    */
   public static int countWords (String text) {
      // StringTokenizer
      int counter = 0;
      boolean t = true;
      for (int i = 0; i < text.length(); i++) {
         if (Character.isAlphabetic(text.charAt(i))) {
            if (t) {
               counter++;
               t = false;
            }
         } else {
            t = true;
         }
      }
      return counter;
   }

   /** Case-reverse. Upper -> lower AND lower -> upper.
    * @param s string
    * @return processed string
    */
   public static String reverseCase (String s) {
      StringBuilder sb = new StringBuilder(s);
      for (int i = 0; i < sb.length(); i++) {
         if ((sb.charAt(i) + "").toUpperCase().equals((sb.charAt(i) + ""))) {
            sb.replace(i,i + 1, (sb.charAt(i) + "").toLowerCase());
         } else {
            sb.replace(i,i + 1, (sb.charAt(i) + "").toUpperCase());
         }
      }
      return sb.toString();
   }

   /** List reverse. Do not create a new list.
    * @param list list to reverse
    */
   public static <T extends Object> void reverseList (List<T> list)
      throws UnsupportedOperationException {
         int l = 0;
         int r = list.size() - 1;
         while (l <= r) {
            T temp = list.get(l);
            list.set(l, list.get(r));
            list.set(r, temp);
            l++;
            r--;
         }
   }
}
