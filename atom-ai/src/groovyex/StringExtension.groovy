package groovyex

/**
 *
 * @author cuong.nguyenmanh2
 */
/**
 * An extension class for the String class.
 */
public class StringExtension {
   public static String reverseToUpperCase(String self) {
      StringBuilder sb = new StringBuilder(self);
      sb.reverse();
      return sb.toString().toUpperCase();
   }
}

