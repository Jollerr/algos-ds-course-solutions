import com.sun.deploy.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/** Quaternions. Basic operations. */
public class Quaternion {

   private BigDecimal bigA, bigB, bigC, bigD;
   private final int DECIMAL = 7;
   final double THRESHOLD = 1/Math.pow(10, DECIMAL);

   /** Constructor from four double values.
    * @param a real part
    * @param b imaginary part i
    * @param c imaginary part j
    * @param d imaginary part k
    */

   public Quaternion(double a, double b, double c, double d) {
      bigA = new BigDecimal(a);
      bigB = new BigDecimal(b);
      bigC = new BigDecimal(c);
      bigD = new BigDecimal(d);
   }

   /** Real part of the quaternion.
    * @return real part
    */
   public double getRpart() {
      return bigA.doubleValue();
   }

   /** Imaginary part i of the quaternion. 
    * @return imaginary part i
    */
   public double getIpart() {
      return bigB.doubleValue();
   }

   /** Imaginary part j of the quaternion. 
    * @return imaginary part j
    */
   public double getJpart() {
      return bigC.doubleValue();
   }

   /** Imaginary part k of the quaternion. 
    * @return imaginary part k
    */
   public double getKpart() {
       return bigD.doubleValue();
   }

   /** Conversion of the quaternion to the string.
    * @return a string form of this quaternion:
    * "a+bi+cj+dk"
    * (without any brackets)
    */
   @Override
   public String toString() {
      NumberFormat plusMinusNF = new DecimalFormat("+#;-#"); // https://stackoverflow.com/questions/5243316/format-a-number-with-leading-sign
      return plusMinusNF.format(getRpart()) + plusMinusNF.format(getIpart()) + "i" + plusMinusNF.format(getJpart()) + "j" + plusMinusNF.format(getKpart()) + "k";
   }

   /** Conversion from the string to the quaternion. 
    * Reverse to <code>toString</code> method.
    * @throws IllegalArgumentException if string s does not represent 
    *     a quaternion (defined by the <code>toString</code> method)
    * @param s string of form produced by the <code>toString</code> method
    * @return a quaternion represented by string s
    */
   public static Quaternion valueOf(String s) {
      StringBuilder collector = new StringBuilder();
      double[] arr = new double[4];
      for (int i = s.length() - 1; i >= 0; i--) {
         collector.append(s.charAt(i));
         if (s.charAt(i) == '+' || s.charAt(i) == '-' || i == 0) {
            int j;
            collector.reverse();
            switch (collector.charAt(collector.length() - 1)) {
               case 'i':
                  j = 1;
                  break;
               case 'j':
                  j = 2;
                  break;
               case 'k':
                  j = 3;
                  break;
               default:
                  j = 0;
            }
            if (arr[j] != 0) throw new IllegalArgumentException("Parameter " + s + " does not represent a quaternion.");
            try {
               Double.parseDouble(collector.charAt(collector.length() - 1) + "");
            } catch (NumberFormatException e) {
               collector.deleteCharAt(collector.length() - 1);
            }
            try {
               arr[j] = Double.parseDouble(collector.toString());
            } catch (NumberFormatException e) {
               throw new IllegalArgumentException("Parameter " + s + " does not represent a quaternion.");
            }
            collector.setLength(0);
         }
      }
      return new Quaternion(arr[0], arr[1], arr[2], arr[3]);
   }

   /** Clone of the quaternion.
    * @return independent clone of <code>this</code>
    */
   @Override
   public Object clone() throws CloneNotSupportedException {
      return new Quaternion(getRpart(), getIpart(), getJpart() ,getKpart());
   }

   /** Test whether the quaternion is zero. 
    * @return true, if the real part and all the imaginary parts are (close to) zero
    */
   public boolean isZero() {
      // https://howtodoinjava.com/java/basics/correctly-compare-float-double/
      if (Math.abs(getRpart() - 0)  > THRESHOLD) return false;
      if (Math.abs(getIpart() - 0)  > THRESHOLD) return false;
      if (Math.abs(getJpart() - 0)  > THRESHOLD) return false;
      if (Math.abs(getKpart() - 0)  > THRESHOLD) return false;
      return true;
   }

   /** Conjugate of the quaternion. Expressed by the formula 
    *     conjugate(a+bi+cj+dk) = a-bi-cj-dk
    * @return conjugate of <code>this</code>
    */
   public Quaternion conjugate() {
      return new Quaternion(bigA.doubleValue(), bigB.negate().doubleValue(), bigC.negate().doubleValue(), bigD.negate().doubleValue());
   }

