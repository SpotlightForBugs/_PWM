// password generator, using sha256 and a salt

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// TODO:SALTING

public class AccountCrypt {
  // generate a password
  public static String generate(String masterPassword, String accountName) {
    // salt the master password
    String salted = masterPassword + accountName;
    // hash the salted master password
    String hashed = hash(salted);
    // return the first 16 characters of the hash
    return hashed.substring(0, 16);
  }

  // hash a string with sha256
  private static String hash(String input) {
    try {
      // get the sha256 algorithm
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      // hash the input
      byte[] messageDigest = md.digest(input.getBytes());
      // convert the byte array to a string
      StringBuffer sb = new StringBuffer();
      for (byte b : messageDigest) {
        sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
      }
      // return the string
      return sb.toString();
    } catch (NoSuchAlgorithmException e) {
      // if the algorithm is not found, print an error message
      System.out.println("Error: Algorithm not found");
      return null;
    }
  }

  public static boolean verify(String password, String hashedPassword, String accountName) {
    // hash the password
    String hashed = generate(password, accountName);
    // compare the hashes
    return hashed.equals(hashedPassword);
  }

  public static void test() {
    System.out.println(hash("hello"));
  }
}
