package groovyex

/**
 *
 * @author cuong.nguyenmanh2
 */
import java.util.UUID;


/**
 * Static extension methods for the String class
 */
public class StringStaticExtension {
    public static String randomUUID(String selfType) {
        return UUID.randomUUID().toString();
    }
}