   /** Opposite of the quaternion. Expressed by the formula 
    *    opposite(a+bi+cj+dk) = -a-bi-cj-dk
    * @return quaternion <code>-this</code>
    */
   public Quaternion opposite() {
      return new Quaternion(bigA.negate().doubleValue(), bigB.negate().doubleValue(), bigC.negate().doubleValue(), bigD.negate().doubleValue());
   }

   /** Sum of quaternions. Expressed by the formula 
    *    (a1+b1i+c1j+d1k) + (a2+b2i+c2j+d2k) = (a1+a2) + (b1+b2)i + (c1+c2)j + (d1+d2)k
    * @param q addend
    * @return quaternion <code>this+q</code>
    */
   public Quaternion plus (Quaternion q) {
      return new Quaternion(getRpart() + q.getRpart(), getIpart() + q.getIpart(), getJpart() + q.getJpart(), getKpart() + q.getKpart());
   }

   /** Product of quaternions. Expressed by the formula
    *  (a1+b1i+c1j+d1k) * (a2+b2i+c2j+d2k) = (a1a2-b1b2-c1c2-d1d2) + (a1b2+b1a2+c1d2-d1c2)i +
    *  (a1c2-b1d2+c1a2+d1b2)j + (a1d2+b1c2-c1b2+d1a2)k
    * @param q factor
    * @return quaternion <code>this*q</code>
    */
   public Quaternion times (Quaternion q) {
      BigDecimal a2 = new BigDecimal(q.getRpart());
      BigDecimal b2 = new BigDecimal(q.getIpart());
      BigDecimal c2= new BigDecimal(q.getJpart());
      BigDecimal d2 = new BigDecimal(q.getKpart());
      // https://stackoverflow.com/questions/4134047/java-bigdecimal-round-to-the-nearest-whole-value
      double rPart = (bigA.multiply(a2).subtract(bigB.multiply(b2)).subtract(bigC.multiply(c2)).subtract(bigD.multiply(d2))).setScale(DECIMAL, BigDecimal.ROUND_HALF_UP).doubleValue();
      double iPart = (bigA.multiply(b2).add(bigB.multiply(a2)).add(bigC.multiply(d2)).subtract(bigD.multiply(c2))).setScale(DECIMAL, BigDecimal.ROUND_HALF_UP).doubleValue();
      double jPart = (bigA.multiply(c2).subtract(bigB.multiply(d2)).add(bigC.multiply(a2).add(bigD.multiply(b2)))).setScale(DECIMAL, BigDecimal.ROUND_HALF_UP).doubleValue();
      double kPart = (bigA.multiply(d2).add(bigB.multiply(c2)).subtract(bigC.multiply(b2)).add(bigD.multiply(a2))).setScale(DECIMAL, BigDecimal.ROUND_HALF_UP).doubleValue();
      return new Quaternion(rPart, iPart, jPart, kPart);
   }

   /** Multiplication by a coefficient.
    * @param r coefficient
    * @return quaternion <code>this*r</code>
    */
   public Quaternion times (double r) {
      BigDecimal bigR = new BigDecimal(r);
       return new Quaternion(
              bigR.multiply(bigA).setScale(DECIMAL, BigDecimal.ROUND_HALF_UP).doubleValue(),
              bigR.multiply(bigB).setScale(DECIMAL, BigDecimal.ROUND_HALF_UP).doubleValue(),
              bigR.multiply(bigC).setScale(DECIMAL, BigDecimal.ROUND_HALF_UP).doubleValue(),
              bigR.multiply(bigD).setScale(DECIMAL, BigDecimal.ROUND_HALF_UP).doubleValue()
              );
   }

   /** Inverse of the quaternion. Expressed by the formula
    *     1/(a+bi+cj+dk) = a/(a*a+b*b+c*c+d*d) +
    *     ((-b)/(a*a+b*b+c*c+d*d))i + ((-c)/(a*a+b*b+c*c+d*d))j + ((-d)/(a*a+b*b+c*c+d*d))k
    * @return quaternion <code>1/this</code>
    */
   public Quaternion inverse() {
      if (isZero()) throw new IllegalArgumentException(this + " is zero. Cannot inverse.");
      BigDecimal divisor = bigA.multiply(bigA) // (a*a+b*b+c*c+d*d)
              .add(bigB.multiply(bigB)
              .add(bigC.multiply(bigC))
              .add(bigD.multiply(bigD)));
      double aPart = bigA.divide(divisor, DECIMAL, BigDecimal.ROUND_HALF_UP).doubleValue();
      double bPart = bigB.divide(divisor, DECIMAL, BigDecimal.ROUND_HALF_UP).negate().doubleValue();
      double cPart = bigC.divide(divisor, DECIMAL, BigDecimal.ROUND_HALF_UP).negate().doubleValue();
      double dPart = bigD.divide(divisor, DECIMAL, BigDecimal.ROUND_HALF_UP).negate().doubleValue();
      return new Quaternion(aPart, bPart, cPart, dPart);
   }

