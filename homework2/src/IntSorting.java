import java.util.*;

/**
 * Comparison of sorting methods. The same array of non-negative int values is
 * used for all methods.
 *
 * @author Jaanus
 * @version 3.0
 * @since 1.6
 */
public class IntSorting {

   /** maximal array length */
   static final int MAX_SIZE = 512000;

   /** number of competition rounds */
   static final int NUMBER_OF_ROUNDS = 4;

   /**
    * Main method.
    *
    * @param args
    *           command line parameters
    */
   public static void main(String[] args) {
         int[] arr = new int[]{4,47,2,76,99,23,23,47,2,4};
         IntSorting.binaryInsertionSort(arr);
         System.out.println(Arrays.toString(arr));
//      }


   }

   /**
    * Insertion sort.
    *
    * @param a
    *           array to be sorted
    */
   public static void insertionSort(int[] a) {
      if (a.length < 2) // if one element
         return;
      for (int i = 1; i < a.length; i++) {
         int smallest = a[i];
         int j;
         for (j = i - 1; j >= 0; j--) {
            if (a[j] <= smallest)
               break;
            a[j + 1] = a[j];
         }
         a[j + 1] = smallest;
      }
   }

   /**
    * Binary insertion sort.
    *
    * @param a
    *           array to be sorted
    */
   public static void binaryInsertionSort(int[] a) {
      if (a.length < 2) return;
      for (int i = 1; i < a.length; i++) {
         int sortedNumber = a[i]; // hetkel vaadeldav element
         int leftIndex = 0;
         int rightIndex = i - 1; // vaadeldavast elemendist vasak olev osa on sorteeritud
         int middleIndex = 0;
         int guess = a[middleIndex];
         while (leftIndex <= rightIndex) {
            middleIndex = (leftIndex + rightIndex) / 2;
            guess = a[middleIndex];
            if (guess == sortedNumber) {
               middleIndex += 1;
               break;
            }
            if (guess > sortedNumber) {
               rightIndex = middleIndex - 1;
            }
            if (guess < sortedNumber) {
               leftIndex = middleIndex + 1;
            }
         }
         // kui vaadeldav number on suurem kui kahendotsinguga leitud number, siis paneb selle ühe indeksi võrra ettepoole
         if (sortedNumber > guess) {
            middleIndex++;
         }
         // leitud indeks on lõplik
         if (sortedNumber < guess) {
         }
         // kopeerib numbreid massiivis paremale
         System.arraycopy(a,middleIndex, a, middleIndex + 1, i-middleIndex); // copies blocks of memory instead of copying single array elements
         a[middleIndex] = sortedNumber;
      }
   }

   /**
    * Sort a part of the array using quicksort method.
    *
    * @param array
    *           array to be changed
    * @param l
    *           starting index (included)
    * @param r
    *           ending index (excluded)
    */
   public static void quickSort (int[] array, int l, int r) {
      if (array == null || array.length < 1 || l < 0 || r <= l)
         throw new IllegalArgumentException("quickSort: wrong parameters");
      if ((r - l) < 2)
         return;
      int i = l;
      int j = r - 1;
      int x = array[(i + j) / 2];
      do {
         while (array[i] < x)
            i++;
         while (x < array[j])
            j--;
         if (i <= j) {
            int tmp = array[i];
            array[i] = array[j];
            array[j] = tmp;
            i++;
            j--;
         }
      } while (i < j);
      if (l < j)
         quickSort(array, l, j + 1); // recursion for left part
      if (i < r - 1)
         quickSort(array, i, r); // recursion for right part
   }

   /** frequency of the byte */
   public static int[] freq = new int[256];

   /** number of positions */
   public static final int KEYLEN = 4;

   /** Get the value of the position i. */
   public static int getValue(int key, int i) {
      return (key >>> (8 * i)) & 0xff;
   }

   /** Sort non-negative keys by position i in a stable manner. */
   public static int[] countSort(int[] keys, int i) {
      if (keys == null)
         return null;
      int[] res = new int[keys.length];
      for (int k = 0; k < freq.length; k++) {
         freq[k] = 0;
      }
      for (int key : keys) {
         freq[getValue(key, i)]++;
      }
      for (int k = 1; k < freq.length; k++) {
         freq[k] = freq[k - 1] + freq[k];
      }
      for (int j = keys.length - 1; j >= 0; j--) {
         int ind = --freq[getValue(keys[j], i)];
         res[ind] = keys[j];
      }
      return res;
   }

   /** Radix sort for non-negative integers. */
   public static void radixSort(int[] keys) {
      if (keys == null)
         return;
      int[] res = keys;
      for (int p = 0; p < KEYLEN; p++) {
         res = countSort(res, p);
      }
      System.arraycopy(res, 0, keys, 0, keys.length);
   }

   /**
    * Check whether an array is ordered.
    *
    * @param a
    *           sorted (?) array
    * @throws IllegalArgumentException
    *            if an array is not ordered
    */
   static void checkOrder(int[] a) {
      if (a.length < 2)
         return;
      for (int i = 0; i < a.length - 1; i++) {
         if (a[i] > a[i + 1])
            throw new IllegalArgumentException(
                    "array not ordered: " + "a[" + i + "]=" + a[i] + " a[" + (i + 1) + "]=" + a[i + 1]);
      }
   }

}

