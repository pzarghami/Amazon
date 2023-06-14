package Baloot.security;


import org.springframework.security.crypto.bcrypt.BCrypt;
import java.security.SecureRandom;

public class PasswordEncoder {
    private static final int strength = 10;
    public static String encode(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(strength));
    }
    public static boolean matches(String rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword, encodedPassword);
    }

}