   /** Difference of quaternions. Expressed as addition to the opposite.
    * @param q subtrahend
    * @return quaternion <code>this-q</code>
    */
   public Quaternion minus (Quaternion q) {
      return new Quaternion(getRpart() - q.getRpart(), getIpart() - q.getIpart(), getJpart() - q.getJpart(), getKpart() - q.getKpart());
   }

   /** Right quotient of quaternions. Expressed as multiplication to the inverse.
    * @param q (right) divisor
    * @return quaternion <code>this*inverse(q)</code>
    */
   public Quaternion divideByRight (Quaternion q) {
      if (q.isZero()) throw new IllegalArgumentException("Parameter " + q + " is zero. Cannot find right quotient of quaternions.");
      return times(q.inverse());
   }

   /** Left quotient of quaternions.
    * @param q (left) divisor
    * @return quaternion <code>inverse(q)*this</code>
    */
   public Quaternion divideByLeft (Quaternion q) {
      if (q.isZero()) throw new IllegalArgumentException("Parameter " + q + " is zero. Cannot find left quotient of quaternions.");
      return q.inverse().times(this);
   }
   
   /** Equality test of quaternions. Difference of equal numbers
    *     is (close to) zero.
    * @param qo second quaternion
    * @return logical value of the expression <code>this.equals(qo)</code>
    */
   @Override
   public boolean equals(Object qo) {
      if (qo.getClass() != this.getClass()) {
         return false;
      }
      Quaternion toCompare = (Quaternion)qo;
      if (Math.abs(getRpart() - toCompare.getRpart())  > THRESHOLD) return false;
      if (Math.abs(getIpart() - toCompare.getIpart())  > THRESHOLD) return false;
      if (Math.abs(getJpart() - toCompare.getJpart())  > THRESHOLD) return false;
      if (Math.abs(getKpart() - toCompare.getKpart())  > THRESHOLD) return false;
      return true;
   }

   /** Dot product of quaternions. (p*conjugate(q) + q*conjugate(p))/2
    * @param q factor
    * @return dot product of this and q
    */
   public Quaternion dotMult (Quaternion q) {
      return (this.times(q.conjugate()).plus(q.times(this.conjugate()))).times(0.5);
   }

   /** Integer hashCode has to be the same for equal objects.
    * @return hashcode
    */
   @Override
   public int hashCode() {
      int result;
      long temp;
      temp = Double.doubleToLongBits(getRpart());
      result = (int) (temp ^ (temp >>> 32));
      temp = Double.doubleToLongBits(getIpart());
      result = 31 * result + (int) (temp ^ (temp >>> 32));
      temp = Double.doubleToLongBits(getJpart());
      result = 31 * result + (int) (temp ^ (temp >>> 32));
      temp = Double.doubleToLongBits(getKpart());
      result = 31 * result + (int) (temp ^ (temp >>> 32));
      return result;
   }

   /** Norm of the quaternion. Expressed by the formula
    *     norm(a+bi+cj+dk) = Math.sqrt(a*a+b*b+c*c+d*d)
    * @return norm of <code>this</code> (norm is a real number)
    */
   public double norm() {
      return Math.sqrt(bigA.multiply(bigA).add(bigB.multiply(bigB)).add(bigC.multiply(bigC)).add(bigD.multiply(bigD)).doubleValue());
   }

   /** Main method for testing purposes. 
    * @param arg command line parameters
    */
   public static void main (String[] arg) {
      String[] oige = new String[]{
              "-1-2i-3j-4k",
              "-1",
              "-1+3j",
              "3k-4j",
              "1+2i+3k+4j"
      };
      for (String s : oige) {
         Quaternion q = Quaternion.valueOf(s);
         System.out.println(q.toString());
      }

      String[] viga = new String[]{
              "-1-2i-3i-4k",
              "1+2i+3j+4k+5k"
      };
      for (String s : viga) {
         try {
            Quaternion q = Quaternion.valueOf(s);
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }
}
// end of file